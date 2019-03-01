import java.io.*;

class Application {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Usage: Recognizer <program_filename> <string_filename>");
            return;
        }

        File programFile = new File(args[0]);
        if (!programFile.exists()) {
            System.out.println(String.format("File %s is not exist", programFile.getName()));
            return;
        }

        Machine machine;
        try {
            machine = MachineLoader.load(new BufferedReader(new FileReader(programFile)));
        } catch (Exception e) {
            System.err.println("Failed to read program file");
            return;
        }

        File stringFile = new File(args[1]);
        if (!stringFile.exists()) {
            System.err.println(String.format("File %s is not exist", stringFile.getName()));
            return;
        }
        System.out.println(machine.recognize(new FileReader(stringFile)));
    }
}