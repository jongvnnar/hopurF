package flightplanner.data;

import flightplanner.entities.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Mock class to simulate a database connection that is online and always returns some object.
 * Implemented as a singleton.
 */
public class OnlineDataMock implements FlightDataConnection{
    private static FlightDataConnection instance = null;
    private Flight mockFlight;
    private Person mockPerson;
    private Booking mockBooking;
    private Passenger mockPassenger;
    private Seat mockSeat;

    /**
     * Private constructor, sets a number of mock objects to return.
     */
    private OnlineDataMock(){
        mockSeat = new Seat("ff", false);
        ArrayList<Seat> seats = new ArrayList<Seat>();
        seats.add(mockSeat);
        mockFlight = new Flight(1, "TF-OMG", new Airport(2, "KEF", "Keflavik"), new Airport(3, "RVK", "Reykjavik"), LocalDateTime.now(), LocalDateTime.now(),seats);
        mockPerson = new Person(1, "Jon", "Jonsson", LocalDate.of(1999, 9, 9), "email@email.is", "(354) 777-777");
        mockPassenger = new Passenger(1, "Jon", "Jonsson", LocalDate.of(1999, 9, 9), "email@email.is", "(354) 777-777");
        mockBooking = new Booking(1, mockPassenger, mockPerson, mockFlight, mockSeat, 200, "Heima", false);
    }

    /**
     * Returns the singleton instance of OnlineDataMock
     * @return OnlineDataMock instance
     */
    public static FlightDataConnection getInstance(){
        if(instance == null){
            instance = new OnlineDataMock();
        }
        return instance;
    }

    /**
     * "Searches" for a flight by ID.
     * @param id: id of object to return
     * @return a mock Flight object.
     * @throws SQLException never, but does throw in the offline mock object.
     */
    public Flight getFlightById(int id) throws SQLException {
        mockFlight.setID(id);
        return mockFlight;
    }

    /**
     * "Searches" for a person by ID.
     * @param id: id of object to return.
     * @return a mock Person object.
     * @throws SQLException never, but does throw in the offline mock object.
     */
    public Person getPerson(int id) throws SQLException{
        mockPerson.setID(id);
        return mockPerson;
    }

    /**
     * "Searches" for a booking by ID.
     * @param id: id of object to return.
     * @return a mock Booking object.
     * @throws SQLException never, but does throw in the offline mock object.
     */
    public Booking getBooking(int id) throws SQLException{
        mockBooking.setID(id);
        return mockBooking;
    }

    /**
     * "Creates" a new Booking in the database.
     * Actually just updates the single instance of a mock object to the new Booking.
     * @param booking: Booking object to send to database.
     * @throws SQLException never, but does throw in the offline mock object.
     */
    public void createBooking(Booking booking) throws SQLException{
        mockBooking = booking;
    }

    /**
     * "Updates" the food preference of the user with the parameter ID.
     * @param id: id of the Passenger we want to update.
     * @param newFood: new food preference, true or false.
     * @throws SQLException never, but does throw in the offline mock object.
     */
    public void updateFood(int id, Boolean newFood) throws SQLException{
        mockPassenger.setID(id);
        mockPassenger.setWantsFood(newFood);
    }
}
