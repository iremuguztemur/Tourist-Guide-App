package com.example.popfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.popfinder.R;
import com.example.popfinder.databinding.ActivityForgetBinding;

public class ForgetActivity extends AppCompatActivity {

    private ActivityForgetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(view -> {
            onBackPressed();
        });


    }
}