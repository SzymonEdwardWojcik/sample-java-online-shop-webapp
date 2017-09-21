package com.codecool.jlamas.controllers;

import com.codecool.jlamas.views.CodecoolerView;
import com.codecool.jlamas.models.account.Codecooler;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.account.Admin;
import com.codecool.jlamas.controllers.AdminMenuController;
import com.codecool.jlamas.database.LoginDAO;
import com.codecool.jlamas.database.UserDAO;

public class AppController {

    LoginDAO loginData;
    UserDAO userData;
    CodecoolerView view;

    public AppController() {
        this.loginData = new LoginDAO();
        this.userData = new UserDAO();
        this.view = new CodecoolerView();
    }

    public void login() {

        boolean isLogging = true;
        while (isLogging) {

            String login = this.view.getString("Login");
            String password = this.view.getString("Password");

            if (loginData.matchLogin(login, password)) {
                launchUserController(login);
            }

            this.view.reportWrongLoginData();
            String tryAgain = this.view.getString("Y or anything else");
            if (!tryAgain.equalsIgnoreCase("y")) {
                isLogging = false;
            }
        }
    }

    public void launchUserController(String login) {

        String userType = this.userData.getType(login);

        if (userType.equals("admin")) {
            Admin admin = this.userData.getAdmin(login);
            AdminMenuController adminMenu = new AdminMenuController(admin);
            adminMenu.start();
        } else if (userType.equals("mentor")) {
            Mentor mentor = this.userData.getMentor(login);
            MentorMenuController mentorMenu = new MentorMenuController(mentor);
            mentorMenu.start();
        } else if (userType.equals("student")) {
            // TODO
        }

    }
}
