package com.codecool.app.view.consoleimpl;

import com.codecool.app.view.QSView;

public class QSViewConsoleImpl extends ViewConsoleImpl implements QSView {

    @Override
    public void printMenu(String[] options) {
        for (int i=0; i<options.length; i++){
            println(String.format("(%d) %s", i+1, options[i]));
        }
    }
}
