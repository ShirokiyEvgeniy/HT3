package by.epam.trainingrpa.app;

import by.epam.trainingrpa.util.HttpCheckerRunner;

import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String path = bundle.getString("filePath");
        HttpCheckerRunner runner = new HttpCheckerRunner(path);
        runner.run();
    }
}
