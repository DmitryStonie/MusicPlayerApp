package application.core;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.annotation.Nullable;
import application.model.Song;

import java.util.ArrayList;

public class SongsController {
    private ArrayList<Song> songs;
    LocalDatabase database;
    Context context;

    Song playSong(Song song) {
        return new Song();
        //get song from file
    }

    public SongsController(Context context) {
        this.context = context;
        //database = new LocalDatabase(context);
        songs = new ArrayList<>();
        //updateSongs();
    }

    private void updateSongs() {
//        songs.clear();
//        Cursor cursor = database.readAllSongs();
//        if (cursor.getCount() != 0) {
//            while (cursor.moveToNext()) {
//                songs.add(new Song(Integer.getInteger(cursor.getString(0)), cursor.getString(1), Integer.getInteger(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
//            }
//        }
    }

    public ArrayList<Song> getSongs() {
        songs.clear();
        Uri songLibraryUri;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            songLibraryUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else{
            songLibraryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
        };
        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC";

        try(Cursor cursor = context.getContentResolver().query(songLibraryUri, projection, null, null, sortOrder)){
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
            int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);

            while(cursor.moveToNext()){
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                String artist = cursor.getString(artistColumn);
                String album = cursor.getString(albumColumn);
                long albumId = cursor.getLong(albumIdColumn);

                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,id);
                Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);

                name = name.substring(0, name.lastIndexOf("."));

                Song song = new Song(id, name, duration, artist, album, uri, albumArtUri);
                songs.add(song);
            }
        }
        Toast.makeText(context, "Songs: " + songs.size(), Toast.LENGTH_SHORT).show();
        return songs;
    }
}


//        Cursor cursor = contentResolver.query(uri, null, MediaStore.Audio.Media.DATA + " LIKE?", new String[]{"%.mp3%"}, null);
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                final String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//                final String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//                final String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
//                final String duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//
//                long cursorId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
//                Uri musicFileUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursorId);
//                Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), cursorId);
//                songs.add(new Song((int)cursorId, name, duration, artist, album, musicFileUri, albumArtUri));
//            }
//            cursor.close();
//        }
//        return songs;