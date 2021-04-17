package flightplanner.controllers;

import flightplanner.data.FlDataConnection;
import flightplanner.entities.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class FlightSearchController{
    private static FlightSearchController instance = null;
    private FlDataConnection connection = null;
    private FlightSearchController(){
        connection = FlDataConnection.getInstance();
    }

    public static FlightSearchController getInstance(){
        if(instance == null){
            instance = new FlightSearchController();
        }
        return instance;
    }

    public Flight searchFlightById(int id) throws Exception{
        return connection.getFlightById(id);
    }
    public Person searchPerson(int userId) throws Exception{
        return connection.getPerson(userId);
    }

    public Booking searchBooking(int id) throws Exception{
        return connection.getBooking(id);
    }

    public ArrayList<Airport> getAirports() {
        ArrayList<Airport> airports = null;
        try {
            airports = connection.getAirports();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return airports;
    }

    public ArrayList<Flight> getAllFlights() throws Exception{
        return connection.getAllFlights();
    }

    public ArrayList<Flight> searchFlightsByFilter(Airport departure, Airport destination, LocalDate fromTime, LocalDate toTime) throws Exception{
        return connection.getFlightsByFilter(departure, destination, fromTime, toTime);
    }


}
