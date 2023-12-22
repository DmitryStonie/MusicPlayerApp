package application.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import application.core.Authorize;
import application.core.Client;
import com.example.musicplayer.MainActivity;
import com.example.musicplayer.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.ExecutionException;

import static application.core.Authorize.AUTH_FAIL;
import static application.core.Authorize.AUTH_SUCCESS;

public class Login {
    private String ip;
    private String username;
    private String password;
    private Button loginButton, skipButton;
    private AppCompatActivity activity;
    private Bundle savedInstanceState;

    public void logIn(){
        Long result;
        Authorize auth = new Authorize();
        auth.execute(username, password, ip);
        try {
            result = auth.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(result.equals(AUTH_SUCCESS))
            Toast.makeText(activity.getApplicationContext(), "LogIn successful", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(activity.getApplicationContext(), "LogIn failed", Toast.LENGTH_LONG).show();
            return;
        }
        MainMenu mainMenu = new MainMenu();
        mainMenu.create(activity, savedInstanceState);
    }
    public void listenMusic(){
        MainMenu mainMenu = new MainMenu();
        mainMenu.create(activity, savedInstanceState);
    }

    public void create(AppCompatActivity activity, Bundle savedInstanceState){
        this.activity = activity;
        this.savedInstanceState = savedInstanceState;
        activity.setContentView(R.layout.login);
        addListenerOnButtons();

    }

    private void addListenerOnButtons(){
        loginButton = activity.findViewById(R.id.Login);
        skipButton = activity.findViewById(R.id.Skip);
        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText name = activity.findViewById(R.id.loginText);
                        EditText pass = activity.findViewById(R.id.passwordText);
                        EditText address = activity.findViewById(R.id.Ip);
                        username = name.getText().toString();
                        password = pass.getText().toString();
                        ip = address.getText().toString();
                        if (username.matches(""))
                            Snackbar.make(v, "Enter your login", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        else if (password.matches(""))
                            Snackbar.make(v, "Enter your password", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        else if (ip.matches(""))
                            Snackbar.make(v, "Enter server ip-address", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        else logIn();
                    }
                }
        );
        skipButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listenMusic();
                    }
                }
        );

    }

}
