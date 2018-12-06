package com.codecool.app.dao;

import com.codecool.app.user.model.Codecooler;
import com.codecool.app.user.model.Mentor;

import java.util.List;

public interface DAOCodecoolers {
    void insertCodecooler(Codecooler newCodecooler);
    void updateCodecooler(int id, Codecooler updatedCodecooler);
    Codecooler getCodecooler(int id);
    List<Codecooler> getAllCodecoolers();
    void deleteCodecooler(int id);
}
