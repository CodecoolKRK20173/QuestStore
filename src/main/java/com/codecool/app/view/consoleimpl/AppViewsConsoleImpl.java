package com.codecool.app.view.consoleimpl;

import com.codecool.app.user.view.AdminView;
import com.codecool.app.user.view.consoleimpl.AdminViewConsoleImpl;
import com.codecool.app.view.AppViews;
import com.codecool.app.view.LoginView;
import com.codecool.app.view.QSView;

public class AppViewsConsoleImpl implements AppViews {
    @Override
    public QSView getQSView() {
        return new QSViewConsoleImpl();
    }

    @Override
    public LoginView getLoginView() {
        return new LoginViewConsoleImpl();
    }

    @Override
    public AdminView getAdminView() {
        return new AdminViewConsoleImpl();
    }
}
