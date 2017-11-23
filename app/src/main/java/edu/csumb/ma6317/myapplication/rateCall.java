package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class rateCall extends AppCompatActivity implements View.OnClickListener {

    private Button submitButton;
    private Button notNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_call);

        // Get references to the widgets in activity_success_msg.xml
        submitButton = (Button) findViewById(R.id.submitButt);
        notNowButton = (Button) findViewById(R.id.notNowButt);

        // Set the click listeners for the buttons
        submitButton.setOnClickListener(this);
        notNowButton.setOnClickListener(this);
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
