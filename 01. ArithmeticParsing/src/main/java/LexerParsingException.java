public class LexerParsingException extends Exception {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public LexerParsingException(String message) {
        this.message = message;
    }
}
