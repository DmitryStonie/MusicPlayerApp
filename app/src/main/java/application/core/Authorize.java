package application.core;

import java.io.*;
import java.net.Socket;

public class Authorize {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    private static final int SERVER_PORT = 7777;
    public static final int DATABASE_AUTH_REQUEST = 1;
    public static final int DATABASE_AUTH_REQUEST_OK = 2;
    public Authorize(String ip) {
        try {
            Socket client = new Socket(ip, Integer.valueOf(SERVER_PORT));
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authorize(String username, String password) {
        try {
            out.writeInt(DATABASE_AUTH_REQUEST);
            out.writeInt(username.length());
            out.writeInt(password.length());
            out.writeBytes(username);
            out.writeBytes(password);
            int answer = in.readInt();
            if (answer == DATABASE_AUTH_REQUEST_OK)
                return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
