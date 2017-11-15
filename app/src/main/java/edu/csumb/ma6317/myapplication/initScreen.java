package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class initScreen extends AppCompatActivity implements View.OnClickListener {

    // FirebaseUI sign-in code
    public static final int RC_SIGN_IN = 1;

    // Firebase instance variables
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;


    // GeoFire
    //private DatabaseReference mDatabaseRef;
    //private GeoFire mGeoFire;

    // Button variables
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

        //mDatabaseRef = FirebaseDatabase.getInstance().getReference("path/geofire");
        //mGeoFire = new GeoFire(mDatabaseRef);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.signInButt) {
            if (auth.getCurrentUser() != null) {
                // User is signed in
                Intent intent = new Intent(this, profCreate.class);
                startActivity(intent);

                // test to send location to firebase
               // mGeoFire.setLocation("firebase-hq", new GeoLocation(37.7853889, -122.4056973));

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
        } else if (v.getId() == R.id.guestButt) {
            signOut();
            Intent intent = new Intent(this, profCreate.class);
            startActivity(intent);
        } else {
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, profCreate.class);
                startActivity(intent);
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, return to previous activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void signOut() {
        AuthUI.getInstance().signOut(this);
    }
}
