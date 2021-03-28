package flightplanner.controllers;

public class FlightBookingController {
    private static FlightBookingController instance = null;
    private FlightBookingController(){

    }

    public static FlightBookingController getInstance() {
        if(instance == null){
            instance = new FlightBookingController();
        }
        return instance;
    }
}
