package by.epam.trainingrpa.util;

/**
 * Class for describing commands
 * @version 1.0
 * */
public class Command {
    /**
     * String with command (what to do).
     * */
    private String command;
    /**
     * Command value.
     * */
    private String value;
    /**
     * Max time for connection. Uses only in 'open' command.
     * */
    private String timeout;

    /**
     * Constructor with parameters <b>command</b> and <b>value</b>
     * @param command what to do
     * @param value value to do
     *  */
    public Command(String command, String value) {
        this.command = command;
        this.value = value;
        this.timeout = "";
    }

    /**
     * Constructor with parameters <b>command</b>, <b>value</b> and <b>timeout</b>
     * @param command what to do
     * @param value value to do
     * @param timeout timeout for connection in 'open' command
     *  */
    public Command(String command, String value, String timeout) {
        this.command = command;
        this.value = value;
        this.timeout = timeout;
    }

    /**
     * @return value of the field {@link Command#command}
     * */
    public String getCommand() {
        return command;
    }

    /**
     * @return value of the field {@link Command#value}
     * */
    public String getValue() {
        return value;
    }

    /**
     * @return value of the field {@link Command#timeout}
     * */
    public String getTimeout() {
        return timeout;
    }
}
