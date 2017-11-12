package edu.csumb.ma6317.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class initScreen extends AppCompatActivity {

    // FirebaseUI sign-in code
    public static final int RC_SIGN_IN = 1;

    // Firebase instance variables
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_screen);

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance();

        // Handles user sign-in
        final Button signInButton = findViewById(R.id.signInButt);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
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
            }
        });

        final Button logoutButton = findViewById(R.id.logoutButt);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                signOut();
            }
        });


    }

    public void signOut() {
        AuthUI.getInstance().signOut(this);
    }
}
