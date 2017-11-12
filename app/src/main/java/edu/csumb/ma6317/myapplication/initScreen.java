package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class initScreen extends AppCompatActivity implements View.OnClickListener {

    // FirebaseUI sign-in code
    public static final int RC_SIGN_IN = 1;

    // Firebase instance variables
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Button signInButton;
    private Button contGuestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_screen);

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance();

        //get references to the widgets in activity_init_screen.xml
        signInButton = (Button) findViewById(R.id.signInButt);
        contGuestButton = (Button) findViewById(R.id.guestButt);

        //set the click listeners for the buttons
        signInButton.setOnClickListener(this);
        contGuestButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.signInButt) {
            if (auth.getCurrentUser() != null) {
                // User is signed in
            } else {
                // User is signed out
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(
                                        Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                .build(),
                        RC_SIGN_IN);
            }

            Intent intent = new Intent(this, signUp.class);
            startActivity(intent);
        } else if (v.getId() == R.id.guestButt) {
            Intent intent = new Intent(this, profCreate.class);
            startActivity(intent);
        } else {
            return;
        }
    }
}
//        final Button logoutButton = findViewById(R.id.logoutButt);
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Code here executes on main thread after user presses button
//                signOut();
//            }
//        });

//    public void signOut() {
//        AuthUI.getInstance().signOut(this);
//    }
