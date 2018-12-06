package com.codecool.app.user.view.consoleimpl;

import com.codecool.app.login.AccessLevel;
import com.codecool.app.login.Account;
import com.codecool.app.messages.AdminMessages;
import com.codecool.app.messages.ErrorMessages;
import com.codecool.app.messages.LoginMessages;
import com.codecool.app.messages.UserCreationMessages;
import com.codecool.app.user.model.Mentor;
import com.codecool.app.user.view.AdminView;
import com.codecool.app.view.consoleimpl.QSViewConsoleImpl;

import java.util.List;

public class AdminViewConsoleImpl extends QSViewConsoleImpl implements AdminView {
    @Override
    public void showMentor(Mentor mentor) {
        println(mentor.toString());
    }

    @Override
    public void showMentorsAndTheirClassess(List<Mentor> mentors) {
        for (Mentor mentor : mentors) {
            println(mentor.toString());
            println("Classess: WEB :)");
        }
    }

    @Override
    public Account getAccountInformation() {
        String nickname, password;
        Account accountInformation;
        LoginMessages loginMessages = new LoginMessages();

        println(loginMessages.getASK_FOR_NICKNAME());
        nickname = getInput();
        println(loginMessages.getASK_FOR_PASSWORD());
        password = getInput();
        accountInformation = new Account(nickname, password);
        setAccountAccessLevel(accountInformation);

        return accountInformation;
    }

    private void setAccountAccessLevel(Account accountInformation){
        AdminMessages adminMessages = new AdminMessages();
        String accessLevelString;
        boolean isAccessLevelValid = false;

        while (! isAccessLevelValid){
            println(adminMessages.getASK_FOR_ACCESS_LEVEL());
            println(String.format("%s: %s", adminMessages.getAVAILABLE_ACCESS_LEVELS_MESSAGE(), AccessLevel.getAccessLevelsString()));
            accessLevelString = getInput();
            accountInformation.setAccessLevel(AccessLevel.getAccessLevel(accessLevelString));
            if (accountInformation.getAccessLevel() != AccessLevel.NOBODY) {
                isAccessLevelValid = true;
            }
        }
    }

    @Override
    public Mentor getMentorInformation() {
        UserCreationMessages userCreationMessages = new UserCreationMessages();
        String firstName, lastName, email;

        println(userCreationMessages.getASK_FOR_FIRST_NAME());
        firstName = getInput();
        println(userCreationMessages.getASK_FOR_LAST_NAME());
        lastName = getInput();
        println(userCreationMessages.getASK_FOR_EMAIL());
        email = getInput();

        return new Mentor(firstName, lastName, email);
    }
}
