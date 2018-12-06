package com.codecool.app.user.model;

import java.util.List;

public class Class {
    private List<Mentor> mentors;
    private List<Codecooler> codecoolers;
    private String name;
    private int class_id;

    public Class() {}

    public Class(List<Mentor> mentors, List<Codecooler> codecoolers) {
        this.mentors = mentors;
        this.codecoolers = codecoolers;
    }

    public void setMentors(List<Mentor> mentors) {
        this.mentors = mentors;
    }

    public List<Mentor> getMentors() {
        return mentors;
    }

    public void setCodecoolers(List<Codecooler> codecoolers) {
        this.codecoolers = codecoolers;
    }

    public List<Codecooler> getCodecoolers() {
        return codecoolers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }
}
