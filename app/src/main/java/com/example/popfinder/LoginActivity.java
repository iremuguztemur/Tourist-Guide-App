package com.example.popfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.popfinder.Constant.LanguageConfig;
import com.example.popfinder.Constant.LocaleHelper;
import com.example.popfinder.R;
import com.example.popfinder.Utility.LoadingDialog;
import com.example.popfinder.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String email, password;
    private LoadingDialog loadingDialog;
    Context context;
    Context boscontext;
    Resources resources;
    static String tutulandil="en";


    @Override
    protected void attachBaseContext(Context newBase) {

        Context context1 = LanguageConfig.changeLanguage ( newBase,tutulandil );
        super.attachBaseContext ( context1 );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = new LoadingDialog(this);

        binding.btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });

        binding.txtForgetPassword.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
        });
        binding.btnLogin.setOnClickListener(view -> {
            if (areFieldReady()) {
                login();
            }
        });
        binding.btnLanguages.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view1) {

                final String[] language={"en" , "tr"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.log_out_dialog, null, false);
                builder.setView(view);
                builder.setTitle ( "Select a Language" ).setSingleChoiceItems ( language, -1, new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        if(which==0){
                                tutulandil=language[which];

                        }
                        if(which==1){
                            tutulandil=language[which];
                        }
                        System.out.println ("sssssss"+ which);

                    }
                } )
                        .setPositiveButton ("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(LoginActivity.this, LoginActivity.class));

                            }
                        });
                builder.create().show();

            }
        } );

    }

    private void login() {
        loadingDialog.startLoading();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                        loadingDialog.stopLoading();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        firebaseAuth.getCurrentUser()
                                .sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> email) {
                                        if (email.isSuccessful()) {
                                            loadingDialog.stopLoading();
                                            Toast.makeText(LoginActivity.this, "Please verify email", Toast.LENGTH_SHORT).show();
                                        } else {
                                            loadingDialog.stopLoading();
                                            Toast.makeText(LoginActivity.this, "Error : " + email.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                } else {
                    loadingDialog.stopLoading();
                    Toast.makeText(LoginActivity.this, "Error : " + task.getException(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean areFieldReady() {

        email = binding.edtEmail.getText().toString().trim();
        password = binding.edtPassword.getText().toString().trim();

        boolean flag = false;
        View requestView = null;

        if (email.isEmpty()) {
            binding.edtEmail.setError("Field is required");
            flag = true;
            requestView = binding.edtEmail;
        } else if (password.isEmpty()) {
            binding.edtPassword.setError("Field is required");
            flag = true;
            requestView = binding.edtPassword;
        } else if (password.length() < 6) {
            binding.edtPassword.setError("Minimum 8 characters");
            flag = true;
            requestView = binding.edtPassword;
        }

        if (flag) {
            requestView.requestFocus();
            return false;
        } else {
            return true;
        }

    }
}