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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class AdminLevelsHandler implements HttpHandler {
    private TemplatesPaths templatesPaths = new TemplatesPaths();
    private AppDAOs appDAOs;
    private CookieHelper cookieHelper;

    public AdminLevelsHandler(AppDAOs appDAOs, CookieHelper cookieHelper) {
        this.appDAOs = appDAOs;
        this.cookieHelper = cookieHelper;
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")){
            // TODO: Check if sessionId stored in cookies is active and user has admin privileges
            // TODO: should handler use AdminController for better clean code???
            Optional<HttpCookie> cookie = cookieHelper.getSessionIdCookie(httpExchange);
            String sessionId = cookie.get().getValue();
            sessionId = sessionId.replace("\"", "");

            if(appDAOs.getDAOAccounts().isValidUserType( sessionId, "ADMIN")) {
                JtwigTemplate template = JtwigTemplate.classpathTemplate(templatesPaths.ADMIN_LEVELS);
                JtwigModel model = JtwigModel.newModel();

                // Example
                model.with("userName", appDAOs.getDAOAccounts().getAccountBySessionId(sessionId).getNickname());
//                model.with("userNickname", "The Lightbringer");

                String response = template.render(model);

                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                System.out.println("Unauthorized request for admin");
                httpExchange.getResponseHeaders().add("Location", "/");
                httpExchange.sendResponseHeaders(303, 0);

            }
        }
    }
}
