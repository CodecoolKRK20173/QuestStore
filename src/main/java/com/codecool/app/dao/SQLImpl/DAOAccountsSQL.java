package com.codecool.app.dao.SQLImpl;

import com.codecool.app.connectors.SQLConnector;
import com.codecool.app.dao.DAOAccounts;
import com.codecool.app.login.AccessLevel;
import com.codecool.app.login.Account;
import com.codecool.app.messages.ErrorMessages;

import java.sql.*;
import java.util.NoSuchElementException;

public class DAOAccountsSQL implements DAOAccounts {
    private Connection connection;

    public DAOAccountsSQL() {
        this.connection = SQLConnector.getConnection();
    }

    @Override
    public Account getAccountByNicknameAndPassword(String nickname, String password) {
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM accounts WHERE nick=? AND password=?");
            ps.setString(1, nickname);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()){
                return extractAccountFromResultSet(resultSet);
            }

            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        throw new NoSuchElementException(new ErrorMessages().getUSER_NOT_REGISTERED_MESSAGE());
    }

    @Override
    public Account getAccountBySessionId(String sessionId) {
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM accounts WHERE session_id=?");
            ps.setString(1, sessionId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return extractAccountFromResultSet(resultSet);
            }
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return new Account();
    }

    @Override
    public void insertAccount(Account account) {
        String sql = "INSERT INTO accounts (nick, type, password) VALUES (?, ?, ?)";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getNickname());
            ps.setString(2, account.getAccessLevel().toString());
            ps.setString(3, account.getPassword());
            ps.execute();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(int id, Account newAccount) {
        String sql = "UPDATE accounts SET nick=?, type=?, password=?, session_id=? WHERE user_id=?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newAccount.getNickname());
            ps.setString(2, newAccount.getAccessLevel().toString());
            ps.setString(3, newAccount.getPassword());
            ps.setString(4, newAccount.getSessionID());
            ps.setInt(5, id);
            ps.execute();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(int id) {
        try{
            Statement stmt = connection.createStatement();
            stmt.execute("DELETE FROM accounts WHERE id=" + id);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Account extractAccountFromResultSet(ResultSet resultSet) throws SQLException{
        Account account = new Account(resultSet.getString("nick"), resultSet.getString("password"));
        account.setAccessLevel(AccessLevel.getAccessLevel(resultSet.getString("type")));
        account.setId(resultSet.getInt("user_id"));
        account.setSessionID(resultSet.getString("session_id"));
        return account;
    }

    @Override
    public boolean isValidUserType(String sessionId, String userType) {
        boolean isValid = false;
        Account account = getAccountBySessionId(sessionId);
        if(account.getAccessLevel().toString().equals(userType)){
            isValid = true;
        }
        return isValid;
    }
}
