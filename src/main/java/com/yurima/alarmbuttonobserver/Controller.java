package com.yurima.alarmbuttonobserver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    private Thread serverThread;

    @FXML
    private Button startButton;

    @FXML
    private void onStartButtonClick(){
        serverThread = new Thread(new Server());
        serverThread.start();
        System.out.println("Server started");

    }
}
