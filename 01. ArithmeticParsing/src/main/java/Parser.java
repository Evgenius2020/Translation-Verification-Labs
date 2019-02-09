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
        long value = parseTerm();
        while ((current.type == LexemeType.PLUS) || (current.type == LexemeType.MINUS)) {
            var current = getCurrentAndUpdate();
            if (current.type == LexemeType.PLUS)
                value += parseTerm();
            else
                value -= parseTerm();
        }
        return value;
    }

    long parseTerm() throws IOException, ParsingException, ArithmeticException {
        long value = parseFactor();
        while ((current.type == LexemeType.MULTIPLY) || (current.type == LexemeType.DIVIDE)) {
            var current = getCurrentAndUpdate();
            if (current.type == LexemeType.MULTIPLY)
                value *= parseFactor();
            else
                value /= parseFactor();
        }
        return value;
    }

    long parseFactor() throws IOException, ParsingException {
        long power = parsePower();
        if (current.type == LexemeType.POWER) {
            getCurrentAndUpdate();
            power = (long)Math.pow(power, parseFactor());
        }

        return power;
    }

    long parsePower() throws IOException, ParsingException {
        if (current.type == LexemeType.MINUS) {
            getCurrentAndUpdate();
            return -parseAtom();
        }
        return parseAtom();
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
