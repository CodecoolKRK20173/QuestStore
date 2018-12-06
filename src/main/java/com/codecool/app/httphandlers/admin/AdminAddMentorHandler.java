package com.codecool.app.httphandlers.admin;

import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.AppDAOs;
import com.codecool.app.httphandlers.TemplatesPaths;
import com.codecool.app.login.AccessLevel;
import com.codecool.app.login.Account;
import com.codecool.app.user.model.Mentor;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AdminAddMentorHandler implements HttpHandler {
    private TemplatesPaths templatesPaths = new TemplatesPaths();
    private AppDAOs appDAOs;
    private CookieHelper cookieHelper;

    public AdminAddMentorHandler(AppDAOs appDAOs, CookieHelper cookieHelper) {
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

            if(appDAOs.getDAOAccounts().isValidUserType( sessionId, "ADMIN")) {
                JtwigTemplate template = JtwigTemplate.classpathTemplate(templatesPaths.ADMIN_ADD_MENTOR);
                JtwigModel model = JtwigModel.newModel();

                // Example
                model.with("userName", appDAOs.getDAOAccounts().getAccountBySessionId(sessionId).getNickname());
//                model.with("userNickname", "The Lightbringer");
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
        } else if (method.equals("POST")){
            // TODO don't know if i have to verify user session here
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            System.out.println(formData);
            Map<String, String> map = parseFormData(formData);
            addMentor(map);
            httpExchange.getResponseHeaders().add("Location", "/admin/mentors");
            httpExchange.sendResponseHeaders(303, 0);
        }
    }

    private Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
    private void addMentor(Map<String, String> map){
        Account newAccount = new Account();
        newAccount.setAccessLevel(AccessLevel.getAccessLevel("MENTOR"));
        String nick = map.get("nickname");
        String password = map.get("password");
        newAccount.setNickname(nick);
        newAccount.setPassword(password);
        appDAOs.getDAOAccounts().insertAccount(newAccount);
        Account account = appDAOs.getDAOAccounts().getAccountByNicknameAndPassword(nick, password);
        Mentor newMentor = new Mentor();
        newMentor.setId(account.getId());
        newMentor.setFirstName(map.get("firstName"));
        newMentor.setLastName(map.get("lastName"));
        newMentor.setEmail(map.get("email"));
        appDAOs.getDAOMentors().insertMentor(newMentor);
    }
}
