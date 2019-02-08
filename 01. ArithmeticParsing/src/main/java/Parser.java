import java.io.IOException;

public class Parser {
    Lexeme current;
    Lexer lexer;

    public Parser(Lexer lexer) throws IOException, ParsingException {
        this.lexer = lexer;
        this.current = lexer.getLexeme();
    }

    private Lexeme getCurrentAndUpdate() throws IOException, ParsingException {
        Lexeme current = this.current;
        this.current = lexer.getLexeme();
        return current;
    }

    long parseExpr() throws IOException, ParsingException {
        long temp = parseAtom();
        while((current.type == LexemeType.PLUS) || (current.type == LexemeType.MINUS)) {
            var current = getCurrentAndUpdate();
            if (current.type == LexemeType.PLUS)
                temp += parseAtom();
            else
                temp -= parseAtom();
        }
        return temp;
    }

    public long parseAtom() throws IOException, ParsingException {
        if (current.type != LexemeType.NUMBER)
            throw new ParsingException(
                    ParsingExceptionType.UNEXPECTED_LEXEME, String.format("Unexpected lexeme '%s'", current.text));
        long value;
        try {
            value = Long.parseLong(current.text);
        } catch (NumberFormatException e) {
            throw new ParsingException(
                    ParsingExceptionType.LONG_NUMBER, String.format("Number is too long '%s'",current.text));
        }

        getCurrentAndUpdate();
        return value;
    }
}
