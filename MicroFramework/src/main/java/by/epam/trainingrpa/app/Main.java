package by.epam.trainingrpa.app;

import by.epam.trainingrpa.util.HttpCheckerRunner;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/input.txt";
        HttpCheckerRunner runner = new HttpCheckerRunner(path);
        runner.run();
    }
}
