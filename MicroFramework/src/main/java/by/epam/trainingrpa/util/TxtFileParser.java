package by.epam.trainingrpa.util;

import java.io.File;

public class TxtFileParser {
    private String filePath;

    public TxtFileParser (String filePath) {
        this.filePath = filePath;
    }

    private static boolean isCorrectFile(String string) { // define correctness of the file
        return string.matches("([A-Z|a-z]:[/][^*|\"<>?\\n]*)|([/]?.*?[/].*)");
    }

    private static String getFileExtension(String mystr) { // returns file extension
        int index = mystr.lastIndexOf('.');
        return index == -1 ? null : mystr.substring(index + 1);
    }

    public boolean validateFile() {
        if (isCorrectFile(filePath)) {
            File txtFile = new File(filePath);
            if (txtFile.exists()) {
                if (txtFile.isFile()) {
                    if ("txt".equals(getFileExtension(filePath))) {
                        if (txtFile.canRead()) {
                            System.out.println("File is OK! (" + filePath + ")");
                            return true;
                        } else {
                            System.out.println("File can't be read! (" + filePath + ")");
                            return false;
                        }
                    } else {
                        System.out.println("File extension isn't 'txt'! (" + filePath + ")");
                        return false;
                    }
                } else {
                    System.out.println("It isn't a file! (" + filePath + ")");
                    return false;
                }
            } else {
                System.out.println("File doesn't exist! (" + filePath + ")");
                return false;
            }
        } else {
            System.out.println("Incorrect file path! (" + filePath + ")");
            return false;
        }
    }
}
