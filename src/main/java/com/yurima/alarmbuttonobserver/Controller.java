package com.yurima.alarmbuttonobserver;

import com.yurima.alarmbuttonobserver.edit.AddController;
import com.yurima.alarmbuttonobserver.msg.AlarmMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller implements Server.ServerStateListener {

    private Thread serverThread;
    private Server server;
    private Image connectedImage = new Image("/connected.png");
    private Image disconnectedImage = new Image("/disconnected.png");

    @FXML
    private Button startButton;
    @FXML
    private Label alarmMessageLabel;


    @FXML
    private void onStartButtonClick(){
        server = new Server();
        server.setListener(this);
        serverThread = new Thread(server);
        serverThread.start();
    }

    @FXML
    private void onAddButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edit_form.fxml"));
        loader.setController(new AddController());
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
