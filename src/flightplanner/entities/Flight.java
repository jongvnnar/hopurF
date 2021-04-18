package flightplanner.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Flight {
    private int ID;
    private String flightNo;
    private Airport departure;
    private Airport arrival;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private ArrayList<Seat> seats;
    private int price;
    public Flight(int ID, String flightNo, Airport departure, Airport arrival, LocalDateTime departureTime, LocalDateTime arrivalTime,ArrayList<Seat> seats, int price){
        this.ID = ID;
        this.flightNo = flightNo;
        this.departure = departure;
        this.arrival = arrival;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seats = seats;
        this.price = price;
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
        return null;
    }
    public ArrayList<Seat> getSeats(){
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

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String toString() {
        return "Flight{" +
                "ID=" + ID +
                ", flightNo='" + flightNo + '\'' +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", price = " + price +
                '}';
    }
}
