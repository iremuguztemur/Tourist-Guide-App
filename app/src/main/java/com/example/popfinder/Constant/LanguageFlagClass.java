package com.example.popfinder.Constant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.popfinder.MainActivity;
import com.example.popfinder.R;
import com.example.popfinder.databinding.ActivityDirectionBinding;
import com.example.popfinder.databinding.ActivityForgetBinding;
import com.example.popfinder.databinding.ActivityLoginBinding;
import com.example.popfinder.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

public class LanguageFlagClass  extends MainActivity {

    private static int languageflag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    public void setLanguageflag(int languageflag) {
        this.languageflag = languageflag;
    }

    public int getLanguageflag() {
        return languageflag;
    }
}
