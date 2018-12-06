package com.codecool.app.user.view;

import com.codecool.app.login.Account;
import com.codecool.app.user.model.Mentor;
import com.codecool.app.view.QSView;

import java.util.List;

public interface AdminView extends QSView {
    public void showMentor(Mentor mentor);
    public void showMentorsAndTheirClassess(List<Mentor> mentors);
    public Account getAccountInformation();
    public Mentor getMentorInformation();
}
