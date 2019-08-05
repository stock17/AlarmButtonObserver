package com.yurima.alarmbuttonobserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestConductor {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket socket = serverSocket.accept()){

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
