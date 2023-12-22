package application.ui;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import application.core.AlbumsController;
import application.core.PlayerController;
import application.core.SongsController;
import application.model.Album;
import application.model.Song;
import com.example.musicplayer.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AlbumsCustomAdapter extends RecyclerView.Adapter<AlbumsCustomAdapter.MyAlbumViewHolder>{
    private Context context;
    private ArrayList<Album> albums;
    private PlayerController playerController;

    public AlbumsCustomAdapter(Context context, ArrayList<Album> albums, PlayerController playerController){
        this.playerController = playerController;
        this.context = context;
        this.albums = albums;
    }
    @NonNull
    @NotNull
    @Override
    public MyAlbumViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alboms, viewGroup, false);
        return new MyAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyAlbumViewHolder viewHolder, int i) {
        Album album = albums.get(i);

        Uri albumCover = album.getAlbumArtUri();
        viewHolder.albumName.setText(album.getName());
        if (albumCover != null) {
            viewHolder.albumImage.setImageURI(albumCover);
            if (viewHolder.albumImage.getDrawable() == null){
                viewHolder.albumImage.setImageResource(R.drawable.default_albumart);
            }
        }

     /*   AlbumsController controller = new AlbumsController(context);
        ArrayList<Song> songs =  controller.;
        MusicPlayer musicPlayer = new MusicPlayer(playerController);
        customAdapter = new CustomAdapter(context,songs, playerController);
        recyclerView = activity.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        customAdapter.notifyDataSetChanged();
 /*       viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerController.playSongs(songs,song);
                MusicPlayer musicPlayer = new MusicPlayer();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class MyAlbumViewHolder extends RecyclerView.ViewHolder{
    ImageView albumImage;
    LinearLayout linearLayout;
    CardView cardView;
    TextView albumName;
    public MyAlbumViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        albumName = itemView.findViewById(R.id.album_name);
        albumImage = itemView.findViewById(R.id.album_cover1);
        linearLayout = itemView.findViewById(R.id.mainLayout2);
        cardView = itemView.findViewById(R.id.cardview2);
    }
    }
}
