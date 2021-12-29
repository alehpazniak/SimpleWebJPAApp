package com.mastery.java.task.exeption;

public class EmployeeNotFoundException extends RuntimeException {
    public static final String EMPLOYEE_NOT_FOUND_ERROR_MESSAGE = "Employee hasn't found";

    public EmployeeNotFoundException() {
        super(EMPLOYEE_NOT_FOUND_ERROR_MESSAGE);
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
