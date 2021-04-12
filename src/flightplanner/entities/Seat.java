package flightplanner.entities;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Seat {
    private SimpleStringProperty seatNumber;
    private SimpleBooleanProperty isBooked;
    public Seat(String seatNumber, boolean isBooked){
        this.seatNumber = new SimpleStringProperty(seatNumber);
        this.isBooked = new SimpleBooleanProperty(isBooked);
    }

    public String getSeatNumber() {
        return seatNumber.get();
    }

    public boolean isBooked() {
        return isBooked.get();
    }

    public void setBooked(boolean booked) {
        isBooked.set(booked);
    }
    public SimpleBooleanProperty getBookedProperty(){
        return isBooked;
    }
    public String toString() {
        return getSeatNumber();
    }
}
