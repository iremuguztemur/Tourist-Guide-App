package com.example.popfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;


public class KullaniciDetayActivity extends AppCompatActivity {


TextView tvName;
TextView tvLocation;
TextView tvPhone;
TextView tvExplain;
CircleImageView imgPP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kullanici_detay);


        Intent intent = getIntent();
        String gelenIsim = intent.getStringExtra("name");
        String gelenProfilePhoto = intent.getStringExtra("profile_photo");
        String gelenphone = intent.getStringExtra("phone");
        String gelenLocation = intent.getStringExtra("location");
        String gelenExplain = intent.getStringExtra("explain");

   //     Toast.makeText(getApplicationContext(), gelenIsim, Toast.LENGTH_SHORT).show();
        tvName = findViewById(R.id.guide_name);
        tvPhone = findViewById(R.id.guide_phone);
        tvLocation = findViewById(R.id.guide_location);
        tvExplain = findViewById(R.id.guide_explain);
        imgPP = findViewById(R.id.img_pp_detail);


        tvName.setText(gelenIsim);
        tvPhone.setText(gelenphone);
        tvLocation.setText(gelenLocation);
        tvExplain.setText(gelenExplain);

        Glide.with(getApplicationContext()).load(gelenProfilePhoto).into(imgPP);




    }


}