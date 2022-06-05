package com.example.popfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity2 extends AppCompatActivity {

    TextView actName ;
    CircleImageView actPP ;
    TextView actphone ;
    TextView actlocation ;
    TextView actexplain ;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String gelenIsim = intent.getStringExtra("name");
        String gelenProfilePhoto = intent.getStringExtra("profile_photo");
        String gelenLocation = intent.getStringExtra("location");
        String gelenPhone = intent.getStringExtra("phone");
        String gelenExplain = intent.getStringExtra("explain");

       // actPP = findViewById(R.id.img_pp);
        actName = findViewById(R.id.guide_name);
        //actlocation = findViewById(R.id)
        //   Glide.with(getApplicationContext()).load(gelenProfilePhoto).into(actPP);

        actName.setText(gelenIsim);
    }
}