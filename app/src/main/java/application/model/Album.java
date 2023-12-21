package application.model;

import android.media.Image;
import android.net.Uri;

import java.util.ArrayList;

public class Album {
    private Uri albumArtUri;
    private String name;
    private int duration;
    private String artist;
    private int numOfSongs;
    private ArrayList<Song> songs;

    public Album() {
        songs = new ArrayList<>();
    }

    public Uri getAlbumArtUri() {
        return albumArtUri;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
    public void addSong(Song song){
        songs.add(song);
        if(numOfSongs == 0){
            albumArtUri = song.getAlbumArtUri();
            name = song.getAlbumName();
            artist = song.getArtist();
        }
        numOfSongs++;
        duration+=song.getRawDuration();
    }
}
