package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class initScreen extends AppCompatActivity implements View.OnClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    // FirebaseUI sign-in code
    public static final int RC_SIGN_IN = 1;

    public static boolean userExists;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener authStateListener;

    // Button variables
    private Button signInButton;
    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_screen);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();

        // Get references to the widgets in activity_init_screen.xml
        signInButton = (Button) findViewById(R.id.signInButt);
        signOutButton = (Button) findViewById(R.id.signOutButt);

        // Set the click listeners for the buttons
        signInButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);

        // Check if user exists in onCreate()
        // If done in onClick(), there's not enough time to process result before activity change
        if (mAuth.getCurrentUser() != null) {
            // User is signed in, check if they exist in database
            mUser = mAuth.getCurrentUser();
            checkUserExists(mUser);
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.signInButt) {
            if (mAuth.getCurrentUser() != null) {
                // User is signed in, check if they exist in database
                mUser = mAuth.getCurrentUser();
                checkUserExists(mUser);
                if (initScreen.userExists) {
                    Intent intent = new Intent(this, home.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(this, profCreate.class);
                    startActivity(intent);
                }
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
        } else if (v.getId() == R.id.signOutButt) {
            signOut();
            Toast.makeText(this, "Signed Out!", Toast.LENGTH_SHORT).show();
        } else {
            return;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    public void checkUserExists(FirebaseUser mUser) {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid());
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Handle the case where the data already exists
                    initScreen.userExists = true;
                }
                else {
                    // Handle the case where the data does not yet exist
                    initScreen.userExists = false;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
