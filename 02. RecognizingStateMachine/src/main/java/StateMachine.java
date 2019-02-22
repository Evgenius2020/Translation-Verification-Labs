import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

class StateMachine {
    private final Integer[] finalStates;
    private final HashMap<Integer, HashMap<Character, Integer>> instructions;

    StateMachine(BufferedReader programReader) throws IOException {
        int finalStatesNumber = Integer.parseInt(programReader.readLine());
        LinkedList<Integer> finalStates = new LinkedList<Integer>();
        for (int i = 0; i < finalStatesNumber; i++)
            finalStates.add(Integer.parseInt(programReader.readLine()));
        this.finalStates = finalStates.toArray(new Integer[0]);

        int instructionsNumber = Integer.parseInt(programReader.readLine());
        HashMap<Integer, HashMap<Character, Integer>> instructions =
                new HashMap<Integer, HashMap<Character, Integer>>();
        for (int i = 0; i < instructionsNumber; i++) {
            String instruction = programReader.readLine();
            String[] instructionStrings = instruction.split(" ");
            if (instructionStrings.length != 3)
                throw new IllegalArgumentException("Wrong instruction: " + instruction);
            int currentState = Integer.parseInt(instructionStrings[0].toLowerCase());
            if (instructionStrings[1].length() != 1)
                throw new IllegalArgumentException("Wrong state: " + instructionStrings[1]);
            char currentSymbol = instructionStrings[1].charAt(0);
            if (currentSymbol < 'a' || currentSymbol > 'z')
                throw new IllegalArgumentException("Wrong state: " + instructionStrings[1]);
            int nextState = Integer.parseInt(instructionStrings[2]);

            if (!instructions.containsKey(currentState))
                instructions.put(currentState, new HashMap<Character, Integer>());
            instructions.get(currentState).put(currentSymbol, nextState);
        }

        this.instructions = instructions;
    }

    boolean recognize(Reader reader) throws IOException {
        int currentState = 1;
        
        int c = reader.read();
        while (c != -1) {
            char currentSymbol = (String.valueOf((char)c)).toLowerCase().charAt(0);
            try {
                currentState = instructions.get(currentState).get(currentSymbol);
            }
            catch (NullPointerException e) {
                return false;
            }
            c = reader.read();
        }

        return Arrays.asList(finalStates).contains(currentState);
    }
}
