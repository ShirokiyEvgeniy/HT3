package by.epam.trainingrpa.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Runner and controller for {@link HttpChecker}.
 * @version 1.0
 * */
public class HttpCheckerRunner {
    /**
     * Path to txt file with command lines.
     * */
    private String filePath;
    /**
     * True if some page is opened.
     * Uses for control running methods in {@link HttpChecker}.
     * */
    private boolean opened;
    /**
     * The main field. See {@link HttpChecker}.
     * */
    private HttpChecker checker;

    /**
     * Constructor with parameter <b>filePath</b>.
     * @param filePath Path to the txt file with command lines.
     * */
    public HttpCheckerRunner(String filePath) {
        this.filePath = filePath;
        checker = new HttpChecker();
        this.opened = false;
    }

    /**
     * The main method. Checks correctness of the txt file by {@link TxtFileParser}.
     * Gets all correct commands by {@link CommandParser} and starts them.
     * */
    public void run() {
        try {
            TxtFileParser txtFileParser = new TxtFileParser(filePath); // checker for correctness of txt file.
            if (txtFileParser.validateFile()) { // validating txt file
                CommandParser commandParser = new CommandParser(new File(filePath)); // command parser
                ArrayList<Command> commands = commandParser.readCommands(); // list with correct command lines
                if (commands != null && !commands.isEmpty()) { // if list with command lines isn't empty
                    doCommands(commands); // doing commands from list
                    logEndMessage(); // logging statistic of this run
                } else {
                    System.out.println("Command list is empty!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for doing command lines.
     * @param commands List with correct command lines.
     * */
    private void doCommands(ArrayList<Command> commands) {
        for (Command command : commands) { // foreach for list of command lines
            switch (command.getCommand()) { // switching by command and doing operations
                case "open":
                    try {
                        checker.open(new URL(command.getValue()), Integer.parseInt(command.getTimeout()));
                        opened = true;
                    } catch (MalformedURLException e) {
                        System.out.println("Incorrect URL (" + command.getCommand() + " \"" + command.getValue() + "\")");
                    }
                    break;
                case "checkPageTitle":
                    if (opened) {
                        checker.checkPageTitle(command.getValue());
                    } else {
                        System.out.println("Unable to do command " + command.getCommand() + " \"" + command.getValue() + "\" (Page isn't opened!)");
                    }
                    break;
                case "checkPageContains":
                    if (opened) {
                        checker.checkPageContains(command.getValue());
                    } else {
                        System.out.println("Unable to do command " + command.getCommand() + " \"" + command.getValue() + "\" (Page isn't opened!)");
                    }
                    break;
                case "checkLinkPresentByHref":
                    if (opened) {
                        checker.checkLinkPresentByHref(command.getValue());
                    } else {
                        System.out.println("Unable to do command " + command.getCommand() + " \"" + command.getValue() + "\" (Page isn't opened!)");
                    }
                    break;
                case "checkLinkPresentByName":
                    if (opened) {
                        checker.checkLinkPresentByName(command.getValue());
                    } else {
                        System.out.println("Unable to do command " + command.getCommand() + " \"" + command.getValue() + "\" (Page isn't opened!)");
                    }
                    break;
            }
        }
    }

    /**
     * Logging statistic of this run.
     * */
    private void logEndMessage() {
        int failedTests = checker.getFailedTests(); // number of failed operations
        int passedTests = checker.getPassedTests(); // number of successful operations
        int totalTests = failedTests + passedTests; // number of operations
        double totalTime = checker.getTotalTime(); // total time of run
        checker.logMessage("Total tests: " + totalTests);
        checker.logMessage("Passed/Failed: " + passedTests + "/" + failedTests);
        checker.logMessage("Total time: " + totalTime);
        double average = totalTime / totalTests; // average time for one operation
        checker.logMessage("Average time: " + String.format("%.3f", average));
    }
}