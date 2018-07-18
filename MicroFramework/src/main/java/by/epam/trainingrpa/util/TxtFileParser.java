package by.epam.trainingrpa.util;

import java.io.File;

/**
 * Parser for txt files.
 * @version 1.0
 * */
public class TxtFileParser {
    /**
     * Path to the parsing txt file.
     * */
    private String filePath;

    /**
     * Constructor with parameter <b>filePath</b>
     * @param filePath path to the txt file for parsing
     * */
    public TxtFileParser (String filePath) {
        this.filePath = filePath;
    }

    /**
     * Checks correctness of the path by regexp.
     * @param string Path for checking.
     * @return True if path is correct.
     * */
    private static boolean isCorrectFile(String string) { // define correctness of the file
        return string.matches("([A-Z|a-z]:[/][^*|\"<>?\\n]*)|([/]?.*?[/].*)");
    }

    /**
     * @param mystr Path to file with extension we want to get.
     * @return File extension
     * */
    private static String getFileExtension(String mystr) { // returns file extension
        int index = mystr.lastIndexOf('.');
        return index == -1 ? null : mystr.substring(index + 1);
    }

    /**
     * Validator for txt file, the main method of the class.
     * @return True if txt file is correct.
     * */
    public boolean validateFile() {
        if (isCorrectFile(filePath)) { // checking by regexp
            File txtFile = new File(filePath);
            if (txtFile.exists()) { // checking existing
                if (txtFile.isFile()) { // should be file
                    if ("txt".equals(getFileExtension(filePath))) { // checking extension
                        if (txtFile.canRead()) { // checking able for reading
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
