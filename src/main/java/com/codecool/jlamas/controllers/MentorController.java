package com.codecool.jlamas.controllers;

import java.util.ArrayList;

import com.codecool.jlamas.database.MentorDAO;
import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.views.MentorView;

public class MentorController {

    private MentorView mentorView = new MentorView();
    private MentorDAO mentorDao = new MentorDAO();

    public MentorController() {
        
    }

    public void addMentor() {
        try {
            String name = mentorView.getName();
            String surname = mentorView.getSurname();
            Mail email = mentorView.getMail();

            Login login = new Login("mentor");
            Password password = new Password("mentor");
            Mentor mentor = new Mentor(login, password, email, name, surname);
            mentorDao.insert(mentor);

        } catch (InvalidUserDataException e) {

        }
    }

    public void editMentor() {
        ;
    }

    public void removeMentor() {
        try {
            Mentor mentor = chooseMentor();
            mentorDao.delete(mentor);

        } catch (IndexOutOfBoundsException e) {

        }
    }

    public Mentor chooseMentor() throws IndexOutOfBoundsException {
        ArrayList<Mentor> mentors = mentorDao.requestAll();
        mentorView.displayAll();
        Integer record = mentorView.getMenuOption();
        Integer index = record - 1;
        if (index >= mentors.size()) {
            throw new IndexOutOfBoundsException();
        }
        return mentors.get(index);
    }

    public void displayMentors() {
        ;
    }
}
