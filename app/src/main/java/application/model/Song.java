package application.model;

import android.media.Image;
import android.net.Uri;

import java.util.ArrayList;

public class Song {
    private Image cover;
    private Integer id;
    private String name;
    private String duration;
    private String artist;
    private String albumName;
    private ArrayList<Byte> data;
    private String path;
    private Uri songUri;
    private Uri albumArtUri;


    public Song() {

    }

    public Song(Integer id, String name, String length, String artist, String albumName, Uri songUri, Uri albumArtUri) {
        this.id = id;
        this.name = name;
        this.duration = length;
        this.artist = artist;
        this.albumName = albumName;
        this.songUri = songUri;
        this.albumArtUri = albumArtUri;
    }

    public String getPath() {
        return path;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Uri getSongUri() {
        return songUri;
    }
}
