package com.yurima.alarmbuttonobserver;

import com.yurima.alarmbuttonobserver.msg.AlarmMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class TestConductor {

    private static final int PORT = 8000;
    private static Socket socket;

    public static void main(String[] args) {

        while (true) {

            try (ServerSocket serverSocket = new ServerSocket(PORT);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                 ){
                while (true) {
                    socket = serverSocket.accept();
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
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
                            case "1":
                                String msg = new String ("{" +
                                    AlarmMessage.ID_NAME + ":1," +
                                    AlarmMessage.DATE_NAME + ":" + Calendar.getInstance().getTime().getTime()+ "," +
                                    AlarmMessage.PHONE_NAME + ":+79991234567" +
                                    "}");
                                writer.println(msg);
                                writer.flush();
                                break;
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
