package application.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import application.core.AlbumsController;
import application.core.PlayerController;
import application.core.SongsController;
import com.example.musicplayer.MainActivity;
import com.example.musicplayer.R;
import com.google.android.material.navigation.NavigationView;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private AlbumsList albumsList;
    private SongsList songsList;
    private SongsManager songsManager;
    private UsersList userList;
    private MusicPlayer musicPlayer;
    private AppCompatActivity activity;
    private DrawerLayout drawerLayout;

    public void openSideMenu(){

    }
    public void openSongs(){

    }
    public void openAlbums(){

    }
    public void manageUsers(){

    }
    public void manageSongs(){

    }
    public void logOut(){

    }
    public void create(AppCompatActivity activity, Bundle savedInstanceState){
        this.activity = activity;
        activity.setContentView(R.layout.activity_main);
        SongsController songsController = new SongsController(activity.getApplicationContext());
        AlbumsController albumsController = new AlbumsController(activity.getApplicationContext());
        PlayerController playerController = new PlayerController(activity.getApplicationContext(), songsController,albumsController);
        songsList = new SongsList(activity,playerController);
        songsManager = new SongsManager();
        userList = new UsersList();
        albumsList = new AlbumsList(playerController,activity);



        Toolbar toolbar = activity.findViewById(R.id.toolbar); //Ignore red line errors
        activity.setSupportActionBar(toolbar);
        drawerLayout = activity.findViewById(R.id.drawer_layout);
        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, songsList).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_songlist) {
   //         songsList = new SongsList(activity);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, songsList).commit();
        }
        else if (item.getItemId() == R.id.nav_albumsongs)
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, albumsList).commit();
        else if (item.getItemId() == R.id.nav_addsong)
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, songsManager).commit();
        else if (item.getItemId() == R.id.nav_users)
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, userList).commit();
        else if (item.getItemId() == R.id.nav_exit) {
 //           activity.finish();
            Toast.makeText(activity, "Logout!", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
/*    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
}
