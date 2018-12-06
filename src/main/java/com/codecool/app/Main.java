package com.codecool.app;

import com.codecool.app.dao.AppDAOs;
import com.codecool.app.dao.SQLImpl.AppDAOsSQL;
import com.codecool.app.view.AppViews;
import com.codecool.app.view.consoleimpl.AppViewsConsoleImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        AppViews appViews = new AppViewsConsoleImpl();
        AppDAOs appDAOs = new AppDAOsSQL();
        QuestStore questStore = new QuestStore(appViews, appDAOs);
        try{
            questStore.run();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(0);
        }
    }
}
