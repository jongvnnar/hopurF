package flightplanner.entities;

import flightplanner.entities.Person;

import java.time.LocalDate;

public class User extends Person {
    private final String role;
    // geyma password sem string, classic
    private String password = null;
    public User(String role,int id, String firstName, String lastName, String kennitala, String email, String phoneNumber) {
        super(id, firstName, lastName, kennitala, email, phoneNumber);
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
    public String toString() {
        return "User{" +
                "role='" + role + '\'' +
                '}';
    }
}
