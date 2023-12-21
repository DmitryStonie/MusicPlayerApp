package application.core;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import application.model.Song;

import java.util.ArrayList;

public class SongsController {
    private ArrayList<Song> songs;
    LocalDatabase database;

    Song playSong(Song song) {
        //get song from file
    }

    public SongsController(Context context) {
        database = new LocalDatabase(context);
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

    public ArrayList<Song> getSongs(ContentResolver contentResolver, Uri uri) {
        songs.clear();
        Cursor cursor = contentResolver.query(uri, null, MediaStore.Audio.Media.DATA + " LIKE?", new String[]{"%.mp3%"}, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                final String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                final String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                final String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                final String duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                long cursorId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                Uri musicFileUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursorId);
                Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), cursorId)
                songs.add(new Song((int)cursorId, name, duration, artist, album, musicFileUri, albumArtUri));
            }
            cursor.close();
        }
        return songs;
    }
}
