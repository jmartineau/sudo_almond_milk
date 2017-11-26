package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class rateCall extends AppCompatActivity implements View.OnClickListener {

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    private Button submitButton;
    private Button notNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_call);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        System.out.println("===PRINT CURRENT USER !!!!!!!!!!");
        System.out.println(mUser.getUid());
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get references to the widgets in activity_success_msg.xml
        submitButton = (Button) findViewById(R.id.submitButt);
        notNowButton = (Button) findViewById(R.id.notNowButt);

        // Set the click listeners for the buttons
        submitButton.setOnClickListener(this);
        notNowButton.setOnClickListener(this);
    }

//    Input: User A will leave a rating for userB_ID
//    Process: Overwrite userB's rating
//    Output: True if updated (success_msg.java), False is not updated
    boolean leaveRatingFor(int userB_ID) {

        return true;
    }


    public void onClick(View v) {
        if (v.getId() == R.id.submitButt) {
            Toast.makeText(this, "Rate Submitted!", Toast.LENGTH_SHORT).show();
            //TODO: Send rating to db
//            Intent intent = new Intent(this, home.class);
//            startActivity(intent);
        }
        // Go back to Home page
        else if (v.getId() == R.id.notNowButt) {
            Toast.makeText(this, "Rate Later", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, home.class);
            startActivity(intent);
        }

    }
}
