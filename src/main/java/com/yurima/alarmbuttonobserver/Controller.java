package com.yurima.alarmbuttonobserver;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;

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
}
