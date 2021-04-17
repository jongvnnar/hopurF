package flightplanner.controllers;

import flightplanner.data.FlDataConnection;
import flightplanner.entities.*;

import java.sql.SQLException;

public class FlightController {
    private static FlightController instance = null;
    private final FlightSearchController searchController = FlightSearchController.getInstance();
    private Info information;
    private FlDataConnection connection;

    private FlightController(){
        information = Info.getInstance();
        connection = FlDataConnection.getInstance();
    }

    public static FlightController getInstance() {
        if(instance == null){
            instance = new FlightController();
        }
        return instance;
    }

    /**
     * Changes Flight number of Flight, for admin.
     * @param id id of flight
     * @param newNum new flight number.
     * @throws Exception if database connection fails.
     */
    public void changeFlightNo(int id, String newNum) throws Exception {
        Flight flight = searchController.searchFlightById(id);
        flight.setFlightNo(newNum);
    }

    /**
     * Saves flight information for booking process.
     * @param flight the Flight object to save.
     */
    public void saveFlightInfo(Flight flight){
        information.setFlight(flight);
    }
}
