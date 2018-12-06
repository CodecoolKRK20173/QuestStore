package com.codecool.app.dao.SQLImpl;

import com.codecool.app.connectors.SQLConnector;
import com.codecool.app.dao.DAOCodecoolers;
import com.codecool.app.dao.DAOMentors;
import com.codecool.app.user.model.Codecooler;
import com.codecool.app.user.model.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOCodecoolersSQL implements DAOCodecoolers {
    private Connection connection;

    public DAOCodecoolersSQL() {
        this.connection = SQLConnector.getConnection();
    }

    @Override
    public void insertCodecooler (Codecooler newCodecooler) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO codecoolers (user_id, name, surname, nick, balance, total_exp, class_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, newCodecooler.getId());
            ps.setString(2, newCodecooler.getFirstName());
            ps.setString(3, newCodecooler.getLastName());
            ps.setString(4, newCodecooler.getNickname());
            ps.setInt(5, newCodecooler.getBalance());
            ps.setInt(6, newCodecooler.getTotalExp());
            ps.setInt(7, newCodecooler.getClassId());
            ps.execute();
            ps.close();
        } catch (SQLException e){
            System.out.println("Couldn't insert new Codecooler");
            e.printStackTrace();
        }
    }

    @Override
    public void updateCodecooler(int id, Codecooler updatedCodecooler) {
        try {
            PreparedStatement ps = connection.prepareStatement(" UPDATE codecoolers SET name=?, surname=?, nick=?, balance=?, earned=?, class_id=?" +
                            "WHERE user_id =?");
            ps.setString(1, updatedCodecooler.getFirstName());
            ps.setString(2, updatedCodecooler.getLastName());
            ps.setString(3, updatedCodecooler.getNickname());
            ps.setInt(4, updatedCodecooler.getBalance());
            ps.setInt(5, updatedCodecooler.getTotalExp());
            ps.setInt(6, updatedCodecooler.getClassId());
            ps.setInt(7, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e){
            System.out.println("Couldn't insert new Codecooler, check id");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCodecooler(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM codecoolers WHERE user_id = ? ");
            ps.setInt(1, id);
            ps.execute();
            ps.close();
        } catch (SQLException e){
            System.out.println("Couldn't find codecooler with given id");
        }
    }

    @Override
    public Codecooler getCodecooler(int id) {
        Codecooler codecooler = null;
        String sql = "SELECT * FROM codecoolers WHERE user_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return extractCodecoolerFromResultSet(resultSet);
            }

        }catch (SQLException e) {
            System.out.println("Couldn't find codecooler with given id");
        }
        return codecooler;
    }

    @Override
    public List<Codecooler> getAllCodecoolers() {
        List<Codecooler> codecoolers = new ArrayList<>();
        String sql = "SELECT * FROM codecoolers";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                codecoolers.add(extractCodecoolerFromResultSet(resultSet));
            }
        }catch (SQLException e) {
            System.out.println("Couldn't find codecooler with given id");
        }
        return codecoolers;
    }

    Codecooler extractCodecoolerFromResultSet(ResultSet resultSet) throws SQLException{
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String nickname = resultSet.getString("nick");
            int balance = resultSet.getInt("balance");
            int totalExp = resultSet.getInt("total_exp");
            int userId = resultSet.getInt("user_id");
            int classId = resultSet.getInt("class_id");
            Codecooler codecooler = new Codecooler(name, surname);
            codecooler.setNickname(nickname);
            codecooler.setBalance(balance);
            codecooler.setTotalExp(totalExp);
            codecooler.setId(userId);
            codecooler.setClassId(classId);
            return codecooler;
    }
}
