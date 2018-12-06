package com.codecool.app;

import com.codecool.app.cookies.CookieHelper;
import com.codecool.app.dao.AppDAOs;
import com.codecool.app.httphandlers.admin.*;
import com.codecool.app.httphandlers.LoginHandler;
import com.codecool.app.httphandlers.StaticHandler;
import com.codecool.app.httphandlers.mentor.MentorHandler;
import com.codecool.app.messages.ErrorMessages;
import com.codecool.app.login.Account;
import com.codecool.app.login.LoginController;
import com.codecool.app.user.controller.AdminController;
import com.codecool.app.user.controller.CodecoolerController;
import com.codecool.app.user.controller.MentorController;
import com.codecool.app.user.controller.UserController;
import com.codecool.app.view.AppViews;
import com.codecool.app.view.QSView;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.NoSuchElementException;

public class QuestStore {
    private final String SESSION_COOKIE_NAME = "sessionId";
    private final String[] MENU_OPTIONS = {"Login", "Exit"};
    private ErrorMessages errorMessages;
    private AppViews appViews;
    private QSView view;
    private AppDAOs appDAOs;
    private CookieHelper cookieHelper = new CookieHelper(SESSION_COOKIE_NAME);

    public QuestStore(AppViews appViews, AppDAOs appDAOs){
        this.appViews = appViews;
        this.appDAOs = appDAOs;
        view = appViews.getQSView();
        errorMessages = new ErrorMessages();
    }

    public void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);


        server.createContext("/", new LoginHandler(appDAOs.getDAOAccounts(), cookieHelper));
        server.createContext("/static", new StaticHandler());
        server.createContext("/admin", new AdminHandler(appDAOs, cookieHelper));
        server.createContext("/admin/mentors", new AdminMentorsHandler(appDAOs, cookieHelper));
        server.createContext("/admin/addMentor", new AdminAddMentorHandler(appDAOs, cookieHelper));
        server.createContext("/admin/classes", new AdminClassesHandler(appDAOs, cookieHelper));
        server.createContext("/admin/levels", new AdminLevelsHandler(appDAOs, cookieHelper));
        server.createContext("/mentor/profile", new MentorHandler(appDAOs, cookieHelper));
        server.setExecutor(null);

        server.start();
    }

//    public void run(){
//        boolean isRunning = true;
//        String choice;
//
//        while (isRunning){
//            view.printMenu(MENU_OPTIONS);
//
//            choice = view.getInput();
//            switch (choice){
//                case "1":
//                    logInAndRunControllerForUser();
//                    break;
//                case "2":
//                    isRunning = false;
//                    break;
//                default:
//                    view.printError(errorMessages.getNO_OPTION_MESSAGE());
//                    break;
//            }
//        }
//    }

    private void logInAndRunControllerForUser(){
        LoginController loginController = new LoginController(appViews.getLoginView(), appDAOs.getDAOAccounts());

        try{
            loginController.logIn();
        } catch (NoSuchElementException e){
            view.printError(e.getMessage());
            return;
        }

        try{
            UserController userController = getControllerForUser(loginController.getLoggedAccount());
            userController.run();
        } catch (IllegalAccessException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private UserController getControllerForUser(Account loggedUser) throws IllegalAccessException{
        switch (loggedUser.getAccessLevel()){
            case ADMIN:
                return new AdminController(appViews.getAdminView(), appDAOs);
            case MENTOR:
                return new MentorController();
            case CODECOOLER:
                return new CodecoolerController();
        }

        throw new IllegalAccessException(errorMessages.getNO_ACCESS_MESSAGE());
    }
}
