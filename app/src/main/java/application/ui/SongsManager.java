package application.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import application.core.SongsManagerController;
import com.example.musicplayer.R;

public class SongsManager extends Fragment {
    private SongsManagerController songsManagerController;
    public void deleteSong(){

    }
    public void uploadSong(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInterfaceState){
        return inflater.inflate(R.layout.fragment_addsong, container, false);
    }
}
