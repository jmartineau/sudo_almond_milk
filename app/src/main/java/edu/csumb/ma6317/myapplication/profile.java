package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class profile extends AppCompatActivity implements View.OnClickListener {

    // Widget variables
    private ImageView img;
    private Button addLangButton;
    private Button deleteLangButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get references to the widgets in activity_profile.xml
        img = (ImageView) findViewById(R.id.userPicImg);
        addLangButton = (Button) findViewById(R.id.addLangButt);
        deleteLangButton = (Button) findViewById(R.id.deleteLangButt);

        // Set the click listeners for the buttons
        img.setOnClickListener(this);
        addLangButton.setOnClickListener(this);
        deleteLangButton.setOnClickListener(this);
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
}
