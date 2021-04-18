package flightplanner.ui;

import flightplanner.data.FlDataConnection;
import flightplanner.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class clickbokaController implements Initializable {
    public Label departLabel;
    public Label arriveLabel;
    public Label departTimeLabel;
    public Label arriveTimeLabel;
    public Label flightNoLabel;
    public Label seatNoLabel;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public Label dateLabel;
    public Label pfirstNameLabel;
    public Label plastNameLabel;
    public Label pEmailLabel;
    public Label pPhoneLabel;
    public Label pSsLabel;
    public Label cfirstNameLabel;
    public Label clastNameLabel;
    public Label cEmailLabel;
    public Label cPhoneLabel;
    public Label cSsLabel;
    public Label insuranceLabel;
    public Label luggageLabel;
    public Label foodLabel;
    public Label wheelchairLabel;
    public Label isExtraLuggageLabel;
    public Label isHealthIssuesLabel;
    public Label extraLuggageLabel;
    public Label healthIssuesLabel;
    private FlDataConnection connection;
    private Info information;
    public clickbokaController() {
        connection = FlDataConnection.getInstance();
        information = Info.getInstance();
    }
    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
            Parent bokaParent = FXMLLoader.load(getClass().getResource("upplysingar.fxml"));
            Scene clickBokaScene = new Scene(bokaParent);
            //This line gets the Stage Information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(clickBokaScene);
            window.show();
        }

    public void changeBookButtonPushed(ActionEvent event) throws IOException {
        try{
            createBooking();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        Parent bokaParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene clickBokaScene = new Scene(bokaParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickBokaScene);
        window.show();

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Bókun staðfest. Takk fyrir að fljúga með okkur");
        a.show();
    }
    public void createBooking() throws Exception{
        // Öll flug kosta sem sagt 100000 kr hjá okkur núna :))))))))
        // Lögum fyrir skil
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Flight flight = information.getFlight();
        //Flight
        departLabel.setText(flight.getDeparture().getName());
        arriveLabel.setText(flight.getArrival().getName());
        dateLabel.setText(flight.getDepartureTime().format(dateFormatter));
        departTimeLabel.setText(flight.getDepartureTime().format(timeFormatter));
        arriveTimeLabel.setText(flight.getArrivalTime().format(timeFormatter));
        flightNoLabel.setText(flight.getFlightNo());
        seatNoLabel.setText(information.getSeat().getSeatNumber());

        //Passenger
        Passenger passenger = information.getCurrentPassenger();
        pfirstNameLabel.setText(passenger.getFirstName());
        plastNameLabel.setText(passenger.getLastName());
        pEmailLabel.setText(passenger.getEmail());
        pPhoneLabel.setText(passenger.getPhoneNumber());
        pSsLabel.setText(passenger.getKennitala());

        //Customer
        User user = information.getUser();
        cfirstNameLabel.setText(user.getFirstName());
        clastNameLabel.setText(user.getLastName());
        cEmailLabel.setText(user.getEmail());
        cPhoneLabel.setText(user.getPhoneNumber());
        cSsLabel.setText(user.getKennitala());

        //Extra info
        insuranceLabel.setText(passenger.isInsurance() ? "Já" : "Nei");
        luggageLabel.setText(passenger.isLuggage() ? "Já" : "Nei");
        foodLabel.setText(passenger.isWantsFood() ? "Já" : "Nei");
        wheelchairLabel.setText(passenger.isWheelchair() ? "Já" : "Nei");
        extraLuggageLabel.setWrapText(true);
        healthIssuesLabel.setWrapText(true);
        if(passenger.getExtraLuggage().trim().equals("")) isExtraLuggageLabel.setText("");
        if(passenger.getHealthIssues().trim().equals("")) isHealthIssuesLabel.setText("");
        extraLuggageLabel.setText(passenger.getExtraLuggage());
        healthIssuesLabel.setText(passenger.getHealthIssues());
    }
}
