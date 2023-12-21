package application.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import application.core.AlbumsController;
import com.example.musicplayer.R;

public class AlbumsList extends Fragment {
    private AlbumsController albCont;
    public void selectAlbum(){

    }
    public void showAlbum(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInterfaceState){
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }
}
