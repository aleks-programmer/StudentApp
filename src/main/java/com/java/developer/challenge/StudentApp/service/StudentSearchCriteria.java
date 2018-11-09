package com.java.developer.challenge.StudentApp.service;

import java.io.Serializable;

public class StudentSearchCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    private String value;

    public StudentSearchCriteria(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
