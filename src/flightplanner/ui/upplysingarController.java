package flightplanner.ui;

import flightplanner.data.FlDataConnection;
import flightplanner.entities.Info;
import flightplanner.entities.Passenger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class upplysingarController implements Initializable {
    @FXML
    public TextField firstnameTextField;
    @FXML
    public TextField lastnameTextField;
    @FXML
    public CheckBox luggage;
    @FXML
    public TextField emailTextField;
    @FXML
    public TextField ssTextField;
    @FXML
    public CheckBox foodCheck;
    @FXML
    public CheckBox wheelchairCheck;
    @FXML
    public TextArea healthIssues;
    @FXML
    public CheckBox bookForSelf;
    @FXML
    public TextArea extraLuggage;
    @FXML
    public TextField phoneTextField;
    @FXML
    public CheckBox insuranceCheck;
    public AnchorPane myAnchorPane;
    private FlDataConnection connection;
    private Info information;
    public upplysingarController() {
        connection = FlDataConnection.getInstance();
        information = Info.getInstance();
    }

    public void changeBookButtonPushed(ActionEvent event) throws IOException {
        if(isFullyFilled()) {
            saveInfo();
            Parent bokaParent = FXMLLoader.load(getClass().getResource("clickboka.fxml"));
            Scene clickBokaScene = new Scene(bokaParent);
            //This line gets the Stage Information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(clickBokaScene);
            window.show();
        } else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Fylla þarf alla mikilvæga textareiti");
            a.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // Til að athuga hvort búið sé að fylla út í alla reiti sem þarf að fylla í.
    public boolean isFullyFilled(){
        boolean retVal = true;
        if(firstnameTextField.getText().trim().equals("")){
            firstnameTextField.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
            retVal = false;
        }
        if(lastnameTextField.getText().trim().equals("")){
            lastnameTextField.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
            retVal = false;
        }
        if(ssTextField.getText().trim().equals("")){
            ssTextField.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
            retVal = false;
        }
        if(emailTextField.getText().trim().equals("")){
            emailTextField.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
            retVal = false;
        }
        if(phoneTextField.getText().trim().equals("")){
            phoneTextField.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
            retVal = false;
        }
        return retVal;
    }

    public void saveInfo(){
        Passenger passenger = new Passenger(-1, firstnameTextField.getText(), lastnameTextField.getText(),ssTextField.getText(),emailTextField.getText(),phoneTextField.getText());
        passenger.setInsurance(insuranceCheck.isSelected());
        passenger.setHealthIssues(healthIssues.getText());
        passenger.setExtraLuggage(extraLuggage.getText());
        passenger.setWheelchair(wheelchairCheck.isSelected());
        passenger.setLuggage(luggage.isSelected());
        passenger.setWantsFood(foodCheck.isSelected());
        information.setCurrentPassenger(passenger);
    }

    // Setja upp bókun ef user vill setja inn eigin upplýsingar úr aðgangi.
    public void setupBooking(ActionEvent actionEvent) {
        CheckBox source = (CheckBox) actionEvent.getTarget();
        if(source.isSelected()){
            try {
                Passenger passenger = connection.getPassenger(information.getUser().getID());
                firstnameTextField.setText(passenger.getFirstName());
                lastnameTextField.setText(passenger.getLastName());
                ssTextField.setText(passenger.getKennitala());
                emailTextField.setText(passenger.getEmail());
                healthIssues.setText(passenger.getHealthIssues());
                extraLuggage.setText(passenger.getExtraLuggage());
                phoneTextField.setText(passenger.getPhoneNumber());
                wheelchairCheck.setSelected(passenger.isWheelchair());
                foodCheck.setSelected(passenger.isWantsFood());
                luggage.setSelected(passenger.isLuggage());
                insuranceCheck.setSelected(passenger.isInsurance());
            }
            catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        else{
            for(Node e: myAnchorPane.getChildren()){
                if(e instanceof TextArea){
                    TextArea textArea = (TextArea)e;
                    textArea.clear();
                }
                else if(e instanceof TextField){
                    TextField textField = (TextField) e;
                    textField.clear();
                }
                else if(e instanceof CheckBox){
                    CheckBox checkBox = (CheckBox) e;
                    checkBox.setSelected(false);
                }
            }
        }
    }
}
