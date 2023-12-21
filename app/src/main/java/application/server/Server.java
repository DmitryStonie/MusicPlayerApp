package application.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;
import application.model.Song;
import application.model.User;

import java.sql.Connection;

import static application.core.LocalDatabase.*;

public class Server extends SQLiteOpenHelper {
    private Connection database;
    private static final String DATABASE_NAME = "ServerDatabase.db";

    public Server(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    boolean uploadSong(Song song){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SONG_NAME, song.getName());
        cv.put(SONG_LENGTH, song.getDuration());
        cv.put(SONG_ARTIST, song.getArtist());
        cv.put(SONG_ALBUM_NAME, song.getAlbumName());

        long result = db.update(SONG_TABLE_NAME, cv, "_id=?", new String[]{song.getId().toString()});
        return result != -1;
    }
    boolean deleteSong(String songId){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(SONG_TABLE_NAME, "_id=?", new String[]{songId});
        return result != -1;
    }
    boolean sendSong(Song song){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SONG_NAME, song.getName());
        cv.put(SONG_LENGTH, song.getDuration());
        cv.put(SONG_ARTIST, song.getArtist());
        cv.put(SONG_ALBUM_NAME, song.getAlbumName());
        long result = db.insert(SONG_TABLE_NAME,null, cv);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }
    public void getDatabase(){

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
    boolean editUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, user.getName());
        cv.put(USER_PASSWORD, user.getPassword());

        long result = db.update(SONG_TABLE_NAME, cv, "_id=?", new String[]{user.getId().toString()});
        return result != -1;
    }
    public boolean processLogin(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USER_TABLE_NAME + " where " + USER_NAME + " = ? and " + USER_PASSWORD + " = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
