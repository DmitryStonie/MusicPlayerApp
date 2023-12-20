package application.model;

import android.media.Image;

import java.util.ArrayList;

public class Song {
    private Image cover;
    private Integer id;
    private String name;
    private Integer length;
    private String artist;
    private String albumName;
    private ArrayList<Byte> data;
    private String path;

    public void getSong(){

    }
    public Song(){

    }
    public Song(Integer id, String name, Integer length, String artist, String albumName, String path){
        this.id = id;
        this.name = name;
        this.length = length;
        this.artist = artist;
        this.albumName = albumName;
        this.path = path;
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

    public Integer getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
