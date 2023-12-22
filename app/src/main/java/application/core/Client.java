package application.core;

import android.util.Log;
import application.model.Song;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static application.core.LocalDatabase.DATABASE_NAME;

public class Client {
    private static final int BUFFER_SIZE = 4096;
    public static final int DATABASE_AUTH_REQUEST = 1;
    public static final int DATABASE_AUTH_REQUEST_OK = 2;
    public static final int DATABASE_AUTH_REQUEST_FAIL = 3;
    public static final int DATABASE_SONG_REQUEST = 4;
    public static final int DATABASE_SONG_REQUEST_OK = 4;
    public static final int DATABASE_SONG_REQUEST_FAIL = 4;
    public static final int DATABASE_DUMP_REQUEST = 5;
    public static final int DATABASE_DUMP_REQUEST_OK = 6;
    public static final int DATABASE_DUMP_REQUEST_FAIL = 7;
    private static final int MAX_FILENAME_LENGTH = 4096;
    private static final long TERABYTE = 1099511627776L;
    public static final int DEFAULT = -1;
    public static final int RECEIVING = 0;
    public static final int RECEIVED = 1;
    public static final int FAILED = 2;
    private String filePath;
    private String serverName;
    private String serverPort;
    Socket socket;

    public Client(String ip, String port) {
        this.serverName = ip;
        this.serverPort = port;
    }


    public static boolean getDatabase(DataInputStream in, DataOutputStream out, String login, String password) throws IOException {
        try {
            out.writeInt(DATABASE_DUMP_REQUEST);
            out.writeInt(login.length());
            out.writeInt(password.length());
            out.writeBytes(login);
            out.writeBytes(password);
            int answer = in.readInt();
            if (answer == DATABASE_AUTH_REQUEST_FAIL)
                return false;
            File file = new File(DATABASE_NAME);
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                long fileLength = in.readLong(), readFileLength = 0;
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytes = 0;
                long toRead = BUFFER_SIZE;
                while (fileLength - readFileLength > 0) {
                    bytes = in.read(buffer, 0, (int) (toRead));
                    if (bytes == 0) break;
                    readFileLength += bytes;
                    if (fileLength - readFileLength < 4096) {
                        toRead = fileLength - readFileLength;
                    }
                    fileOutputStream.write(buffer, 0, bytes);
                    fileOutputStream.flush();
                }
            } catch (IOException e) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean getSong(DataInputStream in, DataOutputStream out, Song song, String login, String password) throws IOException {
        try {
            out.writeInt(DATABASE_SONG_REQUEST);
            if(!processLogin(in, out, login, password))
                return false;
            out.writeInt(song.getName().length());
            out.writeBytes(song.getName());
            int answer = in.readInt();
            if (answer == DATABASE_SONG_REQUEST_FAIL)
                return false;
            File file = new File("songs/" + song.getName());
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                long fileLength = in.readLong(), readFileLength = 0;
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytes = 0;
                long toRead = BUFFER_SIZE;
                while (fileLength - readFileLength > 0) {
                    bytes = in.read(buffer, 0, (int) (toRead));
                    if (bytes == 0) break;
                    readFileLength += bytes;
                    if (fileLength - readFileLength < 4096) {
                        toRead = fileLength - readFileLength;
                    }
                    fileOutputStream.write(buffer, 0, bytes);
                    fileOutputStream.flush();
                }
            } catch (IOException e) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean processLogin(DataInputStream in, DataOutputStream out, String login, String password) {
        try {
            out.writeInt(DATABASE_AUTH_REQUEST);
            out.writeInt(login.length());
            out.writeInt(password.length());
            out.writeBytes(login);
            out.writeBytes(password);
            int answer = in.readInt();
            if (answer == DATABASE_AUTH_REQUEST_FAIL)
                return false;
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}