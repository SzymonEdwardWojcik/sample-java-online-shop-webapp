package com.codecool.jlamas.models.accountdata;

import com.codecool.jlamas.database.LoginDAO;

public class Login extends AccountData {
    private static final Integer LOGINMAXLEN = 20;
    private static final Integer LOGINMINLEN = 5;

    public Login() {

    }

    public Login(String value) {
        super(value);
    }

    public static boolean isValid(String value){
        boolean isUnique = isLoginUnique(value);
        boolean isLengthValid = isLengthValid(value, LOGINMINLEN, LOGINMAXLEN);

        return isLengthValid && isUnique;
    }

    public static boolean isLoginUnique(String value){
        LoginDAO loginData = new LoginDAO();

        if (loginData.findLogin(value) != null) {
            return false;
        }
        return true;
    }
}
