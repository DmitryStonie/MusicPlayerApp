package application.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;
import application.model.Album;
import application.model.Song;
import application.model.User;

import java.sql.*;
import java.util.ArrayList;

public class LocalDatabase extends SQLiteOpenHelper {
    private Connection database;

    private Context context;
    private static final String DATABASE_NAME = "LocalDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SONG_TABLE_NAME = "songs";
    private static final String SONG_ID = "_id";
    private static final String SONG_NAME = "song_name";
    private static final String SONG_LENGTH = "song_length";
    private static final String SONG_ARTIST = "song_artist";
    private static final String SONG_ALBUM_NAME = "song_album_name";
    private static final String USER_TABLE_NAME = "users";
    private static final String USER_ID = "_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_PASSWORD= "user_password";


    public LocalDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    void addSong(String name, Integer length, String artist, String albumName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SONG_NAME, name);
        cv.put(SONG_LENGTH, length);
        cv.put(SONG_ARTIST, artist);
        cv.put(SONG_ALBUM_NAME, albumName);
        long result = db.insert(SONG_TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    void addUser(String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_NAME, name);
        cv.put(USER_PASSWORD, password);
        long result = db.insert(USER_TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
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
    Cursor readSongsInAlbum(String album){
        String query = "SELECT * FROM " + SONG_TABLE_NAME + " WHERE " + SONG_ALBUM_NAME + " = " + album + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllusers(){
        String query = "SELECT * FROM " + USER_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    void updateSong(Song song){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SONG_NAME, song.getName());
        cv.put(SONG_LENGTH, song.getLength());
        cv.put(SONG_ARTIST, song.getArtist());
        cv.put(SONG_ALBUM_NAME, song.getAlbumName());

        long result = db.update(SONG_TABLE_NAME, cv, "_id=?", new String[]{song.getId().toString()});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, name);
        cv.put(USER_PASSWORD, password);

        long result = db.update(SONG_TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneSong(String songId){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(SONG_TABLE_NAME, "_id=?", new String[]{songId});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneUser(String userId){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(USER_TABLE_NAME, "_id=?", new String[]{userId});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public Album getAlbum(String album_id) {

    }
    public ArrayList<Album> getAlbums() {

    }

    public ArrayList<Song> getSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        Cursor cursor = readAllSongs();
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                songs.add(new Song(Integer.getInteger(cursor.getString(0)), cursor.getString(1), Integer.getInteger(cursor.getString(2)), cursor.getString(3),cursor.getString(4), cursor.getString(5)));
            }
        }
        return songs;
    }

    public ArrayList<Song> getSongsInAlbum() {

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
}
