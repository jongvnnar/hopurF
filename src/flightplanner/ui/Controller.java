package flightplanner.ui;

import flightplanner.controllers.FlightSearchController;
import flightplanner.data.FlDataConnection;
import flightplanner.entities.Airport;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button bookButton;

    @FXML
    private ComboBox departureCombo;

    @FXML
    private ComboBox arrivalCombo;

    @FXML
    private DatePicker pickdateDatePicker;

    @FXML
    private ListView flightListView;

    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField ssTextField;
    FlightSearchController searchController;
    FlDataConnection connection;

    public Controller() {
        connection = new FlDataConnection();
        searchController = FlightSearchController.getInstance();
        searchController.setConnection(connection);
    }

    public ArrayList<Airport> getAirports() {
        ArrayList<Airport> airports = null;
        try {
            airports = searchController.searchAirports();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return airports;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
