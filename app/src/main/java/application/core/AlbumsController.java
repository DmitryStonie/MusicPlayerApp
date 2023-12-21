package application.core;

import android.content.Context;
import application.model.Album;
import application.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AlbumsController {
    private ArrayList<Album> albums;
    Context context;

    public void playAlbum() {

    }
    public AlbumsController(Context context) {
        this.context = context;
        //database = new LocalDatabase(context);
        albums = new ArrayList<>();
        //updateSongs();
    }
    public ArrayList<Album> getAlbums() {
        albums.clear();
        albums = LocalDatabase.getAlbums(context);
        return albums;
    }
}
