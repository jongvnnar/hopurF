package flightplanner.controllers;

import flightplanner.data.FlightDataConnection;
import flightplanner.entities.*;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class FlightSearchController{
    private static FlightSearchController instance = null;
    private FlightDataConnection dataConnection = null;
    private FlightSearchController(){
    }

    public static FlightSearchController getInstance(){
        if(instance == null){
            instance = new FlightSearchController();
        }
        return instance;
    }

    public void setConnection(FlightDataConnection dataConnection){
        this.dataConnection = dataConnection;
    }

    public Flight searchFlightById(int id) throws SQLException{
        return dataConnection.getFlightById(id);
    }
    public Person searchPerson(int userId) throws SQLException{
        return dataConnection.getPerson(userId);
    }

    public Booking searchBooking(int id) throws SQLException{
        return dataConnection.getBooking(id);
    }
}
