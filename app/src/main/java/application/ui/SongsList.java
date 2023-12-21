package application.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import application.core.SongsController;
import application.model.Song;
import com.example.musicplayer.MainActivity;
import com.example.musicplayer.R;

import java.util.ArrayList;

public class SongsList extends Fragment {
    private SongsController songCont;
    private AppCompatActivity activity;
    public SongsList(AppCompatActivity activity){
        this.activity = activity;
    }
    public void selectSong(){

    }
    public void showSongs(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInterfaceState){
        SongsController controller = new SongsController(activity.getApplicationContext());
        ArrayList<Song> songs =  controller.getSongs();
        return inflater.inflate(R.layout.fragment_songlist, container, false);
    }
}
