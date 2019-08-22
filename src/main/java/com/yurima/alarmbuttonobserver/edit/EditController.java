package com.yurima.alarmbuttonobserver.edit;

import com.yurima.alarmbuttonobserver.db.Client;
import com.yurima.alarmbuttonobserver.db.Model;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditController implements EditFormController {

    private Model model;
    private Client client;

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;
    @FXML
    private TextField latitude;
    @FXML
    private TextField longitude;

    public EditController(Model model, Client client) {
        this.model = model;
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.id.setText(String.valueOf(client.getClientId()));
        name.setText(client.getName());
        address.setText(client.getAddress());
        phone.setText(client.getPhone());
        latitude.setText(String.valueOf(client.getLatitude()));
        longitude.setText(String.valueOf(client.getLongitude()));
    }

    @FXML
    @Override
    public void onOkButtonClick() {
        System.out.println("OK in EditForm");
        Client client = new Client();
        String text = this.id.getText();
        client.setClientId(Integer.parseInt(text));
        client.setName((name.getText()));
        client.setAddress(address.getText());
        client.setPhone(phone.getText());
        client.setLatitude(Double.parseDouble(latitude.getText()));
        client.setLongitude(Double.parseDouble(longitude.getText()));
        try {
            model.editClient(client);
            closeWindow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    @Override
    public void onCancelButtonClick() {
        System.out.println("Cancel in EditForm");
        closeWindow();
    }

    private void closeWindow(){
        Stage stage = (Stage) id.getScene().getWindow();
        stage.close();
    }


}
