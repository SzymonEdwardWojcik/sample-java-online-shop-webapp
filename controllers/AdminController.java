package controllers;

import database.MentorDAO;

import models.account.Admin;
import models.account.Mentor;

import models.accountdata.Login;
import models.accountdata.Password;
import models.accountdata.Mail;

import views.CodecoolerView;
import views.AdminView;

import java.util.Scanner;
import java.util.ArrayList;

public class AdminController {

    private Admin admin;
    private MentorDAO mentorData = new MentorDAO();
    ArrayList<Mentor> mentors;

    public AdminController(Admin admin) {
        this.admin = admin;
        this.mentors = mentorData.loadAll();
    }

    public void createMentor() {

        Login login = new Login(CodecoolerView.getString("login"));
        Password password = new Password(CodecoolerView.getString("password"));
        Mail mail = new Mail(CodecoolerView.getString("mail"));
        String name = CodecoolerView.getString("name");
        String surname = CodecoolerView.getString("surname");

        Mentor mentor = new Mentor(login, password, mail, name, surname);
        mentorData.save(mentor);
    }

    public void menu() {
        Scanner input = new Scanner(System.in);
        boolean inMenu = true;

        while (inMenu) {
            AdminView.viewOptions();
            String option = System.console().readLine();

            if (option.equals("1")) {
                AdminView.showAllMentors();
            }
            else if (option.equals("2")) {
                createMentor();
            }
            else if (option.equals("0")) {
                inMenu = false;
            }
        }
    }
}
