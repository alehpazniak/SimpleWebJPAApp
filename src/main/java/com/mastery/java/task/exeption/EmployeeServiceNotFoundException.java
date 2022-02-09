package com.mastery.java.task.exeption;

public class EmployeeServiceNotFoundException extends RuntimeException {

    public EmployeeServiceNotFoundException(String massage) {
        super(massage);
    }

    public EmployeeServiceNotFoundException() {
    }

}
