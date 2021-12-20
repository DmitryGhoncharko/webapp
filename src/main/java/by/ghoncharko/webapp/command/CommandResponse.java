package by.ghoncharko.webapp.command;

public interface CommandResponse {
    boolean isRedirect();

    String getPath();

}
