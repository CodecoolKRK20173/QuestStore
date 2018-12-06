package com.codecool.app.login;

import com.codecool.app.dao.DAOAccounts;
import com.codecool.app.messages.ErrorMessages;
import com.codecool.app.messages.LoginMessages;
import com.codecool.app.view.LoginView;

import java.util.NoSuchElementException;

public class LoginController {
    private LoginView loginView;
    private DAOAccounts daoAccounts;
    private Account loggedAccount;
    private boolean isUserLogged;

    public LoginController(LoginView loginView, DAOAccounts daoAccounts){
        this.loginView = loginView;
        this.daoAccounts = daoAccounts;
        isUserLogged = false;
    }

    public void logIn() throws NoSuchElementException{
        Account userTryingToLogIn = loginView.getAccountInformation();
        loggedAccount = getCorrespondingAccountFromDB(userTryingToLogIn);
        isUserLogged = true;
    }

    public void logOut(){
        isUserLogged = false;
    }

    public boolean isUserLogged(){
        return isUserLogged;
    }

    public Account getLoggedAccount(){
        return loggedAccount;
    }

    private Account getCorrespondingAccountFromDB(Account userTryingToLogIn){
        return userTryingToLogIn = daoAccounts.getAccountByNicknameAndPassword(userTryingToLogIn.getNickname(), userTryingToLogIn.getPassword());
    }
}
