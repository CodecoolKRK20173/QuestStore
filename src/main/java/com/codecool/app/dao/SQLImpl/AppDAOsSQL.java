package com.codecool.app.dao.SQLImpl;

import com.codecool.app.dao.DAOAccounts;
import com.codecool.app.dao.AppDAOs;
import com.codecool.app.dao.DAOClasses;
import com.codecool.app.dao.DAOMentors;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AppDAOsSQL implements AppDAOs {
    @Override
    public DAOAccounts getDAOAccounts() {
        return new DAOAccountsSQL();
    }

    @Override
    public DAOMentors getDAOMentors() {
        return new DAOMentorsSQL();
    }

    @Override
    public DAOClasses getDAOClasses() {
        return new DAOClassesSQL();
    }
}
