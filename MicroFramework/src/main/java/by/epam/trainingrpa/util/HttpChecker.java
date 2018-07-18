package by.epam.trainingrpa.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class in the framework.
 * Has five methods for checking URL.
 * @version 1.0
 * */
public class HttpChecker {
    /**
     * Connection with URL.
     * Program gets information by this connection.
     * */
    private HttpURLConnection connection;
    /**
     * Logger for making a log file with information about commands.
     * */
    private Logger logger;

    /**
     * Number of successfully performed operations.
     * */
    private int passedTests;
    /**
     * Number of failed operations.
     * */
    private int failedTests;
    /**
     * Spent time for performing all commands.
     * */
    private double totalTime;

    /**
     * Code of the page from URL.
     * */
    private String pageCode;

    /**
     * Constructor without parameters.
     * */
    public HttpChecker() {
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml"); // settings for logger
        passedTests = 0;
        failedTests = 0;
        totalTime = 0;
        logger = LogManager.getLogger("logger"); // getting logger
        logger.info("////////////////////" + new Date() + "////////////////////");
    }

    /**
     * Method for opening URL.
     * @param timeout Max time for connection.
     * @param url URL for connection.
     * */
    public boolean open(URL url, int timeout) {
        long start; // time before connection
        long end; // time after connection
        try {
            connection = (HttpURLConnection) url.openConnection(); // opening connection to the URL
            connection.setConnectTimeout(timeout * 100); // setting timeout in Milliseconds
            start = System.currentTimeMillis(); // getting time before connection
            connection.connect();
            end = System.currentTimeMillis(); // getting time after connection

            pageCode = getPageCode(); // getting code of the page
        } catch (UnknownHostException e) {
            System.out.println("Incorrect URL (" + url.toString() + ")");
            logger.info("! [open \"" + url.toString() + "\" \"" + timeout + "\"] " + (double) timeout);
            totalTime += (double) timeout; // because program was waiting for this time
            failedTests++;
            return false;
        } catch (IOException e) { // if connection is failed
            System.out.println("Problems with connection (" + url.toString() + ")");
            logger.info("! [open \"" + url.toString() + "\" \"" + timeout + "\"] " + (double) timeout);
            totalTime += (double) timeout; // because program was waiting for this time
            failedTests++;
            return false;
        }
        logger.info("+ [open \"" + url.toString() + "\" \"" + timeout + "\"] " + (double) (end - start) / 1000.0);
        passedTests++;
        totalTime += (double) (end - start) / 1000.0; // summing time in seconds
        return true;
    }

    /**
     * Method for defining title of the page and comparing with <b>title</b>
     * @param title Title for comparing
     * */
    public void checkPageTitle(String title) {
        long start = System.currentTimeMillis(); // getting time before operation
        boolean flag = title.equals(pageCode.substring(pageCode.indexOf("<title>") + 7, pageCode.indexOf("</title>"))); // comparing titles
        long end = System.currentTimeMillis(); // getting time after operation
        totalTime += (double) (end - start) / 1000.0; // summing time in seconds
        if (flag) { // if titles are similar
            logger.info("+ [checkPageTitle \"" + title + "\"] " + (double) (end - start) / 1000.0);
            passedTests++;
        } else {
            logger.info("! [checkPageTitle \"" + title + "\"] " + (double) (end - start) / 1000.0);
            failedTests++;
        }
    }

    /**
     * Method for finding text on the page.
     * @param text Text to find.
     * */
    public void checkPageContains(String text) {
        long start = System.currentTimeMillis(); // getting time before operation
        boolean flag = pageCode.contains(text); // finding text on the page
        long end = System.currentTimeMillis(); // getting time after operation
        totalTime += (double) (end - start) / 1000.0; // summing time in seconds
        if (flag) { // if text is on the page
            logger.info("+ [checkPageContains \"" + text + "\"] " + (double) (end - start) / 1000.0);
            passedTests++;
        } else {
            logger.info("! [checkPageContains \"" + text + "\"] " + (double) (end - start) / 1000.0);
            failedTests++;
        }
    }

    /**
     * Method for finding links by its href.
     * @param href href of the link.
     * */
    public void checkLinkPresentByHref(String href) {
        long start = System.currentTimeMillis(); // getting time before operation
        Pattern pattern = Pattern.compile("<a(.*)href=\"" + href + "\"(.*)</a>"); // pattern for link tag
        Matcher matcher = pattern.matcher(pageCode); // finding links on the page
        boolean flag = matcher.find(); // true if there is a link with this href
        long end = System.currentTimeMillis(); // getting time after operation
        totalTime += (double) (end - start) / 1000.0; // summing time in seconds
        if (flag) {
            logger.info("+ [checkLinkPresentByHref \"" + href + "\"] " + (double) (end - start) / 1000.0);
            passedTests++;
        } else {
            logger.info("! [checkLinkPresentByHref \"" + href + "\"] " + (double) (end - start) / 1000.0);
            failedTests++;
        }
    }

    /**
     * Method for finding links by its name.
     * @param linkName name of the link.
     * */
    public void checkLinkPresentByName(String linkName) {
        long start = System.currentTimeMillis(); // getting time before operation
        Pattern pattern = Pattern.compile("<a(.*)>" + linkName + "</a>"); // pattern for link tag
        Matcher matcher = pattern.matcher(pageCode); // finding links on the page
        boolean flag = matcher.find(); // true if there is a link with this name
        long end = System.currentTimeMillis(); // getting time after operation
        totalTime += (double) (end - start) / 1000.0; // summing time in seconds
        if (flag) {
            logger.info("+ [checkLinkPresentByName \"" + linkName + "\"] " + (double) (end - start) / 1000.0);
            passedTests++;
        } else {
            logger.info("! [checkLinkPresentByName \"" + linkName + "\"] " + (double) (end - start) / 1000.0);
            failedTests++;
        }
    }

    /**
     * @return Code of the page.
     * */
    private String getPageCode() {
        StringBuilder stringBuilder = new StringBuilder(); // string builder with lines of code
        try {
            InputStream response = connection.getInputStream(); // input stream for getting code
            Scanner scanner = new Scanner(response); // scanner for reading lines of code
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString(); // all code of the page
    }

    /**
     * Method for logging information from other class.
     * @param msg Message for logging.
     * */
    public void logMessage(String msg) {
        logger.info(msg);
    }

    /**
     * Getter that returns value of the field {@link HttpChecker#passedTests}
     * @return {@link HttpChecker#passedTests}
     * */
    public int getPassedTests() {
        return passedTests;
    }

    /**
     * Getter that returns value of the field {@link HttpChecker#failedTests}
     * @return {@link HttpChecker#failedTests}
     * */
    public int getFailedTests() {
        return failedTests;
    }

    /**
     * Getter that returns value of the field {@link HttpChecker#totalTime}
     * @return {@link HttpChecker#totalTime}
     * */
    public double getTotalTime() {
        return totalTime;
    }
}