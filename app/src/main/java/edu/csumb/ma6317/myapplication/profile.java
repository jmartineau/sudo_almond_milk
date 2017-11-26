package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class profile extends AppCompatActivity implements View.OnClickListener {

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private FirebaseUser mUser;

    // Widget variables
    private TextView usernameText;
    private TextView languagesText;
    private ImageView img;
    private Button editProfileButton;
    private Button translationLogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get references to the widgets in activity_profile.xml
        usernameText = (TextView) findViewById(R.id.usernameTxt);
        languagesText = (TextView) findViewById(R.id.langListTxt);
        img = (ImageView) findViewById(R.id.userPicImg);
        editProfileButton = (Button) findViewById(R.id.editProfileButt);
        translationLogButton = (Button) findViewById(R.id.translationLogButt);

        // Set the click listeners for the buttons
        img.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);
        translationLogButton.setOnClickListener(this);

        // Implement scrolling for languages TextView
        languagesText.setMovementMethod(new ScrollingMovementMethod());

        // Read from Firebase database
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String displayName = dataSnapshot.child(mUser.getUid()).child("displayName").getValue(String.class);

                // GenericTypeIndicator is needed to retrieve arrays from Firebase
                GenericTypeIndicator<List<String>> genericTypeIndicator = new GenericTypeIndicator<List<String>>() {};
                List<String> languages = dataSnapshot.child(mUser.getUid()).child("languages").getValue(genericTypeIndicator);

                displayProfileInfo(displayName, languages);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.userPicImg) {
            Toast.makeText(this, "Image Button Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, profPic.class);
            //startActivity(intent);
        }
        else if (v.getId() == R.id.editProfileButt) {
            Intent intent = new Intent(this, profEdit.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.translationLogButt) {
            Toast.makeText(this, "Translation Log Button Clicked", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(this, translationLog.class);
            //startActivity(intent);
        }
    }

    private boolean displayProfileInfo(String displayName, List languages) {
        if (displayName == null || languages == null) {
            return false;
        }

        languagesText.setText("");
        for (Object language : languages) {
            languagesText.append(language + "\n");
        }
        usernameText.setText(displayName);

        return true;
    }
}
