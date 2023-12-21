package application.core;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.annotation.Nullable;
import application.model.Song;

import java.util.ArrayList;

public class SongsController {
    private ArrayList<Song> songs;
    LocalDatabase database;
    Context context;

    void playSong(Song song) {

    }

    public SongsController(Context context) {
        this.context = context;
        songs = new ArrayList<>();
    }
    public ArrayList<Song> getSongs() {
        songs.clear();
        songs = LocalDatabase.getSongs(context);
        return songs;
    }

}
