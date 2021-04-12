package flightplanner.ui;

<<<<<<< HEAD
import flightplanner.controllers.FlightSearchController;
import flightplanner.data.FlDataConnection;
import flightplanner.entities.Seat;
import javafx.beans.value.ObservableValue;
=======
import flightplanner.data.FlDataConnection;
import flightplanner.entities.Info;
>>>>>>> main
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class saetavalController implements Initializable {
    private FlDataConnection connection;
    private Info information;
    public saetavalController(){
        connection = FlDataConnection.getInstance();
        information = Info.getInstance();
    }
    public void changeBookButtonPushed(ActionEvent event) throws IOException {
        Parent upplParent = FXMLLoader.load(getClass().getResource("upplysingar.fxml"));
        Scene clickUpplScene = new Scene(upplParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickUpplScene);
        window.show();
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent saetaParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene clickSaetaScene = new Scene(saetaParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickSaetaScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ListView<Seat> listView = new ListView<>();
        ArrayList<Seat> seats = connection.getSeatsForFlight(information);
        listView.setCellFactory(CheckBoxListCell.forListView(new Callback<Seat, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Seat item) {
                return item.onProperty();
            }
        }));

    }
}
