package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server implements Runnable{

    private static final int PORT = 8000;
    private static final String LOCALHOST = "127.0.0.1";

    @Override
    public void run() {
        try (
            Socket socket = new Socket(LOCALHOST, PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {

            String buffer;
            while ((buffer = reader.readLine())!= null){

                //TODO
                System.out.println(buffer);
            }

        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
