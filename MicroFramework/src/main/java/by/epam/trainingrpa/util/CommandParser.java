package by.epam.trainingrpa.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is using for parsing commands.
 * @version 1.0
 * */
public class CommandParser {
    /**
     * File with commands to parse.
     * */
    private File txtFile;

    /**
     * Feasible commands.
     * */
    private String[] validCommands = {
            "open",
            "checkPageTitle",
            "checkPageContains",
            "checkLinkPresentByHref",
            "checkLinkPresentByName"
    };

    /**
     * Constructor with parameter <b>txtFile</b>
     * @param txtFile File that should be checked and parsed.
     * */
    public CommandParser(File txtFile) {
        this.txtFile = txtFile;
    }

    /**
     * This method checks correctness of command line.
     * @param line String for checking.
     * @return True if command line is correct.
     * */
    private boolean checkCommandLine(String line) {
        return line.matches("^(.)?([a-zA-Z]*) \"(.*)\"( \"([0-9]*)\")?$");
    }

    /**
     * This method is using to define correct integer numbers.
     * @param string String for defining.
     * @return True if number is correct.
     * */
    private static boolean isInteger(String string) {
        return  string.matches("\\d+") || string.matches("\\+\\d+") ||
                string.matches("\\d+e\\d+") || string.matches("\\+\\d+e\\d+");
    }

    /**
     * This method is using for checking and returning correct command lines.
     * @return List with correct command lines.
     * */
    public ArrayList<Command> readCommands() {
        BufferedReader reader; // reads txtFile with unchecked command lines
        ArrayList<Command> commandList = new ArrayList<>(); // list for correct command lines
        try {
            reader = new BufferedReader(new FileReader(txtFile)); // opening txt file
            String line = reader.readLine(); // the first line in file
            if (line == null || line.trim().equals("")) { // empty file check
                System.out.println("File (" + txtFile.getAbsolutePath() + ") is empty!");
                return null;
            }
            do {
                if (checkCommandLine(line)) { // checking command line for correctness
                    commandProcessing(commandList, line); // if line is correct, start parsing
                } else {
                    System.out.println("Command line (" + line + ") is incorrect!");
                }
            } while ((line = reader.readLine()) != null); // reads next line in the file
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandList; // prepared list with correct command lines
    }

    /**
     * This method parses command lines.
     * @param line Command line to check.
     * @param commandList List with correct command lines to add.
     * */
    private void commandProcessing(ArrayList<Command> commandList, String line) {
        int position; // positions of cursor when program is trying to
        int length;   // find command, value and timeout
        String command = line.substring(0, (position = line.indexOf('\"'))); // string with command (unchecked)
        command = command.trim(); // deleting all spaces
        if (!Arrays.toString(validCommands).contains(command)) { // if this command isn't feasible, program can't do it
            System.out.println("Command line (" + line + ") is incorrect!");
        } else { // if it's feasible
            String value = line.substring(position + 1, (length = line.indexOf('\"', position + 1))); // command value
            if (((position = line.indexOf('\"', length + 1)) != -1) && command.equals("open")) { // if command is 'open', timeout is required
                String timeout = line.substring(position + 1, line.indexOf('\"', position + 1)); // timeout for 'open' command
                if (isInteger(timeout)) { // checking timeout value
                    commandList.add(new Command(command, value, timeout));
                } else {
                    System.out.println("Command line (" + line + ") is incorrect!");
                }
            } else if (!command.equals("open")) { // if it's not an 'open' command
                commandList.add(new Command(command, value));
            } else { // if it's an 'open' command, it must have timeout
                System.out.println("Command line (" + line + ") is incorrect!");
            }
        }
    }
}
