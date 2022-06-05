package com.example.popfinder.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.popfinder.R;

import java.util.Random;


public class SpinWheelFragment extends Fragment {

    Button btnSpin;
    ImageView ivWheel;
    CountDownTimer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_spin_wheel, container, false);

        // initializing views
        btnSpin = v.findViewById(R.id.btnSpin);
        ivWheel = v.findViewById(R.id.ivWheel);

        // creating an object of Random class
        // to generate random numbers for the spin
        Random random = new Random();

        // on click listener for btnSpin
        btnSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // disabling the button so that user
                // should not click on the button
                // while the wheel is spinning
                btnSpin.setEnabled(false);

                // reading random value between 10 to 30
                int spin = random.nextInt(20) + 10;

                // since the wheel has 10 divisions, the
                // rotation should be a multiple of
                // 360/10 = 36 degrees
                spin = spin * 36;

                // timer for each degree movement
                timer = new CountDownTimer(spin * 20, 1) {
                    @Override
                    public void onTick(long l) {
                        // rotate the wheel
                        float rotation = ivWheel.getRotation() + 2;
                        ivWheel.setRotation(rotation);
                    }

                    @Override
                    public void onFinish() {
                        // enabling the button again
                        btnSpin.setEnabled(true);
                    }
                }.start();

            }
        });
        return v;
    }

}