package com.codecool.app.dao;

import com.codecool.app.user.model.Class;
import com.codecool.app.user.model.Codecooler;
import com.codecool.app.user.model.Mentor;

import java.util.List;

public interface DAOClasses {
    void insertClass(Class newClass);
    void updateClass(int id, Class updateClass);
    Class getClass(int id);
    List<Class> getAllClasses();
    void deleteClass(int id);
    List<Codecooler> getClassCodecoolers(int class_id);
    List<Mentor> getClassMentors(int class_id);
}
