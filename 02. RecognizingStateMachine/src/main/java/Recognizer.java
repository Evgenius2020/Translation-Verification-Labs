import java.io.*;

public class Recognizer {
    public static void main(String[] args) throws IOException {
        if (args.length < 2)
            throw new IllegalArgumentException("Usage: Recognizer <program_filename> <string_filename>");
        File programFile = new File(args[0]);
        if (!programFile.exists()) {
            throw new FileNotFoundException(args[0]);
        }
        StateMachine stateMachine = new StateMachine(new BufferedReader(new FileReader(programFile)));
        File stringFile = new File(args[1]);
        if (!stringFile.exists()) {
            throw new FileNotFoundException(args[1]);
        }
        System.out.println(stateMachine.recognize(new FileReader(stringFile)));
    }
}