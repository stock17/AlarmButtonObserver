package com.yurima.alarmbuttonobserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TestConductor {

    private static final int PORT = 8000;
    private static Socket socket;

    public static void main(String[] args) {

        while (true) {

            try (ServerSocket serverSocket = new ServerSocket(PORT);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
                 ){
                while (true) {
                    socket = serverSocket.accept();
                    String testCommand;
                    while ((testCommand = reader.readLine()) != null){
                        System.out.println("Command:" + testCommand);
                        switch (testCommand){
                            case "0":
                                socket.shutdownInput();
                                socket.shutdownOutput();
                                socket.close();
                                System.out.println("SOcket is closed");
                                throw new IOException();
                            default:
                        }
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
