// Generated from C:/Courses/compiler2/grammers/htmlParser.g4 by ANTLR 4.13.1
package grammers;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class htmlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, COMMENT=2, EXPR_START=3, TAG_START=4, COMMENT_START=5, HTML_DOCUMENT=6, 
		STYLE_OPEN=7, OPEN_TAG=8, HTML_TEXT=9, TEXT=10, TAG_WS=11, CLOSE_TAG=12, 
		CLOSE_SLASH_TAG=13, TAG_SLASH=14, TAG_NAME=15, ATTR_NAME=16, TAG_EQUALS=17, 
		ATTRIBUTE_VALUE=18, ATTRIBUTE=19, ATTR_WS=20, STYLE_CLOSE=21, CSS_WS=22, 
		CSS_COMMENT=23, OPENBRACE=24, CSS_SELECTOR=25, PROPERTY=26, VALUE=27, 
		SEMICOLON_CSS=28, COLON_CSS=29, COMMA_CSS=30, CLOSEBRACE=31, CSS_PROP_WS=32, 
		EXPR_END=33, DOT=34, COMMA=35, COLON=36, PIPE=37, LPAREN=38, RPAREN=39, 
		LBRACK=40, RBRACK=41, LBRACE=42, RBRACE=43, PLUS=44, MINUS=45, MULT=46, 
		DIV=47, MOD=48, POW=49, EQ=50, NE=51, LT=52, GT=53, LE=54, GE=55, AND=56, 
		OR=57, NOT=58, IN=59, IS=60, NONE=61, TRUE=62, FALSE=63, ID=64, INT=65, 
		FLOAT=66, STRING=67, EXPR_WS=68, JINJA_TAG_WS=69, TAG_END=70, IF=71, ELIF=72, 
		ELSE=73, ENDIF=74, SET=75, INCLUDE=76, EXTENDS=77, FOR=78, ENDFOR=79, 
		COMMENT_END=80, COMMENT_TEXT=81;
	public static final int
		RULE_htmlDocument = 0, RULE_htmlElement = 1, RULE_htmlTag = 2, RULE_htmlAttribute = 3, 
		RULE_cssStyle = 4, RULE_stylesheet = 5, RULE_ruleset = 6, RULE_properties = 7, 
		RULE_value = 8, RULE_jinjaExpression = 9, RULE_jinjaTag = 10, RULE_jinjaComment = 11, 
		RULE_expr = 12, RULE_literal = 13, RULE_jinjaSingleTag = 14, RULE_singleJinjaTagContent = 15, 
		RULE_firstBlockJinjaTagContent = 16, RULE_endBlockJinjaTagContent = 17, 
		RULE_jinjaBlock = 18, RULE_jinjaForBlock = 19, RULE_jinjaIfBlock = 20;
	private static String[] makeRuleNames() {
		return new String[] {
			"htmlDocument", "htmlElement", "htmlTag", "htmlAttribute", "cssStyle", 
			"stylesheet", "ruleset", "properties", "value", "jinjaExpression", "jinjaTag", 
			"jinjaComment", "expr", "literal", "jinjaSingleTag", "singleJinjaTagContent", 
			"firstBlockJinjaTagContent", "endBlockJinjaTagContent", "jinjaBlock", 
			"jinjaForBlock", "jinjaIfBlock"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'{{'", "'{%'", "'{#'", "'<!DOCTYPE html>'", "'<style>'", 
			null, null, null, null, null, "'/>'", null, null, null, "'='", null, 
			null, null, "'</style>'", null, null, null, null, null, null, "';'", 
			null, null, null, null, "'}}'", null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "'%}'", "'if'", "'elif'", "'else'", "'endif'", 
			"'set'", "'include'", "'extends'", "'for'", "'endfor'", "'#}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "COMMENT", "EXPR_START", "TAG_START", "COMMENT_START", "HTML_DOCUMENT", 
			"STYLE_OPEN", "OPEN_TAG", "HTML_TEXT", "TEXT", "TAG_WS", "CLOSE_TAG", 
			"CLOSE_SLASH_TAG", "TAG_SLASH", "TAG_NAME", "ATTR_NAME", "TAG_EQUALS", 
			"ATTRIBUTE_VALUE", "ATTRIBUTE", "ATTR_WS", "STYLE_CLOSE", "CSS_WS", "CSS_COMMENT", 
			"OPENBRACE", "CSS_SELECTOR", "PROPERTY", "VALUE", "SEMICOLON_CSS", "COLON_CSS", 
			"COMMA_CSS", "CLOSEBRACE", "CSS_PROP_WS", "EXPR_END", "DOT", "COMMA", 
			"COLON", "PIPE", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "LBRACE", "RBRACE", 
			"PLUS", "MINUS", "MULT", "DIV", "MOD", "POW", "EQ", "NE", "LT", "GT", 
			"LE", "GE", "AND", "OR", "NOT", "IN", "IS", "NONE", "TRUE", "FALSE", 
			"ID", "INT", "FLOAT", "STRING", "EXPR_WS", "JINJA_TAG_WS", "TAG_END", 
			"IF", "ELIF", "ELSE", "ENDIF", "SET", "INCLUDE", "EXTENDS", "FOR", "ENDFOR", 
			"COMMENT_END", "COMMENT_TEXT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "htmlParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public htmlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HtmlDocumentContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(htmlParser.EOF, 0); }
		public TerminalNode HTML_DOCUMENT() { return getToken(htmlParser.HTML_DOCUMENT, 0); }
		public List<HtmlElementContext> htmlElement() {
			return getRuleContexts(HtmlElementContext.class);
		}
		public HtmlElementContext htmlElement(int i) {
			return getRuleContext(HtmlElementContext.class,i);
		}
		public HtmlDocumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlDocument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterHtmlDocument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitHtmlDocument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitHtmlDocument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlDocumentContext htmlDocument() throws RecognitionException {
		HtmlDocumentContext _localctx = new HtmlDocumentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_htmlDocument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==HTML_DOCUMENT) {
				{
				setState(42);
				match(HTML_DOCUMENT);
				}
			}

			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 952L) != 0)) {
				{
				{
				setState(45);
				htmlElement();
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(51);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HtmlElementContext extends ParserRuleContext {
		public HtmlElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlElement; }
	 
		public HtmlElementContext() { }
		public void copyFrom(HtmlElementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Jinja_TagContext extends HtmlElementContext {
		public JinjaTagContext jinjaTag() {
			return getRuleContext(JinjaTagContext.class,0);
		}
		public Jinja_TagContext(HtmlElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterJinja_Tag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitJinja_Tag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitJinja_Tag(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Tag_htmlContext extends HtmlElementContext {
		public HtmlTagContext htmlTag() {
			return getRuleContext(HtmlTagContext.class,0);
		}
		public Tag_htmlContext(HtmlElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterTag_html(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitTag_html(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitTag_html(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JinjaExprContext extends HtmlElementContext {
		public JinjaExpressionContext jinjaExpression() {
			return getRuleContext(JinjaExpressionContext.class,0);
		}
		public JinjaExprContext(HtmlElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterJinjaExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitJinjaExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitJinjaExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JinjaCommContext extends HtmlElementContext {
		public JinjaCommentContext jinjaComment() {
			return getRuleContext(JinjaCommentContext.class,0);
		}
		public JinjaCommContext(HtmlElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterJinjaComm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitJinjaComm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitJinjaComm(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TextContext extends HtmlElementContext {
		public TerminalNode HTML_TEXT() { return getToken(htmlParser.HTML_TEXT, 0); }
		public TextContext(HtmlElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitText(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitText(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CssStyContext extends HtmlElementContext {
		public CssStyleContext cssStyle() {
			return getRuleContext(CssStyleContext.class,0);
		}
		public CssStyContext(HtmlElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterCssSty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitCssSty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitCssSty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlElementContext htmlElement() throws RecognitionException {
		HtmlElementContext _localctx = new HtmlElementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_htmlElement);
		try {
			setState(59);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OPEN_TAG:
				_localctx = new Tag_htmlContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(53);
				htmlTag();
				}
				break;
			case STYLE_OPEN:
				_localctx = new CssStyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(54);
				cssStyle();
				}
				break;
			case EXPR_START:
				_localctx = new JinjaExprContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(55);
				jinjaExpression();
				}
				break;
			case TAG_START:
				_localctx = new Jinja_TagContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(56);
				jinjaTag();
				}
				break;
			case COMMENT_START:
				_localctx = new JinjaCommContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(57);
				jinjaComment();
				}
				break;
			case HTML_TEXT:
				_localctx = new TextContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(58);
				match(HTML_TEXT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HtmlTagContext extends ParserRuleContext {
		public List<TerminalNode> OPEN_TAG() { return getTokens(htmlParser.OPEN_TAG); }
		public TerminalNode OPEN_TAG(int i) {
			return getToken(htmlParser.OPEN_TAG, i);
		}
		public List<TerminalNode> TAG_NAME() { return getTokens(htmlParser.TAG_NAME); }
		public TerminalNode TAG_NAME(int i) {
			return getToken(htmlParser.TAG_NAME, i);
		}
		public List<TerminalNode> CLOSE_TAG() { return getTokens(htmlParser.CLOSE_TAG); }
		public TerminalNode CLOSE_TAG(int i) {
			return getToken(htmlParser.CLOSE_TAG, i);
		}
		public List<HtmlAttributeContext> htmlAttribute() {
			return getRuleContexts(HtmlAttributeContext.class);
		}
		public HtmlAttributeContext htmlAttribute(int i) {
			return getRuleContext(HtmlAttributeContext.class,i);
		}
		public List<HtmlElementContext> htmlElement() {
			return getRuleContexts(HtmlElementContext.class);
		}
		public HtmlElementContext htmlElement(int i) {
			return getRuleContext(HtmlElementContext.class,i);
		}
		public TerminalNode TAG_SLASH() { return getToken(htmlParser.TAG_SLASH, 0); }
		public TerminalNode CLOSE_SLASH_TAG() { return getToken(htmlParser.CLOSE_SLASH_TAG, 0); }
		public HtmlTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlTag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterHtmlTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitHtmlTag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitHtmlTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlTagContext htmlTag() throws RecognitionException {
		HtmlTagContext _localctx = new HtmlTagContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_htmlTag);
		int _la;
		try {
			int _alt;
			setState(91);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(61);
				match(OPEN_TAG);
				setState(62);
				match(TAG_NAME);
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ATTR_NAME) {
					{
					{
					setState(63);
					htmlAttribute();
					}
					}
					setState(68);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(69);
				match(CLOSE_TAG);
				setState(73);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(70);
						htmlElement();
						}
						} 
					}
					setState(75);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				}
				setState(80);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(76);
					match(OPEN_TAG);
					setState(77);
					match(TAG_SLASH);
					setState(78);
					match(TAG_NAME);
					setState(79);
					match(CLOSE_TAG);
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(82);
				match(OPEN_TAG);
				setState(83);
				match(TAG_NAME);
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ATTR_NAME) {
					{
					{
					setState(84);
					htmlAttribute();
					}
					}
					setState(89);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(90);
				match(CLOSE_SLASH_TAG);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HtmlAttributeContext extends ParserRuleContext {
		public TerminalNode ATTR_NAME() { return getToken(htmlParser.ATTR_NAME, 0); }
		public TerminalNode TAG_EQUALS() { return getToken(htmlParser.TAG_EQUALS, 0); }
		public TerminalNode ATTRIBUTE_VALUE() { return getToken(htmlParser.ATTRIBUTE_VALUE, 0); }
		public HtmlAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterHtmlAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitHtmlAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitHtmlAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlAttributeContext htmlAttribute() throws RecognitionException {
		HtmlAttributeContext _localctx = new HtmlAttributeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_htmlAttribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(ATTR_NAME);
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TAG_EQUALS) {
				{
				setState(94);
				match(TAG_EQUALS);
				setState(95);
				match(ATTRIBUTE_VALUE);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CssStyleContext extends ParserRuleContext {
		public TerminalNode STYLE_OPEN() { return getToken(htmlParser.STYLE_OPEN, 0); }
		public StylesheetContext stylesheet() {
			return getRuleContext(StylesheetContext.class,0);
		}
		public TerminalNode STYLE_CLOSE() { return getToken(htmlParser.STYLE_CLOSE, 0); }
		public CssStyleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cssStyle; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterCssStyle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitCssStyle(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitCssStyle(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CssStyleContext cssStyle() throws RecognitionException {
		CssStyleContext _localctx = new CssStyleContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_cssStyle);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(STYLE_OPEN);
			setState(99);
			stylesheet();
			setState(100);
			match(STYLE_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StylesheetContext extends ParserRuleContext {
		public List<RulesetContext> ruleset() {
			return getRuleContexts(RulesetContext.class);
		}
		public RulesetContext ruleset(int i) {
			return getRuleContext(RulesetContext.class,i);
		}
		public StylesheetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stylesheet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterStylesheet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitStylesheet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitStylesheet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StylesheetContext stylesheet() throws RecognitionException {
		StylesheetContext _localctx = new StylesheetContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_stylesheet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CSS_SELECTOR) {
				{
				{
				setState(102);
				ruleset();
				}
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RulesetContext extends ParserRuleContext {
		public TerminalNode CSS_SELECTOR() { return getToken(htmlParser.CSS_SELECTOR, 0); }
		public TerminalNode OPENBRACE() { return getToken(htmlParser.OPENBRACE, 0); }
		public TerminalNode CLOSEBRACE() { return getToken(htmlParser.CLOSEBRACE, 0); }
		public List<PropertiesContext> properties() {
			return getRuleContexts(PropertiesContext.class);
		}
		public PropertiesContext properties(int i) {
			return getRuleContext(PropertiesContext.class,i);
		}
		public RulesetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleset; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterRuleset(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitRuleset(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitRuleset(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulesetContext ruleset() throws RecognitionException {
		RulesetContext _localctx = new RulesetContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_ruleset);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			match(CSS_SELECTOR);
			setState(109);
			match(OPENBRACE);
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PROPERTY) {
				{
				{
				setState(110);
				properties();
				}
				}
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(116);
			match(CLOSEBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropertiesContext extends ParserRuleContext {
		public TerminalNode PROPERTY() { return getToken(htmlParser.PROPERTY, 0); }
		public TerminalNode COLON_CSS() { return getToken(htmlParser.COLON_CSS, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode SEMICOLON_CSS() { return getToken(htmlParser.SEMICOLON_CSS, 0); }
		public PropertiesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_properties; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterProperties(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitProperties(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitProperties(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertiesContext properties() throws RecognitionException {
		PropertiesContext _localctx = new PropertiesContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_properties);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			match(PROPERTY);
			setState(119);
			match(COLON_CSS);
			setState(120);
			value();
			setState(121);
			match(SEMICOLON_CSS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public List<TerminalNode> VALUE() { return getTokens(htmlParser.VALUE); }
		public TerminalNode VALUE(int i) {
			return getToken(htmlParser.VALUE, i);
		}
		public List<TerminalNode> COMMA_CSS() { return getTokens(htmlParser.COMMA_CSS); }
		public TerminalNode COMMA_CSS(int i) {
			return getToken(htmlParser.COMMA_CSS, i);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(VALUE);
			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA_CSS) {
				{
				{
				setState(124);
				match(COMMA_CSS);
				setState(125);
				match(VALUE);
				}
				}
				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaExpressionContext extends ParserRuleContext {
		public TerminalNode EXPR_START() { return getToken(htmlParser.EXPR_START, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EXPR_END() { return getToken(htmlParser.EXPR_END, 0); }
		public JinjaExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterJinjaExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitJinjaExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitJinjaExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaExpressionContext jinjaExpression() throws RecognitionException {
		JinjaExpressionContext _localctx = new JinjaExpressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_jinjaExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(EXPR_START);
			setState(132);
			expr(0);
			setState(133);
			match(EXPR_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaTagContext extends ParserRuleContext {
		public JinjaSingleTagContext jinjaSingleTag() {
			return getRuleContext(JinjaSingleTagContext.class,0);
		}
		public JinjaBlockContext jinjaBlock() {
			return getRuleContext(JinjaBlockContext.class,0);
		}
		public JinjaTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaTag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterJinjaTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitJinjaTag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitJinjaTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaTagContext jinjaTag() throws RecognitionException {
		JinjaTagContext _localctx = new JinjaTagContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_jinjaTag);
		try {
			setState(137);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(135);
				jinjaSingleTag();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(136);
				jinjaBlock();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaCommentContext extends ParserRuleContext {
		public TerminalNode COMMENT_START() { return getToken(htmlParser.COMMENT_START, 0); }
		public TerminalNode COMMENT_END() { return getToken(htmlParser.COMMENT_END, 0); }
		public TerminalNode COMMENT_TEXT() { return getToken(htmlParser.COMMENT_TEXT, 0); }
		public JinjaCommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaComment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterJinjaComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitJinjaComment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitJinjaComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaCommentContext jinjaComment() throws RecognitionException {
		JinjaCommentContext _localctx = new JinjaCommentContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_jinjaComment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(COMMENT_START);
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMENT_TEXT) {
				{
				setState(140);
				match(COMMENT_TEXT);
				}
			}

			setState(143);
			match(COMMENT_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PowerExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode POW() { return getToken(htmlParser.POW, 0); }
		public PowerExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterPowerExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitPowerExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitPowerExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddSubExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(htmlParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(htmlParser.MINUS, 0); }
		public AddSubExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterAddSubExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitAddSubExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitAddSubExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode IN() { return getToken(htmlParser.IN, 0); }
		public InExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterInExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitInExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitInExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OR() { return getToken(htmlParser.OR, 0); }
		public OrExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubscriptionExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LBRACK() { return getToken(htmlParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(htmlParser.RBRACK, 0); }
		public SubscriptionExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterSubscriptionExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitSubscriptionExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitSubscriptionExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MulDivModExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MULT() { return getToken(htmlParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(htmlParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(htmlParser.MOD, 0); }
		public MulDivModExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterMulDivModExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitMulDivModExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitMulDivModExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LT() { return getToken(htmlParser.LT, 0); }
		public TerminalNode GT() { return getToken(htmlParser.GT, 0); }
		public TerminalNode LE() { return getToken(htmlParser.LE, 0); }
		public TerminalNode GE() { return getToken(htmlParser.GE, 0); }
		public ComparisonExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterComparisonExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitComparisonExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitComparisonExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FilterExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PIPE() { return getToken(htmlParser.PIPE, 0); }
		public TerminalNode ID() { return getToken(htmlParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(htmlParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(htmlParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(htmlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(htmlParser.COMMA, i);
		}
		public FilterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterFilterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitFilterExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitFilterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AttributeExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DOT() { return getToken(htmlParser.DOT, 0); }
		public TerminalNode ID() { return getToken(htmlParser.ID, 0); }
		public AttributeExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterAttributeExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitAttributeExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitAttributeExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExprContext {
		public TerminalNode LPAREN() { return getToken(htmlParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(htmlParser.RPAREN, 0); }
		public ParenExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotExprContext extends ExprContext {
		public TerminalNode NOT() { return getToken(htmlParser.NOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NotExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryMinusExprContext extends ExprContext {
		public TerminalNode MINUS() { return getToken(htmlParser.MINUS, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnaryMinusExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterUnaryMinusExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitUnaryMinusExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitUnaryMinusExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LiteralExprContext extends ExprContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterLiteralExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitLiteralExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitLiteralExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CallExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LPAREN() { return getToken(htmlParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(htmlParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(htmlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(htmlParser.COMMA, i);
		}
		public CallExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterCallExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitCallExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitCallExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IsExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode IS() { return getToken(htmlParser.IS, 0); }
		public TerminalNode ID() { return getToken(htmlParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(htmlParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(htmlParser.RPAREN, 0); }
		public IsExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterIsExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitIsExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitIsExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(htmlParser.EQ, 0); }
		public TerminalNode NE() { return getToken(htmlParser.NE, 0); }
		public EqualityExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterEqualityExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitEqualityExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitEqualityExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdentifierExprContext extends ExprContext {
		public TerminalNode ID() { return getToken(htmlParser.ID, 0); }
		public IdentifierExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterIdentifierExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitIdentifierExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitIdentifierExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode AND() { return getToken(htmlParser.AND, 0); }
		public AndExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
			case LBRACE:
			case NONE:
			case TRUE:
			case FALSE:
			case INT:
			case FLOAT:
			case STRING:
				{
				_localctx = new LiteralExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(146);
				literal();
				}
				break;
			case ID:
				{
				_localctx = new IdentifierExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(147);
				match(ID);
				}
				break;
			case MINUS:
				{
				_localctx = new UnaryMinusExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(148);
				match(MINUS);
				setState(149);
				expr(12);
				}
				break;
			case NOT:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(150);
				match(NOT);
				setState(151);
				expr(11);
				}
				break;
			case LPAREN:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(152);
				match(LPAREN);
				setState(153);
				expr(0);
				setState(154);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(232);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(230);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
					case 1:
						{
						_localctx = new PowerExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(158);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(159);
						match(POW);
						setState(160);
						expr(11);
						}
						break;
					case 2:
						{
						_localctx = new MulDivModExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(161);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(162);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 492581209243648L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(163);
						expr(10);
						}
						break;
					case 3:
						{
						_localctx = new AddSubExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(164);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(165);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(166);
						expr(9);
						}
						break;
					case 4:
						{
						_localctx = new ComparisonExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(167);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(168);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 67553994410557440L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(169);
						expr(8);
						}
						break;
					case 5:
						{
						_localctx = new EqualityExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(170);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(171);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NE) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(172);
						expr(7);
						}
						break;
					case 6:
						{
						_localctx = new AndExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(173);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(174);
						match(AND);
						setState(175);
						expr(6);
						}
						break;
					case 7:
						{
						_localctx = new OrExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(176);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(177);
						match(OR);
						setState(178);
						expr(5);
						}
						break;
					case 8:
						{
						_localctx = new InExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(179);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(180);
						match(IN);
						setState(181);
						expr(4);
						}
						break;
					case 9:
						{
						_localctx = new AttributeExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(182);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(183);
						match(DOT);
						setState(184);
						match(ID);
						}
						break;
					case 10:
						{
						_localctx = new SubscriptionExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(185);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(186);
						match(LBRACK);
						setState(187);
						expr(0);
						setState(188);
						match(RBRACK);
						}
						break;
					case 11:
						{
						_localctx = new CallExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(190);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(191);
						match(LPAREN);
						setState(200);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (((((_la - 38)) & ~0x3f) == 0 && ((1L << (_la - 38)) & 1066401941L) != 0)) {
							{
							setState(192);
							expr(0);
							setState(197);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==COMMA) {
								{
								{
								setState(193);
								match(COMMA);
								setState(194);
								expr(0);
								}
								}
								setState(199);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
						}

						setState(202);
						match(RPAREN);
						}
						break;
					case 12:
						{
						_localctx = new FilterExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(203);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(204);
						match(PIPE);
						setState(205);
						match(ID);
						setState(218);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
						case 1:
							{
							setState(206);
							match(LPAREN);
							setState(215);
							_errHandler.sync(this);
							_la = _input.LA(1);
							if (((((_la - 38)) & ~0x3f) == 0 && ((1L << (_la - 38)) & 1066401941L) != 0)) {
								{
								setState(207);
								expr(0);
								setState(212);
								_errHandler.sync(this);
								_la = _input.LA(1);
								while (_la==COMMA) {
									{
									{
									setState(208);
									match(COMMA);
									setState(209);
									expr(0);
									}
									}
									setState(214);
									_errHandler.sync(this);
									_la = _input.LA(1);
								}
								}
							}

							setState(217);
							match(RPAREN);
							}
							break;
						}
						}
						break;
					case 13:
						{
						_localctx = new IsExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(220);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(221);
						match(IS);
						setState(222);
						match(ID);
						setState(228);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
						case 1:
							{
							setState(223);
							match(LPAREN);
							setState(225);
							_errHandler.sync(this);
							_la = _input.LA(1);
							if (((((_la - 38)) & ~0x3f) == 0 && ((1L << (_la - 38)) & 1066401941L) != 0)) {
								{
								setState(224);
								expr(0);
								}
							}

							setState(227);
							match(RPAREN);
							}
							break;
						}
						}
						break;
					}
					} 
				}
				setState(234);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FalseLiteralContext extends LiteralContext {
		public TerminalNode FALSE() { return getToken(htmlParser.FALSE, 0); }
		public FalseLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterFalseLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitFalseLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitFalseLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NoneLiteralContext extends LiteralContext {
		public TerminalNode NONE() { return getToken(htmlParser.NONE, 0); }
		public NoneLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterNoneLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitNoneLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitNoneLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DictLiteralContext extends LiteralContext {
		public TerminalNode LBRACE() { return getToken(htmlParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(htmlParser.RBRACE, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(htmlParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(htmlParser.COLON, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(htmlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(htmlParser.COMMA, i);
		}
		public DictLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterDictLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitDictLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitDictLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringLiteralContext extends LiteralContext {
		public TerminalNode STRING() { return getToken(htmlParser.STRING, 0); }
		public StringLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntLiteralContext extends LiteralContext {
		public TerminalNode INT() { return getToken(htmlParser.INT, 0); }
		public IntLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterIntLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitIntLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListLiteralContext extends LiteralContext {
		public TerminalNode LBRACK() { return getToken(htmlParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(htmlParser.RBRACK, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(htmlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(htmlParser.COMMA, i);
		}
		public ListLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterListLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitListLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitListLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FloatLiteralContext extends LiteralContext {
		public TerminalNode FLOAT() { return getToken(htmlParser.FLOAT, 0); }
		public FloatLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterFloatLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitFloatLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitFloatLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TrueLiteralContext extends LiteralContext {
		public TerminalNode TRUE() { return getToken(htmlParser.TRUE, 0); }
		public TrueLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterTrueLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitTrueLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitTrueLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_literal);
		int _la;
		try {
			setState(270);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				_localctx = new IntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(235);
				match(INT);
				}
				break;
			case FLOAT:
				_localctx = new FloatLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(236);
				match(FLOAT);
				}
				break;
			case STRING:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(237);
				match(STRING);
				}
				break;
			case TRUE:
				_localctx = new TrueLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(238);
				match(TRUE);
				}
				break;
			case FALSE:
				_localctx = new FalseLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(239);
				match(FALSE);
				}
				break;
			case NONE:
				_localctx = new NoneLiteralContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(240);
				match(NONE);
				}
				break;
			case LBRACE:
				_localctx = new DictLiteralContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(241);
				match(LBRACE);
				setState(255);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 38)) & ~0x3f) == 0 && ((1L << (_la - 38)) & 1066401941L) != 0)) {
					{
					setState(242);
					expr(0);
					setState(243);
					match(COLON);
					setState(244);
					expr(0);
					setState(252);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(245);
						match(COMMA);
						setState(246);
						expr(0);
						setState(247);
						match(COLON);
						setState(248);
						expr(0);
						}
						}
						setState(254);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(257);
				match(RBRACE);
				}
				break;
			case LBRACK:
				_localctx = new ListLiteralContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(258);
				match(LBRACK);
				setState(267);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 38)) & ~0x3f) == 0 && ((1L << (_la - 38)) & 1066401941L) != 0)) {
					{
					setState(259);
					expr(0);
					setState(264);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(260);
						match(COMMA);
						setState(261);
						expr(0);
						}
						}
						setState(266);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(269);
				match(RBRACK);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaSingleTagContext extends ParserRuleContext {
		public TerminalNode TAG_START() { return getToken(htmlParser.TAG_START, 0); }
		public SingleJinjaTagContentContext singleJinjaTagContent() {
			return getRuleContext(SingleJinjaTagContentContext.class,0);
		}
		public TerminalNode TAG_END() { return getToken(htmlParser.TAG_END, 0); }
		public JinjaSingleTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaSingleTag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterJinjaSingleTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitJinjaSingleTag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitJinjaSingleTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaSingleTagContext jinjaSingleTag() throws RecognitionException {
		JinjaSingleTagContext _localctx = new JinjaSingleTagContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_jinjaSingleTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			match(TAG_START);
			setState(273);
			singleJinjaTagContent();
			setState(274);
			match(TAG_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SingleJinjaTagContentContext extends ParserRuleContext {
		public TerminalNode SET() { return getToken(htmlParser.SET, 0); }
		public TerminalNode ID() { return getToken(htmlParser.ID, 0); }
		public TerminalNode EQ() { return getToken(htmlParser.EQ, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode INCLUDE() { return getToken(htmlParser.INCLUDE, 0); }
		public TerminalNode STRING() { return getToken(htmlParser.STRING, 0); }
		public TerminalNode EXTENDS() { return getToken(htmlParser.EXTENDS, 0); }
		public SingleJinjaTagContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleJinjaTagContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterSingleJinjaTagContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitSingleJinjaTagContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitSingleJinjaTagContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleJinjaTagContentContext singleJinjaTagContent() throws RecognitionException {
		SingleJinjaTagContentContext _localctx = new SingleJinjaTagContentContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_singleJinjaTagContent);
		try {
			setState(284);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SET:
				enterOuterAlt(_localctx, 1);
				{
				setState(276);
				match(SET);
				setState(277);
				match(ID);
				setState(278);
				match(EQ);
				setState(279);
				expr(0);
				}
				break;
			case INCLUDE:
				enterOuterAlt(_localctx, 2);
				{
				setState(280);
				match(INCLUDE);
				setState(281);
				match(STRING);
				}
				break;
			case EXTENDS:
				enterOuterAlt(_localctx, 3);
				{
				setState(282);
				match(EXTENDS);
				setState(283);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FirstBlockJinjaTagContentContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(htmlParser.IF, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode ELIF() { return getToken(htmlParser.ELIF, 0); }
		public TerminalNode ELSE() { return getToken(htmlParser.ELSE, 0); }
		public TerminalNode FOR() { return getToken(htmlParser.FOR, 0); }
		public TerminalNode ID() { return getToken(htmlParser.ID, 0); }
		public TerminalNode IN() { return getToken(htmlParser.IN, 0); }
		public FirstBlockJinjaTagContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_firstBlockJinjaTagContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterFirstBlockJinjaTagContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitFirstBlockJinjaTagContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitFirstBlockJinjaTagContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FirstBlockJinjaTagContentContext firstBlockJinjaTagContent() throws RecognitionException {
		FirstBlockJinjaTagContentContext _localctx = new FirstBlockJinjaTagContentContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_firstBlockJinjaTagContent);
		try {
			setState(295);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IF:
				enterOuterAlt(_localctx, 1);
				{
				setState(286);
				match(IF);
				setState(287);
				expr(0);
				}
				break;
			case ELIF:
				enterOuterAlt(_localctx, 2);
				{
				setState(288);
				match(ELIF);
				setState(289);
				expr(0);
				}
				break;
			case ELSE:
				enterOuterAlt(_localctx, 3);
				{
				setState(290);
				match(ELSE);
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 4);
				{
				setState(291);
				match(FOR);
				setState(292);
				match(ID);
				setState(293);
				match(IN);
				setState(294);
				expr(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EndBlockJinjaTagContentContext extends ParserRuleContext {
		public TerminalNode ENDIF() { return getToken(htmlParser.ENDIF, 0); }
		public TerminalNode ENDFOR() { return getToken(htmlParser.ENDFOR, 0); }
		public EndBlockJinjaTagContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endBlockJinjaTagContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterEndBlockJinjaTagContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitEndBlockJinjaTagContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitEndBlockJinjaTagContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EndBlockJinjaTagContentContext endBlockJinjaTagContent() throws RecognitionException {
		EndBlockJinjaTagContentContext _localctx = new EndBlockJinjaTagContentContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_endBlockJinjaTagContent);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
			_la = _input.LA(1);
			if ( !(_la==ENDIF || _la==ENDFOR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaBlockContext extends ParserRuleContext {
		public JinjaForBlockContext jinjaForBlock() {
			return getRuleContext(JinjaForBlockContext.class,0);
		}
		public JinjaIfBlockContext jinjaIfBlock() {
			return getRuleContext(JinjaIfBlockContext.class,0);
		}
		public JinjaBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterJinjaBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitJinjaBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitJinjaBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaBlockContext jinjaBlock() throws RecognitionException {
		JinjaBlockContext _localctx = new JinjaBlockContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_jinjaBlock);
		try {
			setState(301);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(299);
				jinjaForBlock();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(300);
				jinjaIfBlock();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaForBlockContext extends ParserRuleContext {
		public List<TerminalNode> TAG_START() { return getTokens(htmlParser.TAG_START); }
		public TerminalNode TAG_START(int i) {
			return getToken(htmlParser.TAG_START, i);
		}
		public TerminalNode FOR() { return getToken(htmlParser.FOR, 0); }
		public TerminalNode ID() { return getToken(htmlParser.ID, 0); }
		public TerminalNode IN() { return getToken(htmlParser.IN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<TerminalNode> TAG_END() { return getTokens(htmlParser.TAG_END); }
		public TerminalNode TAG_END(int i) {
			return getToken(htmlParser.TAG_END, i);
		}
		public TerminalNode ENDFOR() { return getToken(htmlParser.ENDFOR, 0); }
		public List<HtmlElementContext> htmlElement() {
			return getRuleContexts(HtmlElementContext.class);
		}
		public HtmlElementContext htmlElement(int i) {
			return getRuleContext(HtmlElementContext.class,i);
		}
		public JinjaForBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaForBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterJinjaForBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitJinjaForBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitJinjaForBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaForBlockContext jinjaForBlock() throws RecognitionException {
		JinjaForBlockContext _localctx = new JinjaForBlockContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_jinjaForBlock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			match(TAG_START);
			setState(304);
			match(FOR);
			setState(305);
			match(ID);
			setState(306);
			match(IN);
			setState(307);
			expr(0);
			setState(308);
			match(TAG_END);
			setState(312);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(309);
					htmlElement();
					}
					} 
				}
				setState(314);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			}
			setState(315);
			match(TAG_START);
			setState(316);
			match(ENDFOR);
			setState(317);
			match(TAG_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaIfBlockContext extends ParserRuleContext {
		public List<TerminalNode> TAG_START() { return getTokens(htmlParser.TAG_START); }
		public TerminalNode TAG_START(int i) {
			return getToken(htmlParser.TAG_START, i);
		}
		public TerminalNode IF() { return getToken(htmlParser.IF, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> TAG_END() { return getTokens(htmlParser.TAG_END); }
		public TerminalNode TAG_END(int i) {
			return getToken(htmlParser.TAG_END, i);
		}
		public TerminalNode ENDIF() { return getToken(htmlParser.ENDIF, 0); }
		public List<HtmlElementContext> htmlElement() {
			return getRuleContexts(HtmlElementContext.class);
		}
		public HtmlElementContext htmlElement(int i) {
			return getRuleContext(HtmlElementContext.class,i);
		}
		public List<TerminalNode> ELIF() { return getTokens(htmlParser.ELIF); }
		public TerminalNode ELIF(int i) {
			return getToken(htmlParser.ELIF, i);
		}
		public TerminalNode ELSE() { return getToken(htmlParser.ELSE, 0); }
		public JinjaIfBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaIfBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).enterJinjaIfBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof htmlParserListener ) ((htmlParserListener)listener).exitJinjaIfBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof htmlParserVisitor ) return ((htmlParserVisitor<? extends T>)visitor).visitJinjaIfBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaIfBlockContext jinjaIfBlock() throws RecognitionException {
		JinjaIfBlockContext _localctx = new JinjaIfBlockContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_jinjaIfBlock);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			match(TAG_START);
			setState(320);
			match(IF);
			setState(321);
			expr(0);
			setState(322);
			match(TAG_END);
			setState(326);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(323);
					htmlElement();
					}
					} 
				}
				setState(328);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
			}
			setState(341);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(329);
					match(TAG_START);
					setState(330);
					match(ELIF);
					setState(331);
					expr(0);
					setState(332);
					match(TAG_END);
					setState(336);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(333);
							htmlElement();
							}
							} 
						}
						setState(338);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
					}
					}
					} 
				}
				setState(343);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			}
			setState(353);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				{
				setState(344);
				match(TAG_START);
				setState(345);
				match(ELSE);
				setState(346);
				match(TAG_END);
				setState(350);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(347);
						htmlElement();
						}
						} 
					}
					setState(352);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
				}
				}
				break;
			}
			setState(355);
			match(TAG_START);
			setState(356);
			match(ENDIF);
			setState(357);
			match(TAG_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 12:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 7);
		case 4:
			return precpred(_ctx, 6);
		case 5:
			return precpred(_ctx, 5);
		case 6:
			return precpred(_ctx, 4);
		case 7:
			return precpred(_ctx, 3);
		case 8:
			return precpred(_ctx, 16);
		case 9:
			return precpred(_ctx, 15);
		case 10:
			return precpred(_ctx, 14);
		case 11:
			return precpred(_ctx, 13);
		case 12:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001Q\u0168\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0001\u0000\u0003\u0000"+
		",\b\u0000\u0001\u0000\u0005\u0000/\b\u0000\n\u0000\f\u00002\t\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0003\u0001<\b\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0005\u0002A\b\u0002\n\u0002\f\u0002D\t\u0002\u0001\u0002\u0001"+
		"\u0002\u0005\u0002H\b\u0002\n\u0002\f\u0002K\t\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0003\u0002Q\b\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0005\u0002V\b\u0002\n\u0002\f\u0002Y\t\u0002\u0001"+
		"\u0002\u0003\u0002\\\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0003"+
		"\u0003a\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0005\u0005h\b\u0005\n\u0005\f\u0005k\t\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0005\u0006p\b\u0006\n\u0006\f\u0006s\t\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0005\b\u007f\b\b\n\b\f\b\u0082\t\b\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0003\n\u008a\b\n\u0001\u000b"+
		"\u0001\u000b\u0003\u000b\u008e\b\u000b\u0001\u000b\u0001\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0003\f\u009d\b\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0005\f\u00c4\b\f\n\f\f\f\u00c7\t\f"+
		"\u0003\f\u00c9\b\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0005\f\u00d3\b\f\n\f\f\f\u00d6\t\f\u0003\f\u00d8\b\f\u0001"+
		"\f\u0003\f\u00db\b\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00e2"+
		"\b\f\u0001\f\u0003\f\u00e5\b\f\u0005\f\u00e7\b\f\n\f\f\f\u00ea\t\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u00fb\b\r\n\r\f\r\u00fe"+
		"\t\r\u0003\r\u0100\b\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r"+
		"\u0107\b\r\n\r\f\r\u010a\t\r\u0003\r\u010c\b\r\u0001\r\u0003\r\u010f\b"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0003\u000f\u011d\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010"+
		"\u0128\b\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0003\u0012"+
		"\u012e\b\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0005\u0013\u0137\b\u0013\n\u0013\f\u0013\u013a"+
		"\t\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u0145\b\u0014\n"+
		"\u0014\f\u0014\u0148\t\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0005\u0014\u014f\b\u0014\n\u0014\f\u0014\u0152\t\u0014"+
		"\u0005\u0014\u0154\b\u0014\n\u0014\f\u0014\u0157\t\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u015d\b\u0014\n\u0014\f\u0014"+
		"\u0160\t\u0014\u0003\u0014\u0162\b\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0000\u0001\u0018\u0015\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \""+
		"$&(\u0000\u0005\u0001\u0000.0\u0001\u0000,-\u0001\u000047\u0001\u0000"+
		"23\u0002\u0000JJOO\u0193\u0000+\u0001\u0000\u0000\u0000\u0002;\u0001\u0000"+
		"\u0000\u0000\u0004[\u0001\u0000\u0000\u0000\u0006]\u0001\u0000\u0000\u0000"+
		"\bb\u0001\u0000\u0000\u0000\ni\u0001\u0000\u0000\u0000\fl\u0001\u0000"+
		"\u0000\u0000\u000ev\u0001\u0000\u0000\u0000\u0010{\u0001\u0000\u0000\u0000"+
		"\u0012\u0083\u0001\u0000\u0000\u0000\u0014\u0089\u0001\u0000\u0000\u0000"+
		"\u0016\u008b\u0001\u0000\u0000\u0000\u0018\u009c\u0001\u0000\u0000\u0000"+
		"\u001a\u010e\u0001\u0000\u0000\u0000\u001c\u0110\u0001\u0000\u0000\u0000"+
		"\u001e\u011c\u0001\u0000\u0000\u0000 \u0127\u0001\u0000\u0000\u0000\""+
		"\u0129\u0001\u0000\u0000\u0000$\u012d\u0001\u0000\u0000\u0000&\u012f\u0001"+
		"\u0000\u0000\u0000(\u013f\u0001\u0000\u0000\u0000*,\u0005\u0006\u0000"+
		"\u0000+*\u0001\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000,0\u0001\u0000"+
		"\u0000\u0000-/\u0003\u0002\u0001\u0000.-\u0001\u0000\u0000\u0000/2\u0001"+
		"\u0000\u0000\u00000.\u0001\u0000\u0000\u000001\u0001\u0000\u0000\u0000"+
		"13\u0001\u0000\u0000\u000020\u0001\u0000\u0000\u000034\u0005\u0000\u0000"+
		"\u00014\u0001\u0001\u0000\u0000\u00005<\u0003\u0004\u0002\u00006<\u0003"+
		"\b\u0004\u00007<\u0003\u0012\t\u00008<\u0003\u0014\n\u00009<\u0003\u0016"+
		"\u000b\u0000:<\u0005\t\u0000\u0000;5\u0001\u0000\u0000\u0000;6\u0001\u0000"+
		"\u0000\u0000;7\u0001\u0000\u0000\u0000;8\u0001\u0000\u0000\u0000;9\u0001"+
		"\u0000\u0000\u0000;:\u0001\u0000\u0000\u0000<\u0003\u0001\u0000\u0000"+
		"\u0000=>\u0005\b\u0000\u0000>B\u0005\u000f\u0000\u0000?A\u0003\u0006\u0003"+
		"\u0000@?\u0001\u0000\u0000\u0000AD\u0001\u0000\u0000\u0000B@\u0001\u0000"+
		"\u0000\u0000BC\u0001\u0000\u0000\u0000CE\u0001\u0000\u0000\u0000DB\u0001"+
		"\u0000\u0000\u0000EI\u0005\f\u0000\u0000FH\u0003\u0002\u0001\u0000GF\u0001"+
		"\u0000\u0000\u0000HK\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000"+
		"IJ\u0001\u0000\u0000\u0000JP\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000"+
		"\u0000LM\u0005\b\u0000\u0000MN\u0005\u000e\u0000\u0000NO\u0005\u000f\u0000"+
		"\u0000OQ\u0005\f\u0000\u0000PL\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000"+
		"\u0000Q\\\u0001\u0000\u0000\u0000RS\u0005\b\u0000\u0000SW\u0005\u000f"+
		"\u0000\u0000TV\u0003\u0006\u0003\u0000UT\u0001\u0000\u0000\u0000VY\u0001"+
		"\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000WX\u0001\u0000\u0000\u0000"+
		"XZ\u0001\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000Z\\\u0005\r\u0000"+
		"\u0000[=\u0001\u0000\u0000\u0000[R\u0001\u0000\u0000\u0000\\\u0005\u0001"+
		"\u0000\u0000\u0000]`\u0005\u0010\u0000\u0000^_\u0005\u0011\u0000\u0000"+
		"_a\u0005\u0012\u0000\u0000`^\u0001\u0000\u0000\u0000`a\u0001\u0000\u0000"+
		"\u0000a\u0007\u0001\u0000\u0000\u0000bc\u0005\u0007\u0000\u0000cd\u0003"+
		"\n\u0005\u0000de\u0005\u0015\u0000\u0000e\t\u0001\u0000\u0000\u0000fh"+
		"\u0003\f\u0006\u0000gf\u0001\u0000\u0000\u0000hk\u0001\u0000\u0000\u0000"+
		"ig\u0001\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000j\u000b\u0001\u0000"+
		"\u0000\u0000ki\u0001\u0000\u0000\u0000lm\u0005\u0019\u0000\u0000mq\u0005"+
		"\u0018\u0000\u0000np\u0003\u000e\u0007\u0000on\u0001\u0000\u0000\u0000"+
		"ps\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000\u0000qr\u0001\u0000\u0000"+
		"\u0000rt\u0001\u0000\u0000\u0000sq\u0001\u0000\u0000\u0000tu\u0005\u001f"+
		"\u0000\u0000u\r\u0001\u0000\u0000\u0000vw\u0005\u001a\u0000\u0000wx\u0005"+
		"\u001d\u0000\u0000xy\u0003\u0010\b\u0000yz\u0005\u001c\u0000\u0000z\u000f"+
		"\u0001\u0000\u0000\u0000{\u0080\u0005\u001b\u0000\u0000|}\u0005\u001e"+
		"\u0000\u0000}\u007f\u0005\u001b\u0000\u0000~|\u0001\u0000\u0000\u0000"+
		"\u007f\u0082\u0001\u0000\u0000\u0000\u0080~\u0001\u0000\u0000\u0000\u0080"+
		"\u0081\u0001\u0000\u0000\u0000\u0081\u0011\u0001\u0000\u0000\u0000\u0082"+
		"\u0080\u0001\u0000\u0000\u0000\u0083\u0084\u0005\u0003\u0000\u0000\u0084"+
		"\u0085\u0003\u0018\f\u0000\u0085\u0086\u0005!\u0000\u0000\u0086\u0013"+
		"\u0001\u0000\u0000\u0000\u0087\u008a\u0003\u001c\u000e\u0000\u0088\u008a"+
		"\u0003$\u0012\u0000\u0089\u0087\u0001\u0000\u0000\u0000\u0089\u0088\u0001"+
		"\u0000\u0000\u0000\u008a\u0015\u0001\u0000\u0000\u0000\u008b\u008d\u0005"+
		"\u0005\u0000\u0000\u008c\u008e\u0005Q\u0000\u0000\u008d\u008c\u0001\u0000"+
		"\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000\u008e\u008f\u0001\u0000"+
		"\u0000\u0000\u008f\u0090\u0005P\u0000\u0000\u0090\u0017\u0001\u0000\u0000"+
		"\u0000\u0091\u0092\u0006\f\uffff\uffff\u0000\u0092\u009d\u0003\u001a\r"+
		"\u0000\u0093\u009d\u0005@\u0000\u0000\u0094\u0095\u0005-\u0000\u0000\u0095"+
		"\u009d\u0003\u0018\f\f\u0096\u0097\u0005:\u0000\u0000\u0097\u009d\u0003"+
		"\u0018\f\u000b\u0098\u0099\u0005&\u0000\u0000\u0099\u009a\u0003\u0018"+
		"\f\u0000\u009a\u009b\u0005\'\u0000\u0000\u009b\u009d\u0001\u0000\u0000"+
		"\u0000\u009c\u0091\u0001\u0000\u0000\u0000\u009c\u0093\u0001\u0000\u0000"+
		"\u0000\u009c\u0094\u0001\u0000\u0000\u0000\u009c\u0096\u0001\u0000\u0000"+
		"\u0000\u009c\u0098\u0001\u0000\u0000\u0000\u009d\u00e8\u0001\u0000\u0000"+
		"\u0000\u009e\u009f\n\n\u0000\u0000\u009f\u00a0\u00051\u0000\u0000\u00a0"+
		"\u00e7\u0003\u0018\f\u000b\u00a1\u00a2\n\t\u0000\u0000\u00a2\u00a3\u0007"+
		"\u0000\u0000\u0000\u00a3\u00e7\u0003\u0018\f\n\u00a4\u00a5\n\b\u0000\u0000"+
		"\u00a5\u00a6\u0007\u0001\u0000\u0000\u00a6\u00e7\u0003\u0018\f\t\u00a7"+
		"\u00a8\n\u0007\u0000\u0000\u00a8\u00a9\u0007\u0002\u0000\u0000\u00a9\u00e7"+
		"\u0003\u0018\f\b\u00aa\u00ab\n\u0006\u0000\u0000\u00ab\u00ac\u0007\u0003"+
		"\u0000\u0000\u00ac\u00e7\u0003\u0018\f\u0007\u00ad\u00ae\n\u0005\u0000"+
		"\u0000\u00ae\u00af\u00058\u0000\u0000\u00af\u00e7\u0003\u0018\f\u0006"+
		"\u00b0\u00b1\n\u0004\u0000\u0000\u00b1\u00b2\u00059\u0000\u0000\u00b2"+
		"\u00e7\u0003\u0018\f\u0005\u00b3\u00b4\n\u0003\u0000\u0000\u00b4\u00b5"+
		"\u0005;\u0000\u0000\u00b5\u00e7\u0003\u0018\f\u0004\u00b6\u00b7\n\u0010"+
		"\u0000\u0000\u00b7\u00b8\u0005\"\u0000\u0000\u00b8\u00e7\u0005@\u0000"+
		"\u0000\u00b9\u00ba\n\u000f\u0000\u0000\u00ba\u00bb\u0005(\u0000\u0000"+
		"\u00bb\u00bc\u0003\u0018\f\u0000\u00bc\u00bd\u0005)\u0000\u0000\u00bd"+
		"\u00e7\u0001\u0000\u0000\u0000\u00be\u00bf\n\u000e\u0000\u0000\u00bf\u00c8"+
		"\u0005&\u0000\u0000\u00c0\u00c5\u0003\u0018\f\u0000\u00c1\u00c2\u0005"+
		"#\u0000\u0000\u00c2\u00c4\u0003\u0018\f\u0000\u00c3\u00c1\u0001\u0000"+
		"\u0000\u0000\u00c4\u00c7\u0001\u0000\u0000\u0000\u00c5\u00c3\u0001\u0000"+
		"\u0000\u0000\u00c5\u00c6\u0001\u0000\u0000\u0000\u00c6\u00c9\u0001\u0000"+
		"\u0000\u0000\u00c7\u00c5\u0001\u0000\u0000\u0000\u00c8\u00c0\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c9\u0001\u0000\u0000\u0000\u00c9\u00ca\u0001\u0000"+
		"\u0000\u0000\u00ca\u00e7\u0005\'\u0000\u0000\u00cb\u00cc\n\r\u0000\u0000"+
		"\u00cc\u00cd\u0005%\u0000\u0000\u00cd\u00da\u0005@\u0000\u0000\u00ce\u00d7"+
		"\u0005&\u0000\u0000\u00cf\u00d4\u0003\u0018\f\u0000\u00d0\u00d1\u0005"+
		"#\u0000\u0000\u00d1\u00d3\u0003\u0018\f\u0000\u00d2\u00d0\u0001\u0000"+
		"\u0000\u0000\u00d3\u00d6\u0001\u0000\u0000\u0000\u00d4\u00d2\u0001\u0000"+
		"\u0000\u0000\u00d4\u00d5\u0001\u0000\u0000\u0000\u00d5\u00d8\u0001\u0000"+
		"\u0000\u0000\u00d6\u00d4\u0001\u0000\u0000\u0000\u00d7\u00cf\u0001\u0000"+
		"\u0000\u0000\u00d7\u00d8\u0001\u0000\u0000\u0000\u00d8\u00d9\u0001\u0000"+
		"\u0000\u0000\u00d9\u00db\u0005\'\u0000\u0000\u00da\u00ce\u0001\u0000\u0000"+
		"\u0000\u00da\u00db\u0001\u0000\u0000\u0000\u00db\u00e7\u0001\u0000\u0000"+
		"\u0000\u00dc\u00dd\n\u0002\u0000\u0000\u00dd\u00de\u0005<\u0000\u0000"+
		"\u00de\u00e4\u0005@\u0000\u0000\u00df\u00e1\u0005&\u0000\u0000\u00e0\u00e2"+
		"\u0003\u0018\f\u0000\u00e1\u00e0\u0001\u0000\u0000\u0000\u00e1\u00e2\u0001"+
		"\u0000\u0000\u0000\u00e2\u00e3\u0001\u0000\u0000\u0000\u00e3\u00e5\u0005"+
		"\'\u0000\u0000\u00e4\u00df\u0001\u0000\u0000\u0000\u00e4\u00e5\u0001\u0000"+
		"\u0000\u0000\u00e5\u00e7\u0001\u0000\u0000\u0000\u00e6\u009e\u0001\u0000"+
		"\u0000\u0000\u00e6\u00a1\u0001\u0000\u0000\u0000\u00e6\u00a4\u0001\u0000"+
		"\u0000\u0000\u00e6\u00a7\u0001\u0000\u0000\u0000\u00e6\u00aa\u0001\u0000"+
		"\u0000\u0000\u00e6\u00ad\u0001\u0000\u0000\u0000\u00e6\u00b0\u0001\u0000"+
		"\u0000\u0000\u00e6\u00b3\u0001\u0000\u0000\u0000\u00e6\u00b6\u0001\u0000"+
		"\u0000\u0000\u00e6\u00b9\u0001\u0000\u0000\u0000\u00e6\u00be\u0001\u0000"+
		"\u0000\u0000\u00e6\u00cb\u0001\u0000\u0000\u0000\u00e6\u00dc\u0001\u0000"+
		"\u0000\u0000\u00e7\u00ea\u0001\u0000\u0000\u0000\u00e8\u00e6\u0001\u0000"+
		"\u0000\u0000\u00e8\u00e9\u0001\u0000\u0000\u0000\u00e9\u0019\u0001\u0000"+
		"\u0000\u0000\u00ea\u00e8\u0001\u0000\u0000\u0000\u00eb\u010f\u0005A\u0000"+
		"\u0000\u00ec\u010f\u0005B\u0000\u0000\u00ed\u010f\u0005C\u0000\u0000\u00ee"+
		"\u010f\u0005>\u0000\u0000\u00ef\u010f\u0005?\u0000\u0000\u00f0\u010f\u0005"+
		"=\u0000\u0000\u00f1\u00ff\u0005*\u0000\u0000\u00f2\u00f3\u0003\u0018\f"+
		"\u0000\u00f3\u00f4\u0005$\u0000\u0000\u00f4\u00fc\u0003\u0018\f\u0000"+
		"\u00f5\u00f6\u0005#\u0000\u0000\u00f6\u00f7\u0003\u0018\f\u0000\u00f7"+
		"\u00f8\u0005$\u0000\u0000\u00f8\u00f9\u0003\u0018\f\u0000\u00f9\u00fb"+
		"\u0001\u0000\u0000\u0000\u00fa\u00f5\u0001\u0000\u0000\u0000\u00fb\u00fe"+
		"\u0001\u0000\u0000\u0000\u00fc\u00fa\u0001\u0000\u0000\u0000\u00fc\u00fd"+
		"\u0001\u0000\u0000\u0000\u00fd\u0100\u0001\u0000\u0000\u0000\u00fe\u00fc"+
		"\u0001\u0000\u0000\u0000\u00ff\u00f2\u0001\u0000\u0000\u0000\u00ff\u0100"+
		"\u0001\u0000\u0000\u0000\u0100\u0101\u0001\u0000\u0000\u0000\u0101\u010f"+
		"\u0005+\u0000\u0000\u0102\u010b\u0005(\u0000\u0000\u0103\u0108\u0003\u0018"+
		"\f\u0000\u0104\u0105\u0005#\u0000\u0000\u0105\u0107\u0003\u0018\f\u0000"+
		"\u0106\u0104\u0001\u0000\u0000\u0000\u0107\u010a\u0001\u0000\u0000\u0000"+
		"\u0108\u0106\u0001\u0000\u0000\u0000\u0108\u0109\u0001\u0000\u0000\u0000"+
		"\u0109\u010c\u0001\u0000\u0000\u0000\u010a\u0108\u0001\u0000\u0000\u0000"+
		"\u010b\u0103\u0001\u0000\u0000\u0000\u010b\u010c\u0001\u0000\u0000\u0000"+
		"\u010c\u010d\u0001\u0000\u0000\u0000\u010d\u010f\u0005)\u0000\u0000\u010e"+
		"\u00eb\u0001\u0000\u0000\u0000\u010e\u00ec\u0001\u0000\u0000\u0000\u010e"+
		"\u00ed\u0001\u0000\u0000\u0000\u010e\u00ee\u0001\u0000\u0000\u0000\u010e"+
		"\u00ef\u0001\u0000\u0000\u0000\u010e\u00f0\u0001\u0000\u0000\u0000\u010e"+
		"\u00f1\u0001\u0000\u0000\u0000\u010e\u0102\u0001\u0000\u0000\u0000\u010f"+
		"\u001b\u0001\u0000\u0000\u0000\u0110\u0111\u0005\u0004\u0000\u0000\u0111"+
		"\u0112\u0003\u001e\u000f\u0000\u0112\u0113\u0005F\u0000\u0000\u0113\u001d"+
		"\u0001\u0000\u0000\u0000\u0114\u0115\u0005K\u0000\u0000\u0115\u0116\u0005"+
		"@\u0000\u0000\u0116\u0117\u00052\u0000\u0000\u0117\u011d\u0003\u0018\f"+
		"\u0000\u0118\u0119\u0005L\u0000\u0000\u0119\u011d\u0005C\u0000\u0000\u011a"+
		"\u011b\u0005M\u0000\u0000\u011b\u011d\u0005C\u0000\u0000\u011c\u0114\u0001"+
		"\u0000\u0000\u0000\u011c\u0118\u0001\u0000\u0000\u0000\u011c\u011a\u0001"+
		"\u0000\u0000\u0000\u011d\u001f\u0001\u0000\u0000\u0000\u011e\u011f\u0005"+
		"G\u0000\u0000\u011f\u0128\u0003\u0018\f\u0000\u0120\u0121\u0005H\u0000"+
		"\u0000\u0121\u0128\u0003\u0018\f\u0000\u0122\u0128\u0005I\u0000\u0000"+
		"\u0123\u0124\u0005N\u0000\u0000\u0124\u0125\u0005@\u0000\u0000\u0125\u0126"+
		"\u0005;\u0000\u0000\u0126\u0128\u0003\u0018\f\u0000\u0127\u011e\u0001"+
		"\u0000\u0000\u0000\u0127\u0120\u0001\u0000\u0000\u0000\u0127\u0122\u0001"+
		"\u0000\u0000\u0000\u0127\u0123\u0001\u0000\u0000\u0000\u0128!\u0001\u0000"+
		"\u0000\u0000\u0129\u012a\u0007\u0004\u0000\u0000\u012a#\u0001\u0000\u0000"+
		"\u0000\u012b\u012e\u0003&\u0013\u0000\u012c\u012e\u0003(\u0014\u0000\u012d"+
		"\u012b\u0001\u0000\u0000\u0000\u012d\u012c\u0001\u0000\u0000\u0000\u012e"+
		"%\u0001\u0000\u0000\u0000\u012f\u0130\u0005\u0004\u0000\u0000\u0130\u0131"+
		"\u0005N\u0000\u0000\u0131\u0132\u0005@\u0000\u0000\u0132\u0133\u0005;"+
		"\u0000\u0000\u0133\u0134\u0003\u0018\f\u0000\u0134\u0138\u0005F\u0000"+
		"\u0000\u0135\u0137\u0003\u0002\u0001\u0000\u0136\u0135\u0001\u0000\u0000"+
		"\u0000\u0137\u013a\u0001\u0000\u0000\u0000\u0138\u0136\u0001\u0000\u0000"+
		"\u0000\u0138\u0139\u0001\u0000\u0000\u0000\u0139\u013b\u0001\u0000\u0000"+
		"\u0000\u013a\u0138\u0001\u0000\u0000\u0000\u013b\u013c\u0005\u0004\u0000"+
		"\u0000\u013c\u013d\u0005O\u0000\u0000\u013d\u013e\u0005F\u0000\u0000\u013e"+
		"\'\u0001\u0000\u0000\u0000\u013f\u0140\u0005\u0004\u0000\u0000\u0140\u0141"+
		"\u0005G\u0000\u0000\u0141\u0142\u0003\u0018\f\u0000\u0142\u0146\u0005"+
		"F\u0000\u0000\u0143\u0145\u0003\u0002\u0001\u0000\u0144\u0143\u0001\u0000"+
		"\u0000\u0000\u0145\u0148\u0001\u0000\u0000\u0000\u0146\u0144\u0001\u0000"+
		"\u0000\u0000\u0146\u0147\u0001\u0000\u0000\u0000\u0147\u0155\u0001\u0000"+
		"\u0000\u0000\u0148\u0146\u0001\u0000\u0000\u0000\u0149\u014a\u0005\u0004"+
		"\u0000\u0000\u014a\u014b\u0005H\u0000\u0000\u014b\u014c\u0003\u0018\f"+
		"\u0000\u014c\u0150\u0005F\u0000\u0000\u014d\u014f\u0003\u0002\u0001\u0000"+
		"\u014e\u014d\u0001\u0000\u0000\u0000\u014f\u0152\u0001\u0000\u0000\u0000"+
		"\u0150\u014e\u0001\u0000\u0000\u0000\u0150\u0151\u0001\u0000\u0000\u0000"+
		"\u0151\u0154\u0001\u0000\u0000\u0000\u0152\u0150\u0001\u0000\u0000\u0000"+
		"\u0153\u0149\u0001\u0000\u0000\u0000\u0154\u0157\u0001\u0000\u0000\u0000"+
		"\u0155\u0153\u0001\u0000\u0000\u0000\u0155\u0156\u0001\u0000\u0000\u0000"+
		"\u0156\u0161\u0001\u0000\u0000\u0000\u0157\u0155\u0001\u0000\u0000\u0000"+
		"\u0158\u0159\u0005\u0004\u0000\u0000\u0159\u015a\u0005I\u0000\u0000\u015a"+
		"\u015e\u0005F\u0000\u0000\u015b\u015d\u0003\u0002\u0001\u0000\u015c\u015b"+
		"\u0001\u0000\u0000\u0000\u015d\u0160\u0001\u0000\u0000\u0000\u015e\u015c"+
		"\u0001\u0000\u0000\u0000\u015e\u015f\u0001\u0000\u0000\u0000\u015f\u0162"+
		"\u0001\u0000\u0000\u0000\u0160\u015e\u0001\u0000\u0000\u0000\u0161\u0158"+
		"\u0001\u0000\u0000\u0000\u0161\u0162\u0001\u0000\u0000\u0000\u0162\u0163"+
		"\u0001\u0000\u0000\u0000\u0163\u0164\u0005\u0004\u0000\u0000\u0164\u0165"+
		"\u0005J\u0000\u0000\u0165\u0166\u0005F\u0000\u0000\u0166)\u0001\u0000"+
		"\u0000\u0000&+0;BIPW[`iq\u0080\u0089\u008d\u009c\u00c5\u00c8\u00d4\u00d7"+
		"\u00da\u00e1\u00e4\u00e6\u00e8\u00fc\u00ff\u0108\u010b\u010e\u011c\u0127"+
		"\u012d\u0138\u0146\u0150\u0155\u015e\u0161";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}