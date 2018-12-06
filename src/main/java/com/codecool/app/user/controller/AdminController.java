package com.codecool.app.user.controller;

import com.codecool.app.dao.AppDAOs;
import com.codecool.app.login.Account;
import com.codecool.app.messages.ErrorMessages;
import com.codecool.app.messages.UserCreationMessages;
import com.codecool.app.user.model.Mentor;
import com.codecool.app.user.view.AdminView;

import java.util.NoSuchElementException;

public class AdminController implements UserController{
    private final String[] ADMIN_OPTIONS = {
            "Create new account", "Show Mentors", "View Mentor's details",
            "Edit Mentor", "Delete Mentor", "Create class", "Create level of experience", "Exit"
    };
    private ErrorMessages errorMessages;
    private UserCreationMessages userCreationMessages;
    private AdminView view;
    private AppDAOs appDAOs;
    private boolean isRunning;

    public AdminController(AdminView view, AppDAOs appDAOs) {
        this.view = view;
        this.appDAOs = appDAOs;
        isRunning = true;
        errorMessages = new ErrorMessages();
        userCreationMessages = new UserCreationMessages();
    }

    @Override
    public void run() {
        while (isRunning){
            handleMenu();
        }
    }

    private void handleMenu(){
        String userChoice;

        view.printMenu(ADMIN_OPTIONS);
        try{
            userChoice = view.getInput();
        } catch (NoSuchElementException e){
            view.printError(errorMessages.getEMPTY_INPUT_MESSAGE());
            return;
        }
        int id;

        switch (userChoice){
            case "1":
                handleCreatingAccount();
                break;
            case "2":
                view.showMentorsAndTheirClassess(appDAOs.getDAOMentors().getAllMentors());
                break;
            case "3":
                id = chooseId();
                view.showMentor(appDAOs.getDAOMentors().getMentor(id));
                break;
            case "4":
                id = chooseId();
                Mentor mentor = handleCreatingMentor(id);
                appDAOs.getDAOMentors().updateMentor(id, mentor);
                break;
            case "5":
                id = chooseId();
                appDAOs.getDAOMentors().deleteMentor(id);
                break;
            case "6":
                // TO DO
                break;
            case "7":
                break;
            case "8":
                isRunning = false;
                break;
            default:
                view.printError(errorMessages.getNO_OPTION_MESSAGE());
                break;
        }
    }

    private int chooseId(){
        view.println(userCreationMessages.getASK_FOR_ID());
        return Integer.parseInt(view.getInput());
    }

    private void handleCreatingAccount(){
        Account account = view.getAccountInformation();
        appDAOs.getDAOAccounts().insertAccount(account);
        account = appDAOs.getDAOAccounts().getAccountByNicknameAndPassword(account.getNickname(), account.getPassword());
        int user_id = account.getId();
        
        switch (account.getAccessLevel()){
            case MENTOR:
                Mentor newMentor = handleCreatingMentor(user_id);
                appDAOs.getDAOMentors().insertMentor(newMentor);
                break;
            case CODECOOLER:
                // TODO
                break;
        }
    }

    private Mentor handleCreatingMentor(int user_id){
        boolean areMentorInformationsValid = false;
        Mentor mentor = null;

        do{
            mentor = view.getMentorInformation();
            // Check if informations are valid
            areMentorInformationsValid = true;
        } while (! areMentorInformationsValid);
        mentor.setId(user_id);
        return mentor;
    }
}
