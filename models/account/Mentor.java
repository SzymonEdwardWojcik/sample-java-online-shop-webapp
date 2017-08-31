package models.account;

import models.account.Login;
import models.account.Email;
import models.account.Password;


public class Mentor extends Codecooler {

    private Integer classId;

    public Mentor(Login login, Password password, Email email, String name, String surname) {
        super(login, password, email, name, surname);
        this.classId = null;
    }

    public Mentor(Login login, Password password, Email email, String name, String surname, Integer classId) {
        super(login, password, email, name, surname);
        this.classId = classId;
    }

    public Integer getClassId() {
        return this.classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }


}
