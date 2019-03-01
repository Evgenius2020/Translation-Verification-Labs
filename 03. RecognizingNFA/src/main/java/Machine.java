import java.io.IOException;
import java.io.Reader;
import java.util.*;

class Machine {
    private final Integer[] finalStates;
    private final HashMap<Integer, HashMap<Character, Integer[]>> instructions;

    HashMap<Integer, HashMap<Character, Integer[]>> getInstructions() {
        return instructions;
    }

    Integer[] getFinalStates() {
        return finalStates;
    }

    Machine(Integer[] finalStates, HashMap<Integer, HashMap<Character, Integer[]>> instructions) {
        this.finalStates = finalStates;
        this.instructions = instructions;
    }

    boolean recognize(Reader reader) throws IOException {
        HashSet<Integer> currentStates = new HashSet<Integer>();
        currentStates.add(1);

        int c = reader.read();
        while (c != -1) {
            char currentSymbol = (String.valueOf((char)c)).toLowerCase().charAt(0);
            HashSet<Integer> nextStates = new HashSet<Integer>();
            for (Integer currentState: currentStates) {
                try {
                    nextStates.addAll(Arrays.asList(instructions.get(currentState).get(currentSymbol)));
                } catch (NullPointerException ignored) {
                }
            }
            currentStates = nextStates;
            c = reader.read();
        }

        for (int finalState: finalStates)
            if (currentStates.contains(finalState))
                return true;
        return false;
    }
}
