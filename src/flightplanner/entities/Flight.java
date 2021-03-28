package flightplanner.entities;

import java.time.LocalDateTime;

public class Flight {
    private int ID;
    private String flightNo;
    private Airport departure;
    private Airport arrival;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Seat[] seats;
    public Flight(int ID, String flightNo, Airport departure, Airport arrival, LocalDateTime departureTime, LocalDateTime arrivalTime,Seat[] seats){
        this.ID = ID;
        this.flightNo = flightNo;
        this.departure = departure;
        this.arrival = arrival;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seats = seats;
    }

    public int getID() {
        return ID;
    }
    public void setID(int id){
        ID = id;
    }
    public String getFlightNo() {
        return flightNo;
    }

    public Airport getDeparture() {
        return departure;
    }

    public Airport getArrival() {
        return arrival;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    public Seat getSeat(String seatNumber){
        // skrifa eitthvað sniðugt hér??
        return seats[0];
    }
    public Seat[] getSeats(){
        return seats;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }
}
