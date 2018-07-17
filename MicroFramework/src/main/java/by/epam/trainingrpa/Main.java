package by.epam.trainingrpa;

import java.net.URL;

public class Main {

    public static void main(String[] args) {

        try {
            HttpChecker checker = new HttpChecker();
            checker.open(new URL("http://svyatoslav.biz/"), 1);
            checker.checkPageTitle("Google");
            checker.checkPageContains("Google");
            checker.checkLinkPresentByHref("http://svyatoslav.biz/education/webtechs_and_webapps_in_pictures/");
            checker.checkLinkPresentByName("Классификация тестирования");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
