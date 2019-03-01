import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

public class MachineTests {
    @Test
    public void recognize() throws IOException {
        Integer[] finalStates = new Integer[]{5};
        HashMap<Integer, HashMap<Character, Integer[]>> instructions =
                new HashMap<Integer, HashMap<Character, Integer[]>>();
        HashMap<Character, Integer[]> firstStateInstructions = new HashMap<Character, Integer[]>();
        firstStateInstructions.put('a', new Integer[]{1, 2});
        firstStateInstructions.put('b', new Integer[]{1});
        instructions.put(1, firstStateInstructions);
        HashMap<Character, Integer[]> secondStateInstructions = new HashMap<Character, Integer[]>();
        secondStateInstructions.put('a', new Integer[]{3});
        secondStateInstructions.put('b', new Integer[]{3});
        instructions.put(2, secondStateInstructions);
        HashMap<Character, Integer[]> thirdStateInstructions = new HashMap<Character, Integer[]>();
        thirdStateInstructions.put('a', new Integer[]{4});
        thirdStateInstructions.put('b', new Integer[]{4});
        instructions.put(3, thirdStateInstructions);
        HashMap<Character, Integer[]> forthStateInstructions = new HashMap<Character, Integer[]>();
        forthStateInstructions.put('a', new Integer[]{5});
        forthStateInstructions.put('b', new Integer[]{5});
        instructions.put(4, forthStateInstructions);
        Machine machine = new Machine(finalStates, instructions);
        assert machine.recognize(new StringReader("abbb"));
        assert machine.recognize(new StringReader("aaaa"));
        assert !machine.recognize(new StringReader("bbbbaaa"));
        assert !machine.recognize(new StringReader("baaa"));
        assert machine.recognize(new StringReader("babaaaaa"));
    }
}
