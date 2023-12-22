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
import androidx.recyclerview.widget.RecyclerView;
import application.core.PlayerController;
import application.model.Song;
import com.example.musicplayer.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AlbumSongsAdapter extends RecyclerView.Adapter<AlbumSongsAdapter.MySongsViewHolder> {


    private Context context;
    private ArrayList<Song> songs;
    private PlayerController playerController;


    public AlbumSongsAdapter(Context context, ArrayList<Song> songs, PlayerController playerController){
        this.playerController = playerController;
        this.context = context;
        this.songs = songs;
    }


    @NonNull
    @NotNull
    @Override
    public AlbumSongsAdapter.MySongsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.songs_in_albums, viewGroup, false);
        return new MySongsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AlbumSongsAdapter.MySongsViewHolder mySongsViewHolder, int i) {
        Song song = songs.get(i);
        String songName = song.getName();
        if (songName.length() > 20) songName = songName.substring(0,20)+ "...";
        mySongsViewHolder.songName.setText(songName);
        mySongsViewHolder.authorName.setText(String.valueOf(song.getArtist()));
        mySongsViewHolder.authorName.setText(String.valueOf(song.getDuration()));
        mySongsViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerController.playSongs(songs,song);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class MySongsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        CardView cardView;
        TextView songName, authorName, len;
        public MySongsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name_album);
            authorName = itemView.findViewById(R.id.song_author_album);
            len = itemView.findViewById(R.id.song_len_album);
            linearLayout = itemView.findViewById(R.id.mainLayout1);
            cardView = itemView.findViewById(R.id.cardview1);
        }
    }
}
