package application.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicplayer.R;
import org.jetbrains.annotations.NotNull;

public class AlbumSongsAdapter extends RecyclerView.Adapter<AlbumSongsAdapter.MySongsViewHolder> {

    @NonNull
    @NotNull
    @Override
    public AlbumSongsAdapter.MySongsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AlbumSongsAdapter.MySongsViewHolder mySongsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
