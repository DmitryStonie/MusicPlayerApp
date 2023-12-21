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


    public LocalDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

    }
    public ArrayList<Album> getAlbums() {

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
