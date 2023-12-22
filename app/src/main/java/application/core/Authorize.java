package application.core;

import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.Socket;

public class Authorize extends AsyncTask<String, Integer, Long> {
    public static final Long AUTH_SUCCESS = Long.valueOf(1);
    public static final Long AUTH_FAIL = Long.valueOf(2);
    @Override
    protected Long doInBackground(String... strings) {
        if(strings.length < 3)
            return AUTH_FAIL;
        String username = strings[0];
        String password = strings[1];
        String ip = strings[2];
        Socket client = null;
        try {
            client = new Socket(ip, SERVER_PORT);
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            out.writeInt(DATABASE_AUTH_REQUEST);
            out.writeInt(username.length());
            out.writeInt(password.length());
            out.writeBytes(username);
            out.writeBytes(password);
            int answer = in.readInt();
            client.close();
            in.close();
            out.close();
            if (answer == DATABASE_AUTH_REQUEST_OK)
                return AUTH_SUCCESS;
            return AUTH_FAIL;
        } catch (IOException e) {
            return AUTH_FAIL;
        }
    }

    private static final int SERVER_PORT = 7777;
    public static final int DATABASE_AUTH_REQUEST = 1;
    public static final int DATABASE_AUTH_REQUEST_OK = 2;

}
