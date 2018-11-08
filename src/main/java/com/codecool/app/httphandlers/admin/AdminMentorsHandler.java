package com.codecool.app.httphandlers.admin;

import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.AppDAOs;
import com.codecool.app.dao.DAOAccounts;
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

public class AdminMentorsHandler implements HttpHandler {
    private TemplatesPaths templatesPaths = new TemplatesPaths();
    private Map<String, String> adminOptions = new LinkedHashMap<>();
    private AppDAOs appDAOs;
    private CookieHelper cookieHelper;

    public AdminMentorsHandler(AppDAOs appDAOs, CookieHelper cookieHelper) {
        this.appDAOs = appDAOs;
        this.cookieHelper = cookieHelper;
        initializeAdminOptions();
    }

    private void initializeAdminOptions(){
        adminOptions.put("Profile", "/admin/profile");
        adminOptions.put("Mentors", "/admin/mentors");
        adminOptions.put("Classes", "/admin/classes");
        adminOptions.put("Levels", "/admin/levels");
        adminOptions.put("Log out", "/");
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
                JtwigTemplate template = JtwigTemplate.classpathTemplate(templatesPaths.ADMIN_MENTORS);
                JtwigModel model = JtwigModel.newModel();

                // Example
                model.with("userName", "Uther");
                model.with("userNickname", "The Lightbringer");
                model.with("mentors", appDAOs.getDAOMentors().getAllMentors());

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
