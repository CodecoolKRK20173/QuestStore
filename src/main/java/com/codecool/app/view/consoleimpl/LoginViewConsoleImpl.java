package com.codecool.app.view.consoleimpl;

import com.codecool.app.login.Account;
import com.codecool.app.login.LoginMessages;
import com.codecool.app.view.LoginView;

public class LoginViewConsoleImpl extends ViewConsoleImpl implements LoginView {
    LoginMessages loginMessages;

    public LoginViewConsoleImpl(){
        loginMessages = new LoginMessages();
    }

    @Override
    public Account getAccount() {
        String nickname, password;

        println(loginMessages.getASK_FOR_NICKNAME());
        nickname = getInput();
        println(loginMessages.getASK_FOR_PASSWORD());
        password = getInput();

        return new Account(nickname, password);
    }
}
