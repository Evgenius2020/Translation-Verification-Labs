public class ParsingException extends Exception {
    private final ParsingExceptionType type;
    private final String message;

    public ParsingExceptionType getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ParsingException(ParsingExceptionType type, String message) {
        this.type = type;
        this.message = message;
    }
}
