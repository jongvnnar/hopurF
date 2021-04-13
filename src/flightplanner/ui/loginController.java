package flightplanner.ui;

import flightplanner.data.FlDataConnection;
import flightplanner.entities.Info;
import flightplanner.entities.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    public TextField emailTextField;
    public PasswordField passwordTextField;
    private FlDataConnection connection;
    private Info information;

    public loginController() {
        connection = FlDataConnection.getInstance();
        information = Info.getInstance();
    }

    public void changeBookButtonPushed(ActionEvent event) throws IOException {
        if(validLogin()) {
            saveInfo();
            Parent loginParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene clickloginScene = new Scene(loginParent);

            //This line gets the Stage Information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(clickloginScene);
            window.show();
        }
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Villa við innskráningu");
            a.show();

        }
    }

    public boolean validLogin(){
        boolean retVal = true;
        if(emailTextField.getText().trim().equals("")){
            emailTextField.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
        }
        if(passwordTextField.getText().trim().equals("")){
            passwordTextField.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
            return false;
        }
        try{
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            connection.getUser(email, password);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void saveInfo(){
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        try{
            User user = connection.getUser(email, password);
            information.setUser(user);
        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            a.show();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
