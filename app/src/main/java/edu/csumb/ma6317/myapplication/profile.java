package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class profile extends AppCompatActivity {


    Intent myIntent = new Intent(this, profPic.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView img = (ImageView) findViewById(R.id.userPicImg);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(myIntent);
            }
        });
    }
}
