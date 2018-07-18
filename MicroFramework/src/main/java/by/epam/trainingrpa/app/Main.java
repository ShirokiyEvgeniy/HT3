package by.epam.trainingrpa.app;

import by.epam.trainingrpa.util.HttpCheckerRunner;

import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("config"); // properties with txt file path with command lines
        String path = bundle.getString("filePath"); // getting path to file
        HttpCheckerRunner runner = new HttpCheckerRunner(path); // runner for making operations from txt file
        runner.run(); // starting process
    }
}
