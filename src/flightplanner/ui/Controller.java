package flightplanner.ui;

import flightplanner.controllers.FlightSearchController;
import flightplanner.data.FlDataConnection;
import flightplanner.entities.Airport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
        departureCombo.getItems().removeAll(departureCombo.getItems());
        departureCombo.getItems().addAll("Reykjavík", "Akureyri", "Egilsstaðir", "Ísafjörður", "Keflavík");
        departureCombo.getSelectionModel().select("Veldu brottfararstað");

        arrivalCombo.getItems().removeAll(arrivalCombo.getItems());
        arrivalCombo.getItems().addAll("Reykjavík", "Akureyri", "Egilsstaðir", "Ísafjörður", "Keflavík");
        arrivalCombo.getSelectionModel().select("Veldu brottfararstað");
    }


    /**
     * When this method is called, it will change the scene to "Bókunarstaðfesting"
     * <p>
     * oookidoki hér er fullt af villum en þegar þetta fæst til að virka þá á þetta
     * að virka þannig að þegar maður keyrir og ýtir á "Bóka" þá poppar nýr scenebuilder
     * gluggi upp með næsta scene-i
     * - SEV
     */
    public void changeScreenButtonPushed(ActionEvent event) throws Exception {
        Parent bookParent = FXMLLoader.load(getClass().getResource("ClickBoka.fxml"));
        Scene clickBokaScene = new Scene(bookParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickBokaScene);
        window.show();
    }
}
