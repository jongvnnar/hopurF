package flightplanner;

import flightplanner.controllers.FlightController;
import flightplanner.controllers.FlightSearchController;
import flightplanner.data.OfflineDataMock;
import flightplanner.data.OnlineDataMock;
import flightplanner.data.ReturnNullDataMock;
import flightplanner.entities.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FlightTest {
    private Flight flug1;
    private FlightController flightController;
    private FlightSearchController searchController;
    @Before
    public void setUp(){
        ArrayList<Seat> seats = new ArrayList<Seat>();
        seats.add(new Seat("ff", false));
        flug1 = new Flight(1, "TF-OMG", new Airport(2, "KEF", "Keflavik", "keflavik"), new Airport(3, "RVK", "Reykjavik", "reykjavik"),LocalDateTime.now(), LocalDateTime.now(),seats, 10000);
        flightController = FlightController.getInstance();
        searchController = FlightSearchController.getInstance();
    }

    @After
    public void tearDown(){
        flug1 = null;
    }
/*
    @Test
    public void testId(){
        assertEquals(1, flug1.getID());
    }

    @Test
    public void testFlightSearchById(){
        searchController.setConnection(OnlineDataMock.getInstance());
        try {
            Flight flug = searchController.searchFlightById(3);
            assertEquals(3, flug.getID());
        }
        catch(SQLException e){
            fail("Exception thrown");
        }
    }

    @Test(expected = SQLException.class)
    public void testFlightOfflineSearch() throws SQLException {
        searchController.setConnection(OfflineDataMock.getInstance());
        searchController.searchFlightById(2);
    }

    @Test
    public void testFlightNullSearch(){
        searchController.setConnection(ReturnNullDataMock.getInstance());
        try {
            Flight flug = searchController.searchFlightById(1);
            assertNull(flug);
        }
        catch(SQLException e){
            fail("Exception thrown");
        }
    }
    @Test
    public void testChangeFlightNo(){
        searchController.setConnection(OnlineDataMock.getInstance());
        try {
            flightController.changeFlightNo(1, "TF-GGG");
            Flight flug = searchController.searchFlightById(1);
            assertEquals("TF-GGG", flug.getFlightNo());
        }
        catch(Exception e){
            fail("Exception thrown");
        }
    }

    @Test
    public void testSearchPerson(){
        searchController.setConnection(OnlineDataMock.getInstance());
        try {
            Person person = searchController.searchPerson(2);
            assertEquals(2, person.getID());
        }
        catch(SQLException e){
            fail("Exception thrown");
        }
    }

    @Test(expected = SQLException.class)
    public void testSearchPersonOffline() throws SQLException {
        searchController.setConnection(OfflineDataMock.getInstance());
        Person manneskja = searchController.searchPerson(2);
    }

    @Test
    public void testSearchPersonNull(){
        searchController.setConnection(ReturnNullDataMock.getInstance());
        try{
            Person manneskja = searchController.searchPerson(2);
            assertNull(manneskja);
        }
        catch(SQLException e){
            fail("Exception thrown");
        }
    }

    @Test
    public void testSearchBooking(){
        searchController.setConnection(OnlineDataMock.getInstance());
        try {
            Booking booking = searchController.searchBooking(1);
            assertEquals(1, booking.getID());
        }
        catch(SQLException e){
            fail("Exception thrown");
        }
    }
    @Test(expected = SQLException.class)
    public void testSearchBookingOffline() throws SQLException{
        searchController.setConnection(OfflineDataMock.getInstance());
        searchController.searchBooking(2);
    }

    @Test
    public void testSearchBookingNull(){
        searchController.setConnection(ReturnNullDataMock.getInstance());
        try{
            Booking booking = searchController.searchBooking(2);
            assertNull(booking);
        }
        catch(SQLException e){
            fail("Exception thrown");
        }
    }

 */
}
