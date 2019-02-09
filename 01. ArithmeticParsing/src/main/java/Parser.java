import java.io.IOException;

class Parser {
    private Lexeme current;
    private final Lexer lexer;

    Parser(Lexer lexer) throws IOException, ParsingException {
        this.lexer = lexer;
        this.current = lexer.getLexeme();
    }

    private Lexeme getCurrentAndUpdate() throws IOException, ParsingException {
        Lexeme current = this.current;
        this.current = lexer.getLexeme();
        return current;
    }

    long parseExpr() throws IOException, ParsingException {
        long temp = parsePower();
        while ((current.type == LexemeType.PLUS) || (current.type == LexemeType.MINUS)) {
            var current = getCurrentAndUpdate();
            if (current.type == LexemeType.PLUS)
                temp += parsePower();
            else
                temp -= parsePower();
        }
        return temp;
    }

    long parsePower() throws IOException, ParsingException {
        if (current.type != LexemeType.MINUS)
            return parseAtom();

        getCurrentAndUpdate();
        return -parseAtom();
    }

    long parseAtom() throws IOException, ParsingException {
        if (current.type == LexemeType.OPENING_BRACKET) {
            getCurrentAndUpdate();
            long expr = parseExpr();
            if (current.type == LexemeType.CLOSING_BRACKET) {
                getCurrentAndUpdate();
                return expr;
            }
        } else if (current.type == LexemeType.NUMBER) {
            long value;
            try {
                value = Long.parseLong(current.text);
            } catch (NumberFormatException e) {
                throw new ParsingException(
                        ParsingExceptionType.LONG_NUMBER, String.format("Number is too long '%s'", current.text));
            }
            getCurrentAndUpdate();
            return value;
        }
        throw new ParsingException(
                ParsingExceptionType.UNEXPECTED_LEXEME, String.format("Unexpected lexeme '%s'", current.text));
    }
}
