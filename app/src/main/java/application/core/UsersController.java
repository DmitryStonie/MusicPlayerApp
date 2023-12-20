package application.core;

import android.content.Context;
import android.database.Cursor;
import application.model.User;

import java.util.ArrayList;

public class UsersController {
    private final ArrayList<User> users;
    private final LocalDatabase database;
    public boolean deleteUser(Integer id){
        boolean result = database.deleteOneUser(id.toString());
        if(result)
            updateUsers();
        return result;
    }
    public boolean editUser(Integer id, String username, String password, String status){
        boolean result = database.updateUser(new User(id, username, password, Integer.getInteger(status)));
        if(result)
            updateUsers();
        return result;
    }
    public boolean addUser(String username, String password, String status){//idk about status
        boolean result = database.addUser(new User(username, password, Integer.getInteger(status)));
        if(result)
            updateUsers();
        return result;
    }
    public UsersController(Context context){
        database = new LocalDatabase(context);
        users = new ArrayList<>();
        updateUsers();
    }
    public ArrayList<User> getUsers(){
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
