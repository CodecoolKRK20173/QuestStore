package com.codecool.app.httphandlers.mentor;

import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.AppDAOs;
import com.codecool.app.httphandlers.TemplatesPaths;
import com.codecool.app.user.model.Mentor;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.Optional;


public class MentorHandler implements HttpHandler {
    private TemplatesPaths templatesPaths = new TemplatesPaths();
    private AppDAOs appDAOs;
    private CookieHelper cookieHelper;

    public MentorHandler(AppDAOs appDAOs, CookieHelper cookieHelper) {
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

            if(appDAOs.getDAOAccounts().isValidUserType( sessionId, "MENTOR")) {
                createAdminResponse(httpExchange, sessionId);
            } else {
                System.out.println("Unauthorized request for mentor");
                httpExchange.getResponseHeaders().add("Location", "/");
                httpExchange.sendResponseHeaders(303, 0);

            }
        }
    }

    private void createAdminResponse(HttpExchange httpExchange, String sessionId) throws IOException{
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatesPaths.MENTOR_PROFILE);
        JtwigModel model = JtwigModel.newModel();
        // TODO modify dao to request data based on sessionId
        int id = appDAOs.getDAOAccounts().getAccountBySessionId(sessionId).getId();
        String nickname = appDAOs.getDAOAccounts().getAccountBySessionId(sessionId).getNickname();
        Mentor mentor = appDAOs.getDAOMentors().getMentor(id);
        String fullName = mentor.getFirstName() + " " + mentor.getLastName();
        model.with("userName", nickname);
        model.with("userNickname", fullName);
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
