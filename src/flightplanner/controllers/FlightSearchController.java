package flightplanner.controllers;

import flightplanner.data.FlDataConnection;
import flightplanner.entities.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class FlightSearchController{
    private static FlightSearchController instance = null;
    private FlDataConnection connection;
    private Info information;
    private FlightSearchController(){
        connection = FlDataConnection.getInstance();
        information = Info.getInstance();
    }

    public static FlightSearchController getInstance(){
        if(instance == null){
            instance = new FlightSearchController();
        }
        return instance;
    }

    /**
     * Search flight by it's id.
     * @param id id of Flight object to return.
     * @return Flight object corresponding to the id in parameters.
     * @throws Exception if search fails.
     */
    public Flight searchFlightById(int id) throws Exception{
        return connection.getFlightById(id);
    }

    /**
     * Search user by id.
     * @param userId id of User object to return.
     * @return User object corresponding to the id in parameters.
     * @throws Exception if search fails.
     */
    public Person searchPerson(int userId) throws Exception{
        return connection.getPerson(userId);
    }

    /**
     * Search booking by id.
     * @param id id of Booking object to return.
     * @return Booking object corresponding to the id in parameters.
     * @throws Exception if search fails.
     */
    public Booking searchBooking(int id) throws Exception{
        return connection.getBooking(id);
    }

    /**
     * Get all airports in database.
     * @return ArrayList of all Airport objects in the database.
     */
    public ArrayList<Airport> getAirports() {
        ArrayList<Airport> airports = null;
        try {
            airports = connection.getAirports();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return airports;
    }

    /**
     * Get all airports for specific city.
     * @param cityName name of city.
     * @return  ArrayList of all Airport objects corresponding to the city.
     * @throws Exception if search fails.
     */
    public ArrayList<Airport> getAirportByCity(String cityName) throws Exception{
        return connection.getAirportCityName(cityName);
    }

    /**
     * Get all flights in database.
     * @return ArrayList of all Flight objects in the database.
     * @throws Exception if search fails.
     */
    public ArrayList<Flight> getAllFlights() throws Exception{
        return connection.getAllFlights();
    }

    /**
     * Filtered flight search. Set any parameter as null if you don't want to use it as a filter.
     * @param departure Airport object to depart from.
     * @param destination Airport object corresponding to destination.
     * @param fromTime only return flights departing after this time.
     * @param toTime only return flights departing before this time.
     * @return ArrayList of Flight objects corresponding to the filtered search.
     * @throws Exception if search fails.
     */
    public ArrayList<Flight> searchFlightsByFilter(Airport departure, Airport destination, LocalDate fromTime, LocalDate toTime) throws Exception{
        return connection.getFlightsByFilter(departure, destination, fromTime, toTime);
    }

    /**
     * Get all bookings for logged in user.
     * @return ArrayList of Booking objects where the currently logged in user is the customer.
     * @throws Exception if search fails.
     */
    public ArrayList<Booking> getBookingsForUser() throws Exception{
        return connection.getBookings(information.getUser().getID());
    }
}
