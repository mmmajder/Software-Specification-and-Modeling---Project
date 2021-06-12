package model;

import java.time.LocalDate;

public abstract class Person {
    private String name;
    private String surname;
    private String JMBG;
    private String phoneNumber;
    private LocalDate birthDate;
    private Account account;
}
