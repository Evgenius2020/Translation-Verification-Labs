import java.io.IOException;
import java.io.InputStream;

public class Lexer {
    private InputStream stream;
    private int current;

    public Lexer(InputStream stream) throws IOException {
        this.stream = stream;
        this.current = stream.read();
    }

    private int getCurrentAndUpdate() throws IOException {
        int current = this.current;
        this.current = stream.read();
        return current;
    }

    public Lexeme getLexeme() throws IOException, ParsingException {
        while(current == ' ')
            getCurrentAndUpdate();
        int current = getCurrentAndUpdate();

        if (current == -1)
            return new Lexeme(LexemeType.EOF, "EOF");
        else if(current == '+')
            return new Lexeme(LexemeType.PLUS, "+");
        else if(current == '-')
            return new Lexeme(LexemeType.MINUS, "-");
        else if(current == '*')
            return new Lexeme(LexemeType.MULTIPLY, "*");
        else if(current == '/')
            return new Lexeme(LexemeType.DIVIDE, "/");
        else if(current == '^')
            return new Lexeme(LexemeType.POWER, "^");
        else if(current == '(')
            return new Lexeme(LexemeType.OPENING_BRACKET, "(");
        else if(current == ')')
            return new Lexeme(LexemeType.CLOSING_BRACKET, ")");
        else if(Character.isDigit(current)) {
            StringBuilder value = new StringBuilder();
            value.append((char)current);
            while(Character.isDigit(this.current)) {
                value.append((char)getCurrentAndUpdate());
            }
            return new Lexeme(LexemeType.NUMBER, value.toString());
        }
        else
            throw new ParsingException(
                    ParsingExceptionType.UNEXPECTED_CHARACTER, String.format("Unexpected character '%c'", current));
    }
}
