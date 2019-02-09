class ParsingException extends Exception {
    private final ParsingExceptionType type;
    private final String message;

    ParsingExceptionType getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    ParsingException(ParsingExceptionType type, String message) {
        this.type = type;
        this.message = message;
    }
}
