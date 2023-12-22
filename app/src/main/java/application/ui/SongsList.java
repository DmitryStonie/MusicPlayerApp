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
import application.core.PlayerController;
import application.core.SongsController;
import application.model.Song;
import com.example.musicplayer.MainActivity;
import com.example.musicplayer.R;

import java.util.ArrayList;

public class SongsList extends Fragment {
    private SongsController songCont;
    private AppCompatActivity activity;
    private CustomAdapter customAdapter;
    private RecyclerView recyclerView;
    private PlayerController playerController;
    public SongsList(AppCompatActivity activity, PlayerController playerController){
        this.playerController = playerController;
        this.activity = activity;
    }
    public void selectSong(){

    }
    public void showSongs(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInterfaceState){

        return inflater.inflate(R.layout.fragment_songlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        SongsController controller = new SongsController(getContext());
        ArrayList<Song> songs =  controller.getSongs();
        customAdapter = new CustomAdapter(getContext(),songs, playerController);
        recyclerView = activity.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        customAdapter.notifyDataSetChanged();
    }
}
