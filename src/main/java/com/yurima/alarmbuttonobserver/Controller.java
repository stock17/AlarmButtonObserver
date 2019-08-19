package com.yurima.alarmbuttonobserver;

import com.yurima.alarmbuttonobserver.db.Client;
import com.yurima.alarmbuttonobserver.db.Model;
import com.yurima.alarmbuttonobserver.edit.AddController;
import com.yurima.alarmbuttonobserver.edit.EditController;
import com.yurima.alarmbuttonobserver.edit.EditFormController;
import com.yurima.alarmbuttonobserver.msg.AlarmMessage;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Controller implements Server.ServerStateListener, Initializable {

    private Model model;
    private Thread serverThread;
    private Server server;
    private Image connectedImage = new Image("/connected.png");
    private Image disconnectedImage = new Image("/disconnected.png");

    /*------------- Client variables--------------*/
    private ObservableList<Client> clientList;
    private Client selectedClient = null;

    @FXML
    private Button startButton;
    @FXML
    private Label alarmMessageLabel;
    @FXML
    private ListView<Client> clientListView;



    public Controller(){
        this.model = new Model();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clientListView.setCellFactory(param -> new ListCell<Client>() {
            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);

                if (empty || client == null || client.getName() == null) {
                    setText(null);
                } else {
                    setText(client.getName());
                }
            }
        });
        clientListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        updateClientList();
    }

    private void updateClientList() {
        if (clientList != null) clientList.clear();
        clientList = model.getClients();
        Platform.runLater(() ->
            clientListView.getItems().setAll(clientList));
    }


    @FXML
    private void onStartButtonClick(){
        server = new Server();
        server.setListener(this);
        serverThread = new Thread(server);
        serverThread.start();
    }

    @FXML
    private void onAddButtonClick() throws IOException {
        openEditForm(new AddController(model));
    }

    @FXML
    private void onEditButtonClick() throws IOException {
        openEditForm(new EditController());
    }

    @FXML
    private void onDeleteButtonClick() throws SQLException {
        Client client = clientListView.getSelectionModel().getSelectedItem();
        model.deleteClient(client);
        updateClientList();
    }


    private void openEditForm(EditFormController controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edit_form.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public void onConnect() {
        System.out.println("Server is connected");
        Platform.runLater( () -> {
                startButton.setGraphic(new ImageView(connectedImage));
        });
    }

    @Override
    public void onDisconnect() {
        System.out.println("Server is disconnected");
        Platform.runLater( () -> {
            startButton.setGraphic(new ImageView(disconnectedImage));
        });
    }

    @Override
    public void onAlarmMessageReceived(AlarmMessage msg) {
        Platform.runLater( () -> {
            alarmMessageLabel.setText(msg.toString());
        });

    }


}
