package com.yurima.alarmbuttonobserver.edit;

import com.yurima.alarmbuttonobserver.db.Client;
import com.yurima.alarmbuttonobserver.db.Model;
import javafx.fxml.FXML;

import java.awt.*;
import java.sql.SQLException;

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
        //TODO
        System.out.println("OK in AddForm");
        Client client = new Client();
        client.setClientId(Integer.parseInt(id.getText()));
        client.setName((name.getText()));
        client.setAddress(address.getText());
        client.setPhone(phone.getText());
        client.setLatitude(Double.parseDouble(latitude.getText()));
        client.setLongitude(Double.parseDouble(longitude.getText()));
        try {
            model.addClient(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCancelButtonClick(){
        //TODO
        System.out.println("Cancel in AddForm");
    }
}
