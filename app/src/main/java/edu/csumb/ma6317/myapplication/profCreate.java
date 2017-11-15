package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profCreate extends AppCompatActivity implements View.OnClickListener {

    // Button variables
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_create);

        continueButton = findViewById(R.id.submitButt);
        continueButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.submitButt) {
            // User is signed in
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
    }
}

