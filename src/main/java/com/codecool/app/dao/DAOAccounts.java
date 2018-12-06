package com.codecool.app.dao;

import com.codecool.app.login.Account;

public interface DAOAccounts {
    Account getAccountByNicknameAndPassword(String nickname, String password);
    Account getAccountBySessionId(String sessionId);
    boolean isValidUserType(String sessionId, String userType);
    void insertAccount(Account account);
    void updateAccount(int id, Account newAccount);
    void deleteAccount(int id);
}
