package application.core;

import android.content.Context;
import android.database.Cursor;
import application.model.User;

import java.util.ArrayList;

public class UsersController {
    private final ArrayList<User> users;
//    public boolean deleteUser(Integer id){
//        return database.deleteUser(id.toString());
//    }
//    public boolean editUser(Integer id, String username, String password, String status){
//        return database.updateUser(new User(id, username, password, Integer.getInteger(status)));
//    }
//    public boolean addUser(String username, String password, String status){//idk about status
//        return database.addUser(new User(username, password, Integer.getInteger(status)));
//    }
    public UsersController(Context context){
        users = new ArrayList<>();
    }
    public ArrayList<User> getUsers(){
        return users;
    }

}
