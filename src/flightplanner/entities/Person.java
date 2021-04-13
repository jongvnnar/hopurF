package flightplanner.entities;

import java.time.LocalDate;

public class Person {
    private int ID;
    private String firstName;
    private String lastName;
    // Setja kennitolu frekar
    private String kennitala;
    private String email;
    private String phoneNumber;
    //Beila á að hafa ID hérna? gagnagrunnurinn setur ID fyrir okkur
    public Person(int id, String firstName, String lastName, String kennitala, String email, String phoneNumber){
        this.ID = id;
        this.firstName =  firstName;
        this.lastName = lastName;
        this.email = email;
        this.kennitala = kennitala;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getKennitala() {
        return kennitala;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", kennitala=" + kennitala +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
