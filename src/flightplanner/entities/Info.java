package flightplanner.entities;

import flightplanner.controllers.FlightSearchController;

import java.time.LocalDate;
import java.util.ArrayList;

public class Info {
    private static Info instance;
    //private User user = new User("user", 2, "Jón Gunnar", "Hannesson", "0606060606", "jgh@hi.is", "1111111");
    private User user;
    private Flight flight;
    private Seat seat;
    private Passenger currentPassenger;

    private Info(){

    }

    public static Info getInstance(){
        if(instance == null){
            instance = new Info();
        }
        return instance;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Passenger getCurrentPassenger() {
        return currentPassenger;
    }

    public void setCurrentPassenger(Passenger currentPassenger) {
        this.currentPassenger = currentPassenger;
    }

}
