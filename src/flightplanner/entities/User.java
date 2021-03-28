package flightplanner.entities;

import flightplanner.entities.Person;

import java.time.LocalDate;

public class User extends Person {
    private final String role;
    public User(String role,int id, String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber) {
        super(id, firstName, lastName, dateOfBirth, email, phoneNumber);
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
