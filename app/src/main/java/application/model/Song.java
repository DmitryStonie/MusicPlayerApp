package application.model;

import android.media.Image;
import android.net.Uri;

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

    public int getDuration() {
        return duration;
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
}
