package flightplanner.ui;

import flightplanner.data.FlDataConnection;
import flightplanner.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class profileController implements Initializable {
    public Label firstNameLabel;
    public ListView<Booking> bookingList;
    private ObservableList<Booking> bookings = FXCollections.observableArrayList();
    private FlDataConnection connection;
    private Info information;

    public profileController() {
        connection = FlDataConnection.getInstance();
        information = Info.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = information.getUser();
        firstNameLabel.setText(user.getFirstName() + " " + user.getLastName());
        try{
            System.out.println(information.getUser().getID());
            ArrayList<Booking> bookingsArrayList = connection.getBookings(information.getUser().getID());
            bookings.addAll(bookingsArrayList);
            bookingList.setItems(bookings);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }


/**
 * When this method is called, it will change the scene to "Bókunarstaðfesting"
 * <p>
 * oookidoki hér er fullt af villum en þegar þetta fæst til að virka þá á þetta
 * að virka þannig að þegar maður keyrir og ýtir á "Bóka" þá poppar nýr scenebuilder
 * gluggi upp með næsta scene-i
 * - SEV
 */
    public void backToFlightSearch(ActionEvent event) throws IOException {
        Parent flightsearchParent = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene clickSaetaScene = new Scene(flightsearchParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickSaetaScene);
        window.show();
    }
}
