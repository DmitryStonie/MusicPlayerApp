package application.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import application.model.Song;
import com.example.musicplayer.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Song> songs;
    public CustomAdapter(Context context, ArrayList<Song> songs){
        this.context = context;
        this.songs = songs;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder myViewHolder, int i) {
        Song song = songs.get(i);
        myViewHolder.songImage.setText(String.valueOf(song.getId()));
        myViewHolder.songName.setText(String.valueOf(song.getName()));
        myViewHolder.songAuthor.setText(String.valueOf(song.getArtist()));
        myViewHolder.songLen.setText(String.valueOf(song.getDuration()));
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView songName, songAuthor, songLen, songImage;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name_txt);
            songAuthor = itemView.findViewById(R.id.song_author_txt);
            songImage = itemView.findViewById(R.id.song_id_txt);
            songLen = itemView.findViewById(R.id.song_len_txt);
        }
    }
}
