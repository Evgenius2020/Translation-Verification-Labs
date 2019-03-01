import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

class MachineLoader {
    static Machine load(BufferedReader programReader) throws IOException {
        int finalStatesNumber = Integer.parseInt(programReader.readLine());
        LinkedList<Integer> finalStates = new LinkedList<Integer>();
        for (int i = 0; i < finalStatesNumber; i++)
            finalStates.add(Integer.parseInt(programReader.readLine()));

        int instructionsNumber = Integer.parseInt(programReader.readLine());
        HashMap<Integer, HashMap<Character, HashSet<Integer>>> instructions =
                new HashMap<Integer, HashMap<Character, HashSet<Integer>>>();
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
                instructions.put(currentState, new HashMap<Character, HashSet<Integer>>());
            if (!instructions.get(currentState).containsKey(currentSymbol))
                instructions.get(currentState).put(currentSymbol, new HashSet<Integer>());
            instructions.get(currentState).get(currentSymbol).add(nextState);
        }

        HashMap<Integer, HashMap<Character, Integer[]>> resultInstructions =
                new HashMap<Integer, HashMap<Character, Integer[]>>();
        for (Integer currState : instructions.keySet()) {
            resultInstructions.put(currState, new HashMap<Character, Integer[]>());
            for (Character currSymbol : instructions.get(currState).keySet()) {
                resultInstructions.
                        get(currState).
                        put(currSymbol, instructions.
                                get(currState).
                                get(currSymbol).
                                toArray(new Integer[0]));
            }
        }

        return new Machine(finalStates.toArray(new Integer[0]), resultInstructions);
    }
}