package application.core;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.annotation.Nullable;
import application.model.Album;
import application.model.Song;
import application.model.User;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LocalDatabase extends SQLiteOpenHelper {
    private Connection database;
    private Context context;
    private Socket socket;
    public static final String DATABASE_NAME = "LocalDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String SONG_TABLE_NAME = "songs";
    public static final String SONG_ID = "_id";
    public static final String SONG_NAME = "song_name";
    public static final String SONG_LENGTH = "song_length";
    public static final String SONG_ARTIST = "song_artist";
    public static final String SONG_ALBUM_NAME = "song_album_name";
    public static final String USER_TABLE_NAME = "users";
    public static final String USER_ID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD= "user_password";


    private static final Integer DATABASE_REQUEST = 1;
    private static final Integer DATABASE_REQUEST_OK = 2;
    private static final Integer DATABASE_REQUEST_FAIL = 3;
    private static final Integer DATABASE_SONG_REQUEST = 3;

    private static final long BUFFER_SIZE = 4096;



    public LocalDatabase(@Nullable Context context, String ip, int port) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        try {
            socket = new Socket(ip, port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            sendDatabaseRequest(out);
            getDatabase(in);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void sendDatabaseRequest(DataOutputStream out) throws IOException{
        try {
            byte code = DATABASE_REQUEST.byteValue();
            out.writeInt(DATABASE_REQUEST);
        } catch (IOException e) {
            throw e;
        }
    }
    private boolean getDatabase(DataInputStream in) throws IOException{
            int response = in.readInt();
            if (response == DATABASE_REQUEST_FAIL)
                return false;
            else if (response == DATABASE_REQUEST_OK) {
                String filename = getFilename(in);
                //return getFile(in, filename);
            }
        return true;
    }
    private String getFilename(DataInputStream in) throws IOException{
        String filename;
        try {
            int filePathLength = in.readInt();
            byte[] buffer = new byte[filePathLength];
            int bytesRead = in.read(buffer, 0, filePathLength);
            if(bytesRead != filePathLength){
                throw new IOException("Error in reading filename!");
            }
            String[] r = new String(buffer, StandardCharsets.UTF_8).split("\\\\");
            filename = r[r.length-1];
        } catch (IOException e) {
            throw new IOException("Some error occurred!");
        }
        return filename;
    }
    private void getFile(DataInputStream in, String filename) throws IOException{
        new File("uploads").mkdirs();
        int userStatus;
        File file = new File("uploads\\" + filename);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
            long fileLength = in.readLong(), readFileLength = 0;
            byte[] buffer = new byte[4096];
            int bytes = 0;
            long toRead = BUFFER_SIZE;
            while (fileLength - readFileLength > 0) {
                bytes = in.read(buffer, 0, (int)(toRead));
                if(bytes == 0) break;
                readFileLength += bytes;
                if(fileLength - readFileLength < 4096){
                    toRead = fileLength - readFileLength;
                }
                fileOutputStream.write(buffer, 0, bytes);
                fileOutputStream.flush();
            }
        } catch (IOException e){

        }
    }
    public LocalDatabase(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //Cursor cursor = contentResolver.query(uri, null, MediaStore.Audio.Media.DATA+" LIKE?", new String[]{"%.mp3%"}, null);

//        if(cursor != null){
//            while(cursor.moveToNext()){
//                final String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//                final String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//                final String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
//                final String length = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//
//                //addSong(new Song(0, ));
//            }
//        }
    }

    Cursor readAllSongs(){
        String query = "SELECT * FROM " + SONG_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    private void addSong(Song song){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SONG_NAME, song.getName());
        cv.put(SONG_LENGTH, song.getDuration());
        cv.put(SONG_ARTIST, song.getArtist());
        cv.put(SONG_ALBUM_NAME, song.getAlbumName());
        db.insert(SONG_TABLE_NAME,null, cv);
    }
    Cursor readSongsInAlbum(String album){
        String query = "SELECT * FROM " + SONG_TABLE_NAME + " WHERE " + SONG_ALBUM_NAME + " = " + album + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_NAME, user.getName());
        cv.put(USER_PASSWORD, user.getPassword());
        long result = db.insert(USER_TABLE_NAME,null, cv);
        return result != -1;
    }
    boolean deleteUser(String userId){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(USER_TABLE_NAME, "_id=?", new String[]{userId});
        return result != -1;
    }
    boolean updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, user.getName());
        cv.put(USER_PASSWORD, user.getPassword());

        long result = db.update(SONG_TABLE_NAME, cv, "_id=?", new String[]{user.getId().toString()});
        return result != -1;
    }

    Cursor readAllUsers(){
        String query = "SELECT * FROM " + USER_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Album getAlbum(String album_id) {
        return new Album();
    }
    public ArrayList<Album> getAlbums() {
        return new ArrayList<>();
    }

    public ArrayList<Song> getSongsInAlbum() {
        return new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + SONG_TABLE_NAME +
                " (" + SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SONG_NAME + " TEXT, " +
                SONG_LENGTH + " INTEGER, " +
                SONG_ARTIST + " TEXT, " +
                SONG_ALBUM_NAME + " TEXT);";
        db.execSQL(query1);
        String query2 = "CREATE TABLE " + USER_TABLE_NAME +
                " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT, " +
                USER_PASSWORD + " TEXT);";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + SONG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
    public static ArrayList<Song> getSongs(Context context) {
        ArrayList<Song> songs = new ArrayList<>();
        Uri songLibraryUri;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            songLibraryUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else{
            songLibraryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
        };
        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC";

        try(Cursor cursor = context.getContentResolver().query(songLibraryUri, projection, null, null, sortOrder)){
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
            int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);

            while(cursor.moveToNext()){
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                String artist = cursor.getString(artistColumn);
                String album = cursor.getString(albumColumn);
                long albumId = cursor.getLong(albumIdColumn);

                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,id);
                Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);

                name = name.substring(0, name.lastIndexOf("."));

                Song song = new Song(id, name, duration, artist, album, uri, albumArtUri, Song.ON_DEVICE);
                songs.add(song);
            }
        }
        Toast.makeText(context, "Songs: " + songs.size(), Toast.LENGTH_SHORT).show();
        return songs;
    }
    public static ArrayList<Album> getAlbums(Context context) {
        ArrayList<Album> albums = new ArrayList<>();
        ArrayList<Song> songs = LocalDatabase.getSongs(context);
        HashMap<String, Album> albumNames = new HashMap<>();
        songs.forEach(song -> {
            if (!albumNames.containsKey(song.getAlbumName()))
                albumNames.put(song.getAlbumName(), new Album());
            albumNames.get(song.getAlbumName()).addSong(song);
        });
        albumNames.forEach((key, value) -> {
            albums.add(value);
        });
        return albums;
    }
}
