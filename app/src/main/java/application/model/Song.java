package application.model;

import android.annotation.SuppressLint;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import java.io.IOException;
import java.util.ArrayList;

public class Song {
    private Image cover;
    private long id;
    private String name;
    private int duration;
    private String artist;
    private String albumName;
    private ArrayList<Byte> data;
    private Uri songUri;
    private Uri albumArtUri;


    public Song() {

    }

    public Song(long id, String name, int duration, String artist, String albumName, Uri songUri, Uri albumArtUri) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.artist = artist;
        this.albumName = albumName;
        this.songUri = songUri;
        this.albumArtUri = albumArtUri;
    }


    public String getAlbumName() {
        return albumName;
    }

    public String getArtist() {
        return artist;
    }

    @SuppressLint("DefaultLocale")
    public String getDuration() {
        int hrs = duration / (1000*60*60);
        int min = (duration%(1000*60*60))/(1000*60);
        int secs = ((duration%(1000*60*60))%(1000*60))/1000;

        if(hrs < 1)
            return String.format("%02d:%02d", min, secs);
        else
            return String.format("%1d:%02d:%02d", hrs, min, secs);
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public Uri getSongUri() {
        return songUri;
    }

    public Uri getAlbumArtUri() {
        return albumArtUri;
    }
    private byte[] getImage() {
        byte[] art;
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(this.albumArtUri.toString());
            art = retriever.getEmbeddedPicture();
            retriever.release();
        } catch (IOException e) {
            return null;
        }
        return art;
    }
}
