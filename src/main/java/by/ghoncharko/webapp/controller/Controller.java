package by.ghoncharko.webapp.controller;

import by.ghoncharko.webapp.command.Command;
import by.ghoncharko.webapp.command.CommandRequest;
import by.ghoncharko.webapp.command.CommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private static final String COMMAND_NAME_PARAM = "command";

    private static final RequestFactory REQUEST_FACTORY = RequestFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println(Charset.defaultCharset());
        System.out.println(req.getCharacterEncoding());
        System.out.println(resp.getCharacterEncoding());
        LOG.trace("caught req and resp in doGet method");
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println(Charset.defaultCharset());
        System.out.println(req.getCharacterEncoding());
        System.out.println(resp.getCharacterEncoding());
        LOG.trace("caught req and resp in doPost method");
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        final String commandName = httpRequest.getParameter(COMMAND_NAME_PARAM);
        final Command command = Command.of(commandName);
        final CommandRequest commandRequest = REQUEST_FACTORY.createRequest(httpRequest);
        final CommandResponse commandResponse = command.execute(commandRequest);
        proceedWithResponse(httpRequest, httpResponse, commandResponse);
    }

    private void proceedWithResponse(HttpServletRequest req, HttpServletResponse resp,
                                     CommandResponse commandResponse) {
        try {
            forwardOrRedirectToResponseLocation(req, resp, commandResponse);
        } catch (ServletException e) {
            LOG.error("servlet exception occurred", e);
        } catch (IOException e) {
            LOG.error("IO exception occurred", e);
        }
    }

    private void forwardOrRedirectToResponseLocation(HttpServletRequest req, HttpServletResponse resp,
                                                     CommandResponse commandResponse) throws IOException, ServletException {
        if (commandResponse.isRedirect()) {
            resp.sendRedirect(commandResponse.getPath());
        } else {
            final String desiredPath = commandResponse.getPath();
            final RequestDispatcher dispatcher = req.getRequestDispatcher(desiredPath);
            dispatcher.forward(req, resp);
        }
    }
}