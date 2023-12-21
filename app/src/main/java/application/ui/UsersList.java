package application.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import application.core.UsersController;
import com.example.musicplayer.R;

public class UsersList extends Fragment {
    private UsersController userCont;
    public void showUsers(){

    }
    public void deleteUser(){

    }
    public void addUser(){

    }
    public void editUser(){

    }
    public void changeUserStatus(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInterfaceState){
        return inflater.inflate(R.layout.fragment_users, container, false);
    }
}
