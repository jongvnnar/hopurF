package flightplanner.controllers;

import flightplanner.entities.*;

import java.sql.SQLException;

public class FlightController {
    private static FlightController instance = null;
    private final FlightSearchController searchController = FlightSearchController.getInstance();

    private FlightController(){
    }

    public static FlightController getInstance() {
        if(instance == null){
            instance = new FlightController();
        }
        return instance;
    }

    public void changeFlightNo(int id, String newNum) throws Exception {
        Flight flight = searchController.searchFlightById(id);
        flight.setFlightNo(newNum);
    }
}
