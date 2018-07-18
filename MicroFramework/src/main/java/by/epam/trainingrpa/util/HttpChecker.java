package by.epam.trainingrpa.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpChecker {
    private HttpURLConnection connection;
    private Logger logger;

    private int passedTests;
    private int failedTests;
    private double totalTime;

    private String pageCode;

    public HttpChecker() {
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        passedTests = 0;
        failedTests = 0;
        totalTime = 0;
        logger = LogManager.getLogger("logger");
        logger.info("////////////////////" + new Date() + "////////////////////");
    }

    public void open(URL url, int timeout) {
        long start;
        long end;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(timeout * 100);
            start = System.currentTimeMillis();
            connection.connect();
            end = System.currentTimeMillis();

            pageCode = getPageCode();

        } catch (IOException e) {
            e.printStackTrace();
            logger.info("! [open \"" + url.toString() + "\" \"" + timeout + "\"] " + (double) timeout);
            totalTime += (double) timeout;
            failedTests++;
            return;
        }
        logger.info("+ [open \"" + url.toString() + "\" \"" + timeout + "\"] " + (double) (end - start) / 1000.0);
        passedTests++;
        totalTime += (double) (end - start) / 1000.0;
    }

    public void checkPageTitle(String title) {
        long start = System.currentTimeMillis();
        boolean flag = title.equals(pageCode.substring(pageCode.indexOf("<title>") + 7, pageCode.indexOf("</title>")));
        long end = System.currentTimeMillis();
        totalTime += (double) (end - start) / 1000.0;
        if (flag) {
            logger.info("+ [checkPageTitle \"" + title + "\"] " + (double) (end - start) / 1000.0);
            passedTests++;
        } else {
            logger.info("! [checkPageTitle \"" + title + "\"] " + (double) (end - start) / 1000.0);
            failedTests++;
        }
    }

    public void checkPageContains(String text) {
        long start = System.currentTimeMillis();
        boolean flag = pageCode.contains(text);
        long end = System.currentTimeMillis();
        totalTime += (double) (end - start) / 1000.0;
        if (flag) {
            logger.info("+ [checkPageContains \"" + text + "\"] " + (double) (end - start) / 1000.0);
            passedTests++;
        } else {
            logger.info("! [checkPageContains \"" + text + "\"] " + (double) (end - start) / 1000.0);
            failedTests++;
        }
    }

    public void checkLinkPresentByHref(String href) {
        long start = System.currentTimeMillis();
        Pattern pattern = Pattern.compile("<a(.*)href=\"" + href + "\"(.*)</a>");
        Matcher matcher = pattern.matcher(pageCode);
        boolean flag = matcher.find();
        long end = System.currentTimeMillis();
        totalTime += (double) (end - start) / 1000.0;
        if (flag) {
            logger.info("+ [checkLinkPresentByHref \"" + href + "\"] " + (double) (end - start) / 1000.0);
            passedTests++;
        } else {
            logger.info("! [checkLinkPresentByHref \"" + href + "\"] " + (double) (end - start) / 1000.0);
            failedTests++;
        }
    }

    public void checkLinkPresentByName(String linkName) {
        long start = System.currentTimeMillis();
        Pattern pattern = Pattern.compile("<a(.*)>" + linkName + "</a>");
        Matcher matcher = pattern.matcher(pageCode);
        boolean flag = matcher.find();
        long end = System.currentTimeMillis();
        totalTime += (double) (end - start) / 1000.0;
        if (flag) {
            logger.info("+ [checkLinkPresentByName \"" + linkName + "\"] " + (double) (end - start) / 1000.0);
            passedTests++;
        } else {
            logger.info("! [checkLinkPresentByName \"" + linkName + "\"] " + (double) (end - start) / 1000.0);
            failedTests++;
        }
    }

    private String getPageCode() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response);
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void logMessage(String msg) {
        logger.info(msg);
    }

    public int getPassedTests() {
        return passedTests;
    }

    public int getFailedTests() {
        return failedTests;
    }

    public double getTotalTime() {
        return totalTime;
    }
}