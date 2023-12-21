package application.core;

import android.content.Context;
import android.database.Cursor;
import application.model.User;

import java.util.ArrayList;

public class UsersController {
    private final ArrayList<User> users;
    private final LocalDatabase database;
    public boolean deleteUser(Integer id){
        return database.deleteUser(id.toString());
    }
    public boolean editUser(Integer id, String username, String password, String status){
        return database.updateUser(new User(id, username, password, Integer.getInteger(status)));
    }
    public boolean addUser(String username, String password, String status){//idk about status
        return database.addUser(new User(username, password, Integer.getInteger(status)));
    }
    public UsersController(Context context){
        database = new LocalDatabase(context);
        users = new ArrayList<>();
        updateUsers();
    }
    public ArrayList<User> getUsers(){
        updateUsers();
        return users;
    }
    private void updateUsers(){
        users.clear();
        Cursor cursor = database.readAllUsers();
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                users.add(new User(Integer.getInteger(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.getInteger(cursor.getString(3))));
            }
        }
    }
}
