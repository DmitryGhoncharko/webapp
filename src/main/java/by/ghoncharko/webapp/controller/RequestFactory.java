package by.ghoncharko.webapp.controller;

import by.ghoncharko.webapp.command.CommandRequest;
import by.ghoncharko.webapp.command.CommandResponse;

import javax.servlet.http.HttpServletRequest;

public interface RequestFactory {

    CommandRequest createRequest(HttpServletRequest request);

    CommandResponse createForwardResponse(String path);

    CommandResponse createRedirectResponse(String path);

    static RequestFactory getInstance() {
        return SimpleRequestFactory.getInstance();
    }

}
