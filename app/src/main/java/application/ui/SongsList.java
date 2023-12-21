package application.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import application.core.SongsController;
import com.example.musicplayer.R;

public class SongsList extends Fragment {
    private SongsController songCont;
    public void selectSong(){

    }
    public void showSongs(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInterfaceState){
        return inflater.inflate(R.layout.fragment_songlist, container, false);
    }
}
