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

public class saetavalController implements Initializable {

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

    }
}
