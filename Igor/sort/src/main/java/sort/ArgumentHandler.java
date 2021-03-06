package sort;

import org.apache.commons.cli.*;

/**
 * Created by igoryan on 13.11.15.
 */
public class ArgumentHandler {
    private Options flags;
    private Option register;
    private Option unique;
    private Option threads;
    private Option inputOutput;
    private Option in;

    ArgumentHandler() {
        flags = new Options();

        register = new Option("i", false, "compare insensitive");
        flags.addOption(register);

        unique = new Option("u", false, "sort onlt unique strings");
        flags.addOption(unique);

        threads = new Option("t", true, "threads count");
        threads.setArgs(1);
        flags.addOption(threads);

        inputOutput = new Option("o", true, "inputOutput files");
        inputOutput.setArgs(2);
        flags.addOption(inputOutput);
    }

    public CommandLine getCommandLine(String[] args) throws ParseException {
        CommandLineParser parser = new PosixParser();
        return parser.parse(flags, args);
    }
}