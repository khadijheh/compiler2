
package semantic;

public class SemanticError extends RuntimeException {

    /** Severity level for semantic diagnostics. */
    public enum Severity {
        ERROR,
        WARNING
    }

    private final int line;
    private final Severity severity;

    public SemanticError(String message, int line) {
        this(message, line, Severity.ERROR);
    }

    public SemanticError(String message, int line, Severity severity) {
        super("[Semantic " + severity + "] Line " + line + ": " + message);
        this.line = line;
        this.severity = severity;
    }

    public int getLine() {
        return line;
    }

    public Severity getSeverity() {
        return severity;
    }

    public boolean isWarning() {
        return severity == Severity.WARNING;
    }

    public boolean isError() {
        return severity == Severity.ERROR;
    }
}