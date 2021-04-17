package flightplanner.controllers;

import flightplanner.data.FlDataConnection;
import flightplanner.entities.Info;
import flightplanner.entities.Passenger;
import flightplanner.entities.User;

public class FlightUserController {
    private static FlightUserController instance = null;
    private Info information;
    private FlDataConnection connection;

    private FlightUserController(){
        information = Info.getInstance();
        connection = FlDataConnection.getInstance();

    }
    public static FlightUserController getInstance(){
        if(instance == null){
            instance = new FlightUserController();
        }
        return instance;
    }

    /**
     * Saves customer, who is the User
     * @param customer the user.
     */
    public void saveCustomer(User customer){
        information.setUser(customer);
    }

    /**
     * Logs in user
     * @param email user email
     * @param password user password
     * @throws Exception if connection lost or password wrong.
     */
    public void flightLogin(String email, String password) throws Exception{
        User customer = connection.getUser(email, password);
        saveCustomer(customer);
    }

    /**
     * Saves passenger
     */
    public void savePassenger(Passenger passenger){
        information.setCurrentPassenger(passenger);
    }
}
