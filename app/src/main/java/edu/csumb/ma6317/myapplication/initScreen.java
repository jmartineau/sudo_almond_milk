package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class initScreen extends AppCompatActivity implements OnClickListener {

    private Button signInButton;
    private Button contGuestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_screen);

        //get references to the widgets in activity_init_screen.xml
        signInButton = (Button) findViewById(R.id.signInButt);
        contGuestButton = (Button) findViewById(R.id.guestButt);

        //set the click listeners for the buttons
        signInButton.setOnClickListener(this);
        contGuestButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.signInButt) {
            Intent intent = new Intent(this, signUp.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.guestButt) {
            Intent intent = new Intent(this, profCreate.class);
            startActivity(intent);
        }
        else {
            return;
        }
    }
}
