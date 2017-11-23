package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class failMsg extends AppCompatActivity implements View.OnClickListener{

    private Button tryAgainButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_msg);

        // Get references to the widgets in activity_success_msg.xml
        tryAgainButton = (Button) findViewById(R.id.tryAgainButt);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        // Set the click listeners for the buttons
        tryAgainButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.tryAgainButt) {
            Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, rateCall.class);
//            startActivity(intent);
        }
        else if (v.getId() == R.id.cancelButton) {
            Toast.makeText(this, "Go back home", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, home.class);
            startActivity(intent);
        }

    }
}
