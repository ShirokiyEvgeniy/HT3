package by.epam.trainingrpa.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HttpCheckerRunner {
    private String filePath;
    private boolean opened;
    private HttpChecker checker;

    public HttpCheckerRunner(String filePath) {
        this.filePath = filePath;
        checker = new HttpChecker();
        this.opened = false;
    }

    public void run() {
        try {
            TxtFileParser txtFileParser = new TxtFileParser(filePath);
            if (txtFileParser.validateFile()) {
                CommandParser commandParser = new CommandParser(new File(filePath));
                ArrayList<Command> commands = commandParser.readCommands();
                if (!commands.isEmpty()) {
                    doCommands(commands);
                    logEndMessage();
                } else {
                    System.out.println("Command list is empty!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doCommands(ArrayList<Command> commands) throws MalformedURLException {
        for (Command command : commands) {
            switch (command.getCommand()) {
                case "open":
                    opened = true;
                    checker.open(new URL(command.getValue()), Integer.parseInt(command.getTimeout()));
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

    private void logEndMessage() {
        int failedTests = checker.getFailedTests();
        int passedTests = checker.getPassedTests();
        int totalTests = failedTests + passedTests;
        double totalTime = checker.getTotalTime();
        checker.logMessage("Total tests: " + totalTests);
        checker.logMessage("Passed/Failed: " + passedTests + "/" + failedTests);
        checker.logMessage("Total time: " + totalTime);
        double average = totalTime / totalTests;
        checker.logMessage("Average time: " + String.format("%.3f", average));
    }
}