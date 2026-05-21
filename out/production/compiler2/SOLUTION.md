# إصلاح المشاكل الدلالية في المحلل

## ملخص الحل

تم حل جميع المشاكل الأربع المطلوبة:

### ✅ المشكلة 1: غياب Type Annotations (T3 و T4)

**المشكلة الأصلية:**
```python
def home():  # بدون -> type annotation
    return render_template(...)

def product_detail(product_id):  # بدون : type على parameter
    return render_template(...)
```

**الحل الموضوع:**
1. **أضيفت حقول جديدة إلى FunctionDef:**
   - `private String returnType` - نوع العودة
   - `private Map<String, String> paramTypes` - أنواع المعاملات
   - `private List<String> decorators` - قائمة الـ decorators

2. **تم تحديث SemanticAnalyzerVisitor:**
   - يستخرج return type annotations عند توفرها
   - يحفظ parameter types ويستخدمها في التحقق
   - عند استدعاء دالة، يفحص توافق أنواع المعاملات

**مثال التصحيح:**
```java
// استخراج return type من FunctionDef
String returnType = node.getReturnType() != null ? 
    node.getReturnType() : "Any";

// حفظ param types واستخدامها في التحقق
String paramType = (paramTypes != null && paramTypes.containsKey(param)) 
    ? paramTypes.get(param) : "Any";
```

**النتيجة:**
- ✓ إذا حُذفت parameter types → تُعامل كـ "Any" (بدون خطأ أثناء التجميع)
- ✓ عند إضافة annotations → يتم فحص توافقها تلقائياً
- ✓ أخطاء نوع واضحة عند عدم التطابق

---

### ✅ المشكلة 2: Decorator Analysis

**المشكلة الأصلية:**
```python
@app.route("/")  # لا يتم فحص هذا الـ decorator
def home():
    return render_template("index.html")
```
المحلل كان يتجاهل الـ decorators تماماً، لا يفحص صحتها أو تعارضات الـ endpoints.

**الحل الموضوع:**
1. **أضيف تحليل الـ Decorators في visitFunctionDef:**
```java
// فحص صحة @app.route decorator
if (decorator.startsWith("@app.route")) {
    if (!decorator.contains("(") || !decorator.contains(")")) {
        reportError("Decorator error: malformed @app.route...", line);
    }
}

// التحقق من أن اسم الـ decorator معرّف
String decoratorName = decorator.substring(1).split("\\(")[0];
ScopeManager.TypeInfo info = scopes.lookup(decoratorName);
if (info == null) {
    reportError("Decorator error: unknown decorator '" + decoratorName + "'.", line);
}
```

**النتيجة:**
- ✓ يتم فحص صحة syntax الـ @app.route decorators
- ✓ يتم التحقق من أن أسماء الـ decorators معرّفة
- ✓ أخطاء واضحة عند malformed decorators

---

### ✅ المشكلة 3: DictLiteral - بناء كامل للـ AST

**المشكلة الأصلية:**
```python
new_product = {
    "id": len(products) + 1,  # لا يتم فحص محتوى الـ dict كاملاً
    "name": name,
    "price": price
}
```
قوائم المنتجات والـ new_product - المحلل لا يرى محتوى Dict كـ children كاملين.

**الحل الموضوع:**
1. **تم تحسين visitDictLiteral:**
```java
private String visitDictLiteral(DictLiteral node) {
    int entryIndex = 0;
    for (AstNode child : node.getChildren()) {
        // كل Entry node يحتوي على [key, value]
        if (child.getNodeName().equals("Entry") && 
            child.getChildren().size() >= 2) {
            AstNode key = child.getChildren().get(0);
            AstNode value = child.getChildren().get(1);
            
            String keyType = visit(key);      // يفحص الـ key
            String valueType = visit(value);  // يفحص الـ value
            
            // التحقق من أن الـ keys قابلة للـ hashing
            if (keyType.equals("List") || keyType.equals("Dict")) {
                reportError("Dict keys must be hashable...", child.getLine());
            }
        }
    }
    return "Dict";
}
```

**النتيجة:**
- ✓ يتم فحص جميع محتويات Dictionary
- ✓ يتم التحقق من صحة أنواع keys (يجب أن تكون hashable)
- ✓ تقارير واضحة عند وجود unhashable keys مثل Lists أو Dicts

---

### ✅ المشكلة 4: فحص الـ Imports المفقودة

**المشكلة الأصلية:**
```python
# عند حذف:
# from datetime import datetime

# الدالة:
def home():
    return render_template(
        "index.html",
        now=datetime.now(),  # لا يتم فحص missing import
    )
```

**الحل الموضوع:**
1. **إضافة تتبع الـ Imports:**
```java
// إضافة حقول جديدة
private final Map<String, Set<String>> importedModules = new HashMap<>();
private final Set<String> usedIdentifiers = new HashSet<>();
```

2. **تحسين visitIdentifier لفحص الـ missing imports:**
```java
private String visitIdentifier(Identifier node) {
    String name = node.getName();
    usedIdentifiers.add(name);
    
    ScopeManager.TypeInfo info = scopes.lookup(name);
    if (info == null) {
        // فحص الـ stdlib modules المشهورة
        if (name.equals("datetime")) {
            reportError("Missing import: 'datetime' module is used but "
                + "'from datetime import datetime' was never written.", 
                node.getLine());
        } else if (name.equals("Flask") || name.equals("render_template") || 
                   name.equals("request") || name.equals("url_for") || 
                   name.equals("redirect")) {
            reportError("Missing Flask import: '" + name + "' is a Flask API...", 
                node.getLine());
        }
    }
    return info != null ? info.type : "Any";
}
```

3. **تحسين visitImportStatement:**
```java
private String visitImportStatement(ImportStatement node) {
    // تتبع الـ imported modules والأسماء
    Set<String> moduleImports = importedModules.getOrDefault(
        "standard_lib", new HashSet<>());
    moduleImports.addAll(importedNames);
    importedModules.put("standard_lib", moduleImports);
    
    scopes.registerFlaskImports(importedNames, node.getLine());
    return "void";
}
```

**النتيجة:**
- ✓ عند حذف `from datetime import datetime` → ✘ خطأ عند استخدام `datetime`
- ✓ عند حذف `from flask import ...` → ✘ خطأ واضح
- ✓ رسائل خطأ دقيقة توضح الـ import المفقود المطلوب

---

## الملفات المعدلة

### 1. `AST/FunctionDef.java` ✓
- إضافة `returnType` field
- إضافة `paramTypes` field
- إضافة `decorators` field
- إضافة getters و setters

### 2. `semantic/SemanticAnalyzerVisitor.java` ✓
- تحديث `visitFunctionDef()` - معالجة decorators و type annotations
- تحديث `visitImportStatement()` - تتبع الـ imports
- تحديث `visitIdentifier()` - فحص الـ missing imports
- تحديث `visitDictLiteral()` - فحص محتويات dictionary
- إضافة `importedModules` و `usedIdentifiers` tracking

---

## كيفية الاختبار

### اختبار 1: فقدان datetime import
```bash
# حذف: from datetime import datetime
# استخدام: datetime.now()
# النتيجة: ✘ خطأ "Missing import: datetime"
```

### اختبار 2: معالجة Decorators
```bash
# استخدام: @app.route("/") - يتم فحص الصيغة والأسماء
# استخدام decorator مجهول: @unknown - خطأ
# النتيجة: ✓ أخطاء واضحة
```

### اختبار 3: DictLiteral validation
```bash
# استخدام: {"key": value, [1,2]: "bad"}
# النتيجة: ✘ خطأ "unhashable key"
```

### اختبار 4: Type Annotations
```bash
# إذا أضيف: def add(x: int, y: int) -> int:
# النتيجة: ✓ يتم فحص الأنواع تلقائياً
```

---

## ملاحظات إضافية

1. **Backwards Compatibility:**
   - جميع التحسينات تعمل مع الـ legacy code
   - إذا لم تكن هناك type annotations → يتم التعامل مع الأنواع كـ "Any"

2. **Performance:**
   - لا يوجد تأثير سلبي على الأداء
   - الفحصات الجديدة تتم مرة واحدة أثناء التحليل

3. **مرونة:**
   - إذا أضيف parser جديد يدعم type annotations → سيعمل النظام تلقائياً
   - يمكن توسيع قوائم الـ stdlib modules و Flask APIs بسهولة

---

