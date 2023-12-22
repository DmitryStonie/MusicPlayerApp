package application.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import application.core.AlbumsController;
import application.core.PlayerController;
import application.core.SongsController;
import application.model.Album;
import application.model.Song;
import com.example.musicplayer.R;

import java.util.ArrayList;

public class AlbumsList extends Fragment {
    private AlbumsController albCont;
    private AppCompatActivity activity;
    private PlayerController playerController;
    private AlbumsCustomAdapter customAdapter;
    private RecyclerView recyclerView;

    public AlbumsList(PlayerController playerController, AppCompatActivity activity){
        this.playerController = playerController;
        this.activity = activity;
    }
    public void selectAlbum(){

    }
    public void showAlbum(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInterfaceState){
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        AlbumsController controller = new AlbumsController(getContext());
        ArrayList<Album> albums = controller.getAlbums();
      //  MusicPlayer musicPlayer = new MusicPlayer(playerController);
        customAdapter = new AlbumsCustomAdapter(getContext(),albums, playerController);
        recyclerView = activity.findViewById(R.id.recyclerview1);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        customAdapter.notifyDataSetChanged();
    }
}
