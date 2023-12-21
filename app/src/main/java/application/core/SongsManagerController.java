package application.core;

import android.content.Context;
import android.database.Cursor;
import application.model.Song;
import application.model.User;

import java.util.ArrayList;

public class SongsManagerController {
    private ArrayList<Song> songs;
    private LocalDatabase database;
    public void deleteSong(String songId){
        database.deleteOneSong(songId);
    }
    public void uploadSong(){
        Song song = new Song(); //idk what will be here
        database.addSong(song);
    }
    public SongsManagerController(Context context){
        database = new LocalDatabase(context);
        songs = new ArrayList<>();
        updateSongs();
    }
    private void updateSongs(){
        songs.clear();
        Cursor cursor = database.readAllSongs();
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                songs.add(new Song(Integer.getInteger(cursor.getString(0)), cursor.getString(1), Integer.getInteger(cursor.getString(2)), cursor.getString(3),cursor.getString(4), cursor.getString(5)));
            }
        }
    }
    public ArrayList<Song> getSongs(){
        return songs;
    }
}
