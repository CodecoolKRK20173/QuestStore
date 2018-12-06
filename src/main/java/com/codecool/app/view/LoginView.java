package com.codecool.app.view;

import com.codecool.app.login.Account;

import java.util.NoSuchElementException;

public interface LoginView extends View{
    public Account getAccountInformation() throws NoSuchElementException;
}
