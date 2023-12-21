package application.core;

import android.content.Context;
import android.media.MediaPlayer;
import application.model.Album;
import application.model.Song;

import java.util.ArrayList;

public class PlayerController {
    private AlbumsController albCont;
    private SongsController songCont;
    private final Context context;
    private ArrayList<Song> songs;
    private int currentSongPos = 0;
    private Song currentSong;
    MediaPlayer mediaPlayer = new MediaPlayer();
    public void playAlbum(Album album){

    }
    public PlayerController(Context context){
        this.context = context;
        this.songCont = new SongsController(context);
        this.songs = songCont.getSongs();
        this.currentSong = songs.get(currentSongPos);
    }

    public Song playSong(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(context, currentSong.getSongUri());
        mediaPlayer.start();
        return currentSong;
    }
    public void pauseSong(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
    public void continueSong(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }
    public Song nextSong(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            currentSongPos = (currentSongPos + 1) % songs.size();
            currentSong = songs.get(currentSongPos);
            mediaPlayer = MediaPlayer.create(context, currentSong.getSongUri());
            mediaPlayer.start();
        }
        return currentSong;
    }
    public Song previousSong(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            currentSongPos = (currentSongPos - 1 + songs.size()) % songs.size();
            currentSong = songs.get(currentSongPos);
            mediaPlayer = MediaPlayer.create(context, currentSong.getSongUri());
            mediaPlayer.start();
        }
        return currentSong;
    }
}
