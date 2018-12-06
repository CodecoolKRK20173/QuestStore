package com.codecool.app.dao;

import com.codecool.app.user.model.Mentor;

import java.util.List;

public interface DAOMentors {
    void insertMentor(Mentor newMentor);
    void updateMentor(int id, Mentor updatedMentor);
    Mentor getMentor(int id);
    List<Mentor> getAllMentors();
    void deleteMentor(int id);
}
