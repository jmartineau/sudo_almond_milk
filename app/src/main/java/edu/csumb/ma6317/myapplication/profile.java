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
    private Button addLangButton;
    private Button deleteLangButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get references to the widgets in activity_profile.xml
        usernameText = (TextView) findViewById(R.id.usernameTxt);
        languagesText = (TextView) findViewById(R.id.langListTxt);
        img = (ImageView) findViewById(R.id.userPicImg);
        addLangButton = (Button) findViewById(R.id.addLangButt);
        deleteLangButton = (Button) findViewById(R.id.deleteLangButt);

        // Set the click listeners for the buttons
        img.setOnClickListener(this);
        addLangButton.setOnClickListener(this);
        deleteLangButton.setOnClickListener(this);

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
        else if (v.getId() == R.id.addLangButt) {
            Toast.makeText(this, "Add Language Button Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, addLang.class);
            //startActivity(intent);
        }
        else if (v.getId() == R.id.deleteLangButt) {
            Toast.makeText(this, "Delete Language Button Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, deleteLang.class);
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
