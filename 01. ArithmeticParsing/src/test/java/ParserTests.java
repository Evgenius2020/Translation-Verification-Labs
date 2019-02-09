import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

class ParserTests {
    @Test
    void parseExpr() throws IOException, ParsingException {
        Lexer lexer;
        Parser parser;

        lexer = new Lexer(new ByteArrayInputStream("2 + 2 - 2".getBytes()));
        parser = new Parser(lexer);
        assert 2 == parser.parseExpr();

        lexer = new Lexer(new ByteArrayInputStream("2 + 2 - 2 + (2 + 2 - 2)".getBytes()));
        parser = new Parser(lexer);
        assert 4 == parser.parseExpr();

        lexer = new Lexer(new ByteArrayInputStream("(2)(2)".getBytes()));
        parser = new Parser(lexer);
        try {
            parser.parseAtom();
        } catch (ParsingException e) {
            assert e.getType().equals(ParsingExceptionType.UNEXPECTED_LEXEME);
        }

        lexer = new Lexer(new ByteArrayInputStream("-2 + 2 + (-2 - -2)".getBytes()));
        parser = new Parser(lexer);
        assert 0 == parser.parseExpr();

        lexer = new Lexer(new ByteArrayInputStream("(-2 + 4)^(7 + 3 - 5)".getBytes()));
        parser = new Parser(lexer);
        assert 32 == parser.parseExpr();
    }

    @Test
    void parseFactor() throws IOException, ParsingException {
        Lexer lexer;
        Parser parser;

        lexer = new Lexer(new ByteArrayInputStream("2".getBytes()));
        parser = new Parser(lexer);
        assert 2 == parser.parseFactor();

        lexer = new Lexer(new ByteArrayInputStream("2^2^2".getBytes()));
        parser = new Parser(lexer);
        assert 16 == parser.parseFactor();

        lexer = new Lexer(new ByteArrayInputStream("2^-1".getBytes()));
        parser = new Parser(lexer);
        assert 0 == parser.parseFactor();

        lexer = new Lexer(new ByteArrayInputStream("2^0".getBytes()));
        parser = new Parser(lexer);
        assert 1 == parser.parseFactor();
    }

    @Test
    void parsePower() throws IOException, ParsingException {
        Lexer lexer;
        Parser parser;

        lexer = new Lexer(new ByteArrayInputStream("2".getBytes()));
        parser = new Parser(lexer);
        assert 2 == parser.parsePower();

        lexer = new Lexer(new ByteArrayInputStream("-2".getBytes()));
        parser = new Parser(lexer);
        assert -2 == parser.parsePower();
    }

    @Test
    void parseAtom() throws IOException, ParsingException {
        Lexer lexer;
        Parser parser;

        lexer = new Lexer(new ByteArrayInputStream("2".getBytes()));
        parser = new Parser(lexer);
        assert 2 == parser.parseAtom();

        String bigNumber = "12312249328095838532981573208957913287532817051325873210493125932185839879532791579138795";
        lexer = new Lexer(new ByteArrayInputStream(bigNumber.getBytes()));
        parser = new Parser(lexer);
        try {
            parser.parseAtom();
        } catch (ParsingException e) {
            assert e.getType().equals(ParsingExceptionType.LONG_NUMBER);
        }

        lexer = new Lexer(new ByteArrayInputStream("(2)".getBytes()));
        parser = new Parser(lexer);
        assert 2 == parser.parseAtom();

        lexer = new Lexer(new ByteArrayInputStream("(2())".getBytes()));
        parser = new Parser(lexer);
        try {
            parser.parseAtom();
        } catch (ParsingException e) {
            assert e.getType().equals(ParsingExceptionType.UNEXPECTED_LEXEME);
        }
    }
}