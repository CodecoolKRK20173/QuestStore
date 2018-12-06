package com.codecool.app.dao.SQLImpl;

import com.codecool.app.connectors.SQLConnector;
import com.codecool.app.dao.DAOClasses;
import com.codecool.app.user.model.Class;
import com.codecool.app.user.model.Codecooler;
import com.codecool.app.user.model.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOClassesSQL implements DAOClasses {
    private Connection connection;

    public DAOClassesSQL() {
        this.connection = SQLConnector.getConnection();
    }

    @Override
    public void insertClass(Class newClass) {
        String sql = "INSERT INTO classes (name) VALUES (?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newClass.getName());
            ps.execute();
            ps.close();
        } catch (SQLException e){
            System.out.println("Couldn't insert new Class");
            e.printStackTrace();
        }
    }

    @Override
    public void updateClass(int id, Class updateClass) {
        String sql = "UPDATE classes SET name=? WHERE class_id=?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, updateClass.getName());
            ps.setInt(2, id);
            ps.execute();
            ps.close();
        } catch (SQLException e){
            System.out.println("Couldn't update Class with given id");
        }
    }

    @Override
    public Class getClass(int id) {
        Class myClass = new Class();
        String sql = "SELECT * FROM classes WHERE class_id=?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                setClassBasedOnResultSet(myClass, resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Couldn't get Class with given id");
        }
        return myClass;
    }

    @Override
    public List<Class> getAllClasses() {
        List<Class> classes = new ArrayList<>();
        String sql = "SELECT * FROM classes";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                Class newClass = new Class();
                setClassBasedOnResultSet(newClass, resultSet);
                classes.add(newClass);
            }
        } catch (SQLException e) {
            System.out.println("Couldn't get Class with given id");
        }
        return classes;
    }

    @Override
    public void deleteClass(int id) {
        String sql = "DELETE FROM classes WHERE user_id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ps.close();
        } catch (SQLException e){
            System.out.println("Couldn't find class with given id");
        }
    }

    @Override
    public List<Mentor> getClassMentors(int class_id) {
        List<Mentor> mentors = new ArrayList<>();
        String sql = "SELECT * FROM classes " +
                "LEFT JOIN class_mentors ON classes.class_id=class_mentors.class_id " +
                "LEFT JOIN mentors ON class_mentors.user_id = mentors.user_id " +
                "WHERE class_id =?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,class_id );
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                Mentor mentor = new DAOMentorsSQL().extractMentorFromResultSet(resultSet);
                mentors.add(mentor);
            }
        } catch (SQLException e){
            System.out.println("Couldn't find mentors belonging to given class");
        }
        return null;
    }

    @Override
    public List<Codecooler> getClassCodecoolers(int class_id) {
        List<Codecooler> codecoolers = new ArrayList<>();
        String sql = "SELECT * FROM classes\n" +
                "LEFT JOIN codecoolers ON classes.class_id = codecoolers.class_id " +
                "WHERE classes.class_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, class_id);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                Codecooler codecooler = new DAOCodecoolersSQL().extractCodecoolerFromResultSet(resultSet);
                codecoolers.add(codecooler);
            }
        } catch (SQLException e){
            System.out.println("Couldn't find codecoolers belonging to given class");
        }
        return codecoolers;
    }

    private Class setClassBasedOnResultSet(Class myClass, ResultSet resultSet) throws SQLException{
        String name = resultSet.getString("name");
        int id = resultSet.getInt("class_id");
        myClass.setName(name);
        myClass.setClass_id(id);
        return myClass;
    }
}