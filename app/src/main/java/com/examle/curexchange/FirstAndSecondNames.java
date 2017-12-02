package com.examle.curexchange;


import java.io.Serializable;

public class FirstAndSecondNames implements Serializable {

    private String firstName;
    private String secondName;

    public FirstAndSecondNames(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
