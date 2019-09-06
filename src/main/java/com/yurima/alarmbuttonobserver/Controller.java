package com.yurima.alarmbuttonobserver;

import com.yurima.alarmbuttonobserver.db.Client;
import com.yurima.alarmbuttonobserver.db.Model;
import com.yurima.alarmbuttonobserver.edit.AddController;
import com.yurima.alarmbuttonobserver.edit.EditController;
import com.yurima.alarmbuttonobserver.edit.EditFormController;
import com.yurima.alarmbuttonobserver.log.EventLogger;
import com.yurima.alarmbuttonobserver.msg.AlarmMessage;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Server.ServerStateListener, Initializable {

    private Model model;
    private Thread serverThread;
    private Server server;
    private Image connectedImage = new Image("/connected.png");
    private Image disconnectedImage = new Image("/disconnected.png");

    /*------------- Client variables--------------*/
    private ObservableList<Client> clientList;
    private Client selectedClient = null;

    private EventLogger eventLogger;

    @FXML
    private Button startButton;
    @FXML
    private Label alarmMessageLabel;
    @FXML
    private ListView<Client> clientListView;
    @FXML
    private ListView<EventLogger.Record> logListView;
    @FXML
    private WebView webView;

    private WebEngine webEngine;



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

        logListView.setCellFactory(recordListView -> new ListCell<>() {
            @Override
            protected void updateItem(EventLogger.Record item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setTextFill(null);
                } else {
                    setText(item.getText());
                    setTextFill(item.getColor());
                }
            }
        });

        eventLogger = new EventLogger(logListView);

        // initialize webview
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/ymap.html").toString());
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
        updateClientList();
    }

    @FXML
    private void onEditButtonClick() throws IOException {
        Client client = clientListView.getSelectionModel().getSelectedItem();
        if (client == null) return;
        openEditForm(new EditController(model, client));
        updateClientList();
    }

    @FXML
    private void onDeleteButtonClick() throws SQLException {
        Client client = clientListView.getSelectionModel().getSelectedItem();
        if (client == null) return;
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
        eventLogger.log(EventLogger.SERVER_ON_MESSAGE);
        Platform.runLater( () -> {
                startButton.setGraphic(new ImageView(connectedImage));
        });
    }

    @Override
    public void onDisconnect() {
        eventLogger.log(EventLogger.SERVER_OFF_MESSAGE);
        Platform.runLater( () -> {
            startButton.setGraphic(new ImageView(disconnectedImage));
        });
    }

    @Override
    public void onAlarmMessageReceived(AlarmMessage msg) {
        for (Client client : clientListView.getItems()) {
            if (
                    (msg.getId() != null && msg.getId() == client.getClientId()) ||
                    (msg.getPhone() != null && msg.getPhone().equals(client.getPhone()))
            ) {
                eventLogger.log(msg, client);
                if (client.getLatitude() != 0 && client.getLongitude()!= 0){
                    double lat = client.getLatitude();
                    double lng = client.getLatitude();
                    Platform.runLater( () -> {
//                        webEngine.executeScript("setCenter(" + lat + "," + lng + ")");
                        webEngine.executeScript("setCenter(57.2222, 57.9999)");
                    });

                }

                return;
            }
        }

        eventLogger.log(EventLogger.ERROR_MESSAGE);
    }


}
