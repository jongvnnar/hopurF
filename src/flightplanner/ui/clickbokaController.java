package flightplanner.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class clickbokaController implements Initializable {
    public void changeBookButtonPushed(ActionEvent event) throws IOException {
        Parent bokaParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene clickBokaScene = new Scene(bokaParent);

        //This line gets the Stage Information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(clickBokaScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
