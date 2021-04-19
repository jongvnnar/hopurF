package flightplanner.ui;

import flightplanner.data.FlDataConnection;
import flightplanner.entities.Airport;
import flightplanner.entities.Flight;
import flightplanner.entities.Info;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button bookButton;
    @FXML
    private Button clearFiltersButton;
    @FXML
    private ComboBox<Airport> departureCombo;

    @FXML
    private ComboBox<Airport> arrivalCombo;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private ListView<Flight> flightListView;

    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField ssTextField;

    private ObservableList<Flight> flights = FXCollections.observableArrayList();
    private FlDataConnection connection;
    private Info information;

    public Controller() {
        connection = FlDataConnection.getInstance();
        information = Info.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departureCombo.getItems().removeAll(departureCombo.getItems());
        departureCombo.getItems().addAll(getAirportsDest());


        arrivalCombo.getItems().removeAll(arrivalCombo.getItems());
        arrivalCombo.getItems().addAll(getAirportsDest());
        try {
            flights.addAll(connection.getAllFlights());
            flightListView.setItems(flights);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    private ArrayList<Airport> getAirportsDest() {
        ArrayList<Airport> airports = null;
        try {
            airports = connection.getAirports();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return airports;
    }


    public void filter() {
        Airport arrive = arrivalCombo.getSelectionModel().getSelectedItem();
        Airport depart = departureCombo.getSelectionModel().getSelectedItem();
        LocalDate fromTime = fromDatePicker.getValue();
        LocalDate toTime = toDatePicker.getValue();
        try {
            flights.clear();
            flights.addAll(connection.getFlightsByFilter(depart, arrive, fromTime, toTime));
            flightListView.setItems(flights);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void clearFilters() {
        arrivalCombo.getSelectionModel().select(null);
        departureCombo.getSelectionModel().select(null);
        fromDatePicker.setValue(null);
        toDatePicker.setValue(null);
        try {
            flights.clear();
            flights.addAll(connection.getAllFlights());
            flightListView.setItems(flights);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void saveInfo() {
        information.setFlight(flightListView.getSelectionModel().getSelectedItem());
    }


    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        saveInfo();
        Parent saetaParent = FXMLLoader.load(getClass().getResource("saetaval.fxml"));

        Scene clickSaetaScene = new Scene(saetaParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickSaetaScene);
        window.show();
    }

    public void changeToProfile(ActionEvent event) throws IOException{
        Parent profileParent = FXMLLoader.load(getClass().getResource("profile.fxml"));

        Scene clickProfileScene = new Scene(profileParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickProfileScene);
        window.show();
    }

    public void changeTologin(ActionEvent event) throws IOException{
        Parent loginParent = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene clickloginScene = new Scene(loginParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickloginScene);
        window.show();
    }
}



