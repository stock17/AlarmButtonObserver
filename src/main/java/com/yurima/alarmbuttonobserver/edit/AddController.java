package com.yurima.alarmbuttonobserver.edit;

import com.yurima.alarmbuttonobserver.db.Client;
import com.yurima.alarmbuttonobserver.db.Model;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddController implements EditFormController {

    private Model model;

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


    public AddController(Model model) {
        this.model = model;
    }

    @FXML
    public void onOkButtonClick() {
        System.out.println("OK in AddForm");
        Client client = new Client();
        String text = this.id.getText();
        client.setClientId(Integer.parseInt(text));
        client.setName((name.getText()));
        client.setAddress(address.getText());
        client.setPhone(phone.getText());
        client.setLatitude(Double.parseDouble(latitude.getText()));
        client.setLongitude(Double.parseDouble(longitude.getText()));
        try {
            model.addClient(client);
            closeWindow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCancelButtonClick(){
        System.out.println("Cancel in AddForm");
        closeWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void closeWindow(){
        Stage stage = (Stage) id.getScene().getWindow();
        stage.close();
    }
}
