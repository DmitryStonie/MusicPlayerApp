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
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SongsList()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_songlist)
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SongsList()).commit();
        else if (item.getItemId() == R.id.nav_albumsongs)
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AlbumsList()).commit();
        else if (item.getItemId() == R.id.nav_addsong)
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SongsManager()).commit();
        else if (item.getItemId() == R.id.nav_users)
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UsersList()).commit();
        else if (item.getItemId() == R.id.nav_exit)
            Toast.makeText(activity, "Logout!", Toast.LENGTH_SHORT).show();
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
