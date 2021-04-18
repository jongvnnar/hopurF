package flightplanner.controllers;

import flightplanner.data.FlDataConnection;
import flightplanner.entities.*;

public class FlightBookingController {
    private static FlightBookingController instance = null;
    private Info information;
    private FlDataConnection connection;
    private FlightBookingController(){
        information = Info.getInstance();
        connection = FlDataConnection.getInstance();
    }

    public static FlightBookingController getInstance() {
        if(instance == null){
            instance = new FlightBookingController();
        }
        return instance;
    }
    /**
     * Creates booking based on information saved.
     */
    public void createBooking() throws Exception{
        Passenger passenger = information.getCurrentPassenger();
        User customer = information.getUser();
        Flight flight = information.getFlight();
        Seat seat = information.getSeat();
        Booking booking = new Booking(-1, passenger, customer, flight, seat, flight.getPrice(), "", false);
        // Uppfæra sætið svo það sé bókað
        connection.updateSeat(flight.getID(), seat.getSeatNumber(), true);
        // Ef passenger sami og user þá uppfæra upplýsingar
        if(passenger.getKennitala().equals(customer.getKennitala())){
            connection.updatePassenger(passenger);
            booking.getPassenger().setID(customer.getID());
        }
        // ef ekki þá búa til nýjan passenger
        else{
            connection.createPassenger(passenger);
            // og sækja og uppfæra ID fyrir félagann í leiðinni.
            int passengerID = connection.getPassenger(passenger.getKennitala()).getID();
            booking.getPassenger().setID(passengerID);
        }
        // Búa til bókun
        connection.createBooking(booking);
    }

    public void cancelBooking(Booking booking) throws Exception{
        connection.deleteBooking(booking);
        connection.updateSeat(booking.getFlight().getID(), booking.getSeat().getSeatNumber(), false);
    }
}
