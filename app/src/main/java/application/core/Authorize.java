package application.core;

import java.io.*;
import java.net.Socket;

public class Authorize {
    LocalDatabase database;
    private static int serverPort = 9345;
    public boolean authorize(String username, String password, String ip){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(ip, serverPort);
                    PrintWriter serverOutput = new PrintWriter(socket.getOutputStream());
                    serverOutput.write(username + password);
                    BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return database.processLogin(username, password);
    }

}
