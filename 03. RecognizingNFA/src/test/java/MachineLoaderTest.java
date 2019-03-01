import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class MachineLoaderTest {
    @Test
    public void load() throws IOException {

        String machineDefinition = "1\n" +
                "5\n" +
                "9\n" +
                "1 a 1\n" +
                "1 a 2\n" +
                "1 b 1\n" +
                "2 a 3\n" +
                "2 b 3\n" +
                "3 a 4\n" +
                "3 b 4\n" +
                "4 a 5\n" +
                "4 b 5\n";
        BufferedReader reader = new BufferedReader(new StringReader(machineDefinition));
        Machine machine = MachineLoader.load(reader);
        assert machine.getFinalStates().length == 1;
        assert machine.getFinalStates()[0] == 5;
        assert machine.getInstructions().size() == 4;
        assert machine.getInstructions().get(1).get('a').length == 2;
        assert machine.getInstructions().get(1).get('b').length == 1;
    }
}