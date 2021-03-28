package flightplanner.controllers;

public class FlightUserController {
    private static FlightUserController instance = null;
    private FlightUserController(){

    }
    public static FlightUserController getInstance(){
        if(instance == null){
            instance = new FlightUserController();
        }
        return instance;
    }
}
