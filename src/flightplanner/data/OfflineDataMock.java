package flightplanner.data;

import flightplanner.entities.*;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class OfflineDataMock implements FlightDataConnection{
    private static FlightDataConnection instance = null;
    private Flight mockSearchFlight;

    private OfflineDataMock(){
        //Seat[] seats = {new Seat("ff", false)};
        mockSearchFlight = new Flight(1, "TF-OMG", new Airport(2, "KEF", "Keflavik", "keflavik"), new Airport(3, "RVK", "Reykjavik", "keflavik"), LocalDateTime.now(), LocalDateTime.now(),null);
    }

    public static FlightDataConnection getInstance(){
        if(instance == null){
            instance = new OfflineDataMock();
        }
        return instance;
    }

    public Flight getFlightById(int id) throws SQLException {
        throw new SQLException("Server is offline");
    }

    public Person getPerson(int id) throws SQLException{
        throw new SQLException("Server is offline");
    }

    public Booking getBooking(int id) throws SQLException {
        throw new SQLException("Server is offline");
    }

    public void createBooking(Booking booking ) throws SQLException {
        throw new SQLException("Server is offline");
    }

    public void updateFood(int id, Boolean newFood) throws SQLException {
        throw new SQLException("Server is offline");
    }
}
