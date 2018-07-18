package by.epam.trainingrpa.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandParser {
    private File txtFile;

    private String[] validCommands = {
            "open",
            "checkPageTitle",
            "checkPageContains",
            "checkLinkPresentByHref",
            "checkLinkPresentByName"
    };

    public CommandParser(File txtFile) {
        this.txtFile = txtFile;
    }

    private boolean checkCommandLine(String line) {
        return line.matches("^(.)?([a-zA-Z]*) \"(.*)\"( \"([0-9]*)\")?$");
    }

    /**This method is used to define correct integer number
     * @param string string for defining
     * @return true if number is correct */
    private static boolean isInteger(String string) {
        return  string.matches("\\d+") || string.matches("\\+\\d+") ||
                string.matches("\\d+e\\d+") || string.matches("\\+\\d+e\\d+");
    }

    public ArrayList<Command> readCommands() {
        BufferedReader reader;
        ArrayList<Command> commandList = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(txtFile));
            String line = reader.readLine();
            if (line.equals("")) {
                System.out.println("File (" + txtFile.getAbsolutePath() + ") is empty!");
                return null;
            }
            do {
                if (checkCommandLine(line)) {
                    commandProcessing(commandList, line);
                } else {
                    System.out.println("Command line (" + line + ") is incorrect!");
                }
            } while ((line = reader.readLine()) != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandList;
    }

    private void commandProcessing(ArrayList<Command> commandList, String line) {
        int position;
        int length;
        String command = line.substring(0, (position = line.indexOf('\"')));
        command = command.trim();
        if (!Arrays.toString(validCommands).contains(command)) {
            System.out.println("Command line (" + line + ") is incorrect!");
        } else {
            String value = line.substring(position + 1, (length = line.indexOf('\"', position + 1)));
            if (((position = line.indexOf('\"', length + 1)) != -1) && command.equals("open")) {
                String timeout = line.substring(position + 1, line.indexOf('\"', position + 1));
                if (isInteger(timeout)) {
                    commandList.add(new Command(command, value, timeout));
                } else {
                    System.out.println("Command line (" + line + ") is incorrect!");
                }
            } else {
                commandList.add(new Command(command, value));
            }
        }
    }
}
