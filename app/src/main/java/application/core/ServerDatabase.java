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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerDatabase extends SQLiteOpenHelper {
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


    public ServerDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public ServerDatabase(@Nullable Context context, ContentResolver contentResolver, Uri uri){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Cursor cursor = contentResolver.query(uri, null, MediaStore.Audio.Media.DATA+" LIKE?", new String[]{"%.mp3%"}, null);

        if(cursor != null){
            while(cursor.moveToNext()){
                final String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                final String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                final String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                final String length = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                //addSong(new Song(0, ));
            }
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
    public boolean processLogin(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USER_TABLE_NAME + " where " + USER_NAME + " = ? and " + USER_PASSWORD + " = ?", new String[]{username, password});
        return cursor.getCount() > 0;
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

                Song song = new Song(id, name, duration, artist, album, uri, albumArtUri, Song.ON_SERVER);
                songs.add(song);
            }
        }
        Toast.makeText(context, "Songs: " + songs.size(), Toast.LENGTH_SHORT).show();
        return songs;
    }
    public static ArrayList<Album> getAlbums(Context context) {
        ArrayList<Album> albums = new ArrayList<>();
        ArrayList<Song> songs = ServerDatabase.getSongs(context);
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
