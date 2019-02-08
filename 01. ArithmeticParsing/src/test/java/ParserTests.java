import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ParserTests {
    @Test
    void parseExpr() throws IOException, ParsingException {
        Lexer lexer = new Lexer(new ByteArrayInputStream("2 + 2 - 2".getBytes(StandardCharsets.UTF_8)));
        Parser parser = new Parser(lexer);
        assert 2 == parser.parseExpr();
    }

    @Test
    void parseAtom() throws IOException, ParsingException {
        Lexer lexer;
        Parser parser;

        lexer = new Lexer(new ByteArrayInputStream("2".getBytes(StandardCharsets.UTF_8)));
        parser = new Parser(lexer);
        assert 2 == parser.parseAtom();

        String bigNumber = "12312249328095838532981573208957913287532817051325873210493125932185839879532791579138795";
        lexer = new Lexer(new ByteArrayInputStream(bigNumber.getBytes(StandardCharsets.UTF_8)));
        parser = new Parser(lexer);
        try {
            parser.parseAtom();
        }
        catch (ParsingException e) {
            assert e.getType().equals(ParsingExceptionType.LONG_NUMBER);
        }
    }
}