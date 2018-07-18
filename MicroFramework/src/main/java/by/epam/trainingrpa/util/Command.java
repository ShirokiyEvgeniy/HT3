package by.epam.trainingrpa.util;

public class Command {
    private String command;
    private String value;
    private String timeout;

    public Command(String command, String value) {
        this.command = command;
        this.value = value;
        this.timeout = "";
    }

    public Command(String command, String value, String timeout) {
        this.command = command;
        this.value = value;
        this.timeout = timeout;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
