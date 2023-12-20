package application.core;

public class Authorize {
    LocalDatabase database;
    public boolean authorize(String username, String password){
        return database.processLogin(username, password);
    }
    public void showLogInWindow(){

    }
    public void showMainMenu(){

    }

}
