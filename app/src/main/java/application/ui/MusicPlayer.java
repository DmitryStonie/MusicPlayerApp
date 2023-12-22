package application.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import application.core.PlayerController;
import application.model.Song;
import com.example.musicplayer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MusicPlayer extends Fragment {

    ImageView nextBtn, album;
    TextView artist, songName;
    FloatingActionButton playBtn;
    View view;

    private PlayerController playerController;

    public MusicPlayer(PlayerController playerController){
        this.playerController = playerController;
    }
    public void playSong(){

    }
    public void stopSong(){

    }
    public void nextSong(){

    }
    public void previousSong(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInterfaceState){
        view = inflater.inflate(R.layout.fragment_player, container, false);
        artist = view.findViewById(R.id.song_artist);
        songName = view.findViewById(R.id.song_name);
        album = view.findViewById(R.id.bottom_album);
        nextBtn = view.findViewById(R.id.bottom_next);
        playBtn = view.findViewById(R.id.bottom_play);
       // Song song = playerController.
       // artist
        return inflater.inflate(R.layout.fragment_player, container, false);
    }
}
