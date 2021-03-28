package flightplanner.data;

import flightplanner.entities.*;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReturnNullDataMock implements FlightDataConnection{
    private static FlightDataConnection instance = null;

    private ReturnNullDataMock(){
    }

    public static FlightDataConnection getInstance(){
        if(instance == null){
            instance = new ReturnNullDataMock();
        }
        return instance;
    }

    public Flight getFlightById(int id) throws SQLException {
        return null;
    }

    public Person getPerson(int id) throws SQLException{
        return null;
    }

    public Booking getBooking(int id) throws SQLException {
        return null;
    }

    public void createBooking(Booking booking) throws SQLException {

    }

    public void updateFood(int id, Boolean newFood) throws SQLException {
    }
}
