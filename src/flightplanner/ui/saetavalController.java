package flightplanner.ui;

import flightplanner.data.FlDataConnection;
import flightplanner.entities.Seat;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ListCell;
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
    @FXML
    private ListView<Seat> seatListView = new ListView<>();
    @FXML
    private ObservableList<Seat> seatList = FXCollections.observableArrayList();
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

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seatListView = new ListView<>();
        try {
            ArrayList<Seat> seats = connection.getSeatsForFlight(information.getFlight().getID());
            seatList.addAll(seats);
            seatListView.setItems(seatList);
            seatListView.setCellFactory(CheckBoxListCell.forListView(new Callback<Seat, ObservableValue<Boolean>>(){
                @Override
                public ObservableValue<Boolean> call(Seat item) {
                    BooleanProperty observable = new SimpleBooleanProperty();
                    observable.addListener((obs, wasSelected, isNowSelected) ->
                            System.out.println("Check box for "+item.toString()+" changed from "+wasSelected+" to "+isNowSelected)
                    );
                    observable.set(item.isBooked());
                    return observable;
                    };
            }));
            System.out.println("uh");
            for(Seat e: seats){
                System.out.println(e.toString());
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
