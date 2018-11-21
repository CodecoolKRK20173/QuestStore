package com.codecool.app.httphandlers.admin;

import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.AppDAOs;
import com.codecool.app.httphandlers.TemplatesPaths;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.Optional;

public class AdminClassesHandler implements HttpHandler {
    private TemplatesPaths templatesPaths = new TemplatesPaths();
    private AppDAOs appDAOs;
    private CookieHelper cookieHelper;

    public AdminClassesHandler(AppDAOs appDAOs, CookieHelper cookieHelper) {
        this.appDAOs = appDAOs;
        this.cookieHelper = cookieHelper;
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")){
            Optional<HttpCookie> cookie = cookieHelper.getSessionIdCookie(httpExchange);
            String sessionId = cookie.get().getValue();
            sessionId = sessionId.replace("\"", "");

            if(appDAOs.getDAOAccounts().isValidUserType(sessionId, "ADMIN")) {
                writeResponse(httpExchange, sessionId);
            } else {
                System.out.println("Unauthorized request for admin");
                httpExchange.getResponseHeaders().add("Location", "/");
                httpExchange.sendResponseHeaders(303, 0);

            }
        }
    }

    private void writeResponse(HttpExchange httpExchange, String sessionId) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatesPaths.ADMIN_CLASSES);
        JtwigModel model = createJtwigModel(sessionId);
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private JtwigModel createJtwigModel(String sessionId) {
        JtwigModel model = JtwigModel.newModel();
        model.with("userName", appDAOs.getDAOAccounts().getAccountBySessionId(sessionId).getNickname());

        model.with("classes", appDAOs.getDAOClasses().getAllClasses());
        return model;
    }
}
