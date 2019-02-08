import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

class LexerTest {
    @Test
    void getLexemePositiveInputTest() throws IOException, LexerParsingException {
        ArrayList<Lexeme> lexemesExpected = new ArrayList<>();
        lexemesExpected.add(new Lexeme(LexemeType.PLUS, "+"));
        lexemesExpected.add(new Lexeme(LexemeType.MINUS, "-"));
        lexemesExpected.add(new Lexeme(LexemeType.MULTIPLY, "*"));
        lexemesExpected.add(new Lexeme(LexemeType.DIVIDE, "/"));
        lexemesExpected.add(new Lexeme(LexemeType.POWER, "^"));

        lexemesExpected.add(new Lexeme(LexemeType.OPENING_BRACKET, "("));
        lexemesExpected.add(new Lexeme(LexemeType.CLOSING_BRACKET, ")"));

        lexemesExpected.add(new Lexeme(LexemeType.NUMBER, "1234567890"));
        lexemesExpected.add(new Lexeme(LexemeType.NUMBER, "2345678901"));
        lexemesExpected.add(new Lexeme(LexemeType.NUMBER, "3456789012"));
        lexemesExpected.add(new Lexeme(LexemeType.NUMBER, "4567890123"));
        lexemesExpected.add(new Lexeme(LexemeType.NUMBER, "5678901234"));
        lexemesExpected.add(new Lexeme(LexemeType.NUMBER, "6789012345"));
        lexemesExpected.add(new Lexeme(LexemeType.NUMBER, "7890123456"));
        lexemesExpected.add(new Lexeme(LexemeType.NUMBER, "8901234567"));
        lexemesExpected.add(new Lexeme(LexemeType.NUMBER, "9012345678"));
        lexemesExpected.add(new Lexeme(LexemeType.NUMBER,
                "12345678984012938219847164871264812694012094120898808914212907289479104791824987"));

        lexemesExpected.add(new Lexeme(LexemeType.EOF, "EOF"));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lexemesExpected.size() - 1; i++) {
            stringBuilder.append(" ".repeat(5));
            stringBuilder.append(lexemesExpected.get(i).text);
            stringBuilder.append(" ".repeat(5));
        }
        Lexer lexer = new Lexer(new ByteArrayInputStream(stringBuilder.toString().getBytes(StandardCharsets.UTF_8)));

        ArrayList<Lexeme> lexemesActual = new ArrayList<>();
        Lexeme current;
        do {
            current = lexer.getLexeme();
            lexemesActual.add(current);
        } while(current.type != LexemeType.EOF);

        assert lexemesActual.size() == lexemesExpected.size();

        for(int i = 0; i < lexemesExpected.size(); i++) {
            Lexeme lexemeExpected = lexemesExpected.get(i);
            Lexeme lexemeActual = lexemesActual.get(i);
            assert lexemeActual.type.equals(lexemeExpected.type);
            assert lexemeActual.text.equals(lexemeExpected.text);
        }

        for(int i = 0; i < 255; i++) {
            Lexeme lexeme = lexer.getLexeme();
            assert lexeme.type.equals(LexemeType.EOF);
            assert lexeme.text.equals("EOF");
        }
    }

    @Test
    void getLexemeBadSymbolTest() throws IOException {
        Lexer lexer = new Lexer(new ByteArrayInputStream("2 + 2a".getBytes(StandardCharsets.UTF_8)));
        try {
            lexer.getLexeme();
            lexer.getLexeme();
            lexer.getLexeme();
        }
        catch (LexerParsingException e) {
            assert e.getMessage().equals("Unexpected character 'a'");
        }
    }
}