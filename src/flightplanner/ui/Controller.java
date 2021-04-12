package flightplanner.ui;

import flightplanner.controllers.FlightSearchController;
import flightplanner.data.FlDataConnection;
import flightplanner.entities.Airport;
import flightplanner.entities.Flight;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button bookButton;

    @FXML
    private ComboBox<String> departureCombo;

    @FXML
    private ComboBox<String> arrivalCombo;

    @FXML
    private DatePicker pickdateDatePicker;

    @FXML
    private ListView<Flight> flightListView;

    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField ssTextField;

    private ObservableList<Flight> flights = FXCollections.observableArrayList();
    FlightSearchController searchController;
    FlDataConnection connection;

    public Controller() {
        connection = FlDataConnection.getInstance();
        searchController = FlightSearchController.getInstance();
        searchController.setConnection(connection);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departureCombo.getItems().removeAll(departureCombo.getItems());
        departureCombo.getItems().addAll(getAirportsDest());
        departureCombo.getSelectionModel().select("Veldu brottfararstað");

        arrivalCombo.getItems().removeAll(arrivalCombo.getItems());
        arrivalCombo.getItems().addAll(getAirportsDest());
        arrivalCombo.getSelectionModel().select("Veldu brottfararstað");
        try {
            flights.addAll(connection.getAllFlights());
            flightListView.setItems(flights);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private ArrayList<String> getAirportsDest() {
        ArrayList<Airport> airports;
        ArrayList<String> retVal = new ArrayList();
        try {
            airports = connection.getAirports();
            for (Airport e : airports) {
                retVal.add(e.getFullName());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return retVal;
    }


    /**
     * When this method is called, it will change the scene to "Bókunarstaðfesting"
     * <p>
     * oookidoki hér er fullt af villum en þegar þetta fæst til að virka þá á þetta
     * að virka þannig að þegar maður keyrir og ýtir á "Bóka" þá poppar nýr scenebuilder
     * gluggi upp með næsta scene-i
     * - SEV
     */


    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent saetaParent = FXMLLoader.load(getClass().getResource("saetaval.fxml"));
        Scene clickSaetaScene = new Scene(saetaParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickSaetaScene);
        window.show();
    }
}
