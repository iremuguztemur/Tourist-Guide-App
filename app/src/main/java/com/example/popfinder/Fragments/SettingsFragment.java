package com.example.popfinder.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.popfinder.Constant.AllConstant;
import com.example.popfinder.Constant.LanguageConfig;
import com.example.popfinder.Constant.LanguageFlagClass;
import com.example.popfinder.Constant.LocaleHelper;
import com.example.popfinder.LoginActivity;
import com.example.popfinder.Permissions.AppPermissions;
import com.example.popfinder.R;
import com.example.popfinder.SignUpActivity;
import com.example.popfinder.Utility.LoadingDialog;
import com.example.popfinder.databinding.ActivityDirectionBinding;
import com.example.popfinder.databinding.ActivityForgetBinding;
import com.example.popfinder.databinding.ActivityLoginBinding;
import com.example.popfinder.databinding.FragmentHomeBinding;
import com.example.popfinder.databinding.FragmentSettingsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.rpc.context.AttributeContext;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.util.HashMap;
import java.util.Map;
import com.example.popfinder.Constant.AllConstant;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private ActivityDirectionBinding activityDirectionBinding;
    private ActivityForgetBinding activityForgetBinding;
    private ActivityLoginBinding activityLoginBinding;
    private FirebaseAuth firebaseAuth;
    private LoadingDialog loadingDialog;
    private AppPermissions appPermissions;
    private Uri imageUri;
    private LanguageFlagClass languageFlagClass;
    private FragmentHomeBinding fragmentHomeBinding;
    private boolean lang_selected= true;
    private MediaPlayer mediaPlayer;







    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        loadingDialog = new LoadingDialog(getActivity());
        appPermissions = new AppPermissions();
        languageFlagClass = new LanguageFlagClass ();



        binding.imgCamera.setOnClickListener(camera -> {

            if (appPermissions.isStorageOk(getContext())) {
                pickImage();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE}, AllConstant.STORAGE_REQUEST_CODE);
            }
        });

        binding.txtUsername.setOnClickListener(username -> {
            usernameDialog();
        });

        binding.emailChance.setOnClickListener(mail ->{
            goMailChance();

        });

        binding.changePassword.setOnClickListener(pass ->{
            goPasswordChange();
        });
        binding.cardLogout.setOnClickListener(logout ->{
            logout();
        });
        binding.havalKornarelative.setOnClickListener ( havalı->{
            sounds ();

        } );

        return binding.getRoot();
    }

    private void sounds (){

        mediaPlayer = MediaPlayer.create ( requireContext(),R.raw.krn);
        mediaPlayer.start ();
    }


    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.log_out_dialog, null, false);
        builder.setView(view);
        builder.setTitle("Logout!");

        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.signOut();

                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();


            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }



    private void goPasswordChange() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.change_password_dialog, null, false);
        builder.setView(view);
        TextInputEditText edtChangePassword = view.findViewById(R.id.edtDialogChangePassword);

        builder.setTitle("Edit Password");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String changePassword = edtChangePassword.getText().toString().trim();
                if (!changePassword.isEmpty()) {
                    firebaseAuth.getCurrentUser().updatePassword(changePassword)
                            .addOnCompleteListener (new OnCompleteListener <Void> () {
                                @Override
                                public void onComplete (@NonNull Task <Void> task) {
                                    if (task.isSuccessful ()) {

                                        Toast.makeText(getActivity(),"Password  has been changed!",Toast.LENGTH_LONG).show();
                                    } else {
                                        Exception e = task.getException();
                                        Toast.makeText (getActivity(), "Error updating password: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.w("updateEmail", "Unable to update password", e);
                                    }
                                    dialog.dismiss ();
                                }
                            });

                } else {
                    Toast.makeText(getContext(), "Email is required", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();

    }

    private void goMailChance() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.change_email_dialog, null, false);
        builder.setView(view);
        TextInputEditText edtChangeMail = view.findViewById(R.id.edtDialogChangeMail);

        builder.setTitle("Edit E-mail");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String changeMail = edtChangeMail.getText().toString().trim();
                if (!changeMail.isEmpty()) {

                    firebaseAuth.getCurrentUser().updateEmail(changeMail)
                            .addOnCompleteListener (new OnCompleteListener <Void> () {
                                @Override
                                public void onComplete (@NonNull Task <Void> task) {
                                    if (task.isSuccessful ()) {

                                        firebaseAuth.getCurrentUser()
                                                .sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> email) {
                                                        if (email.isSuccessful()) {

                                                            Toast.makeText(getActivity(), "Please verify email", Toast.LENGTH_SHORT).show();
                                                        } else {

                                                            Toast.makeText(getActivity(), "Error : " + email.getException(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        Toast.makeText(getActivity(),"Mail  has been changed!",Toast.LENGTH_LONG).show();
                                    } else {
                                        Exception e = task.getException();
                                        Toast.makeText (getActivity(), "Error updating email: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.w("updateEmail", "Unable to update email", e);
                                    }
                                    dialog.dismiss ();
                                }
                            });

                } else {
                    Toast.makeText(getContext(), "Email is required", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();


    }

    private void pickImage() {

        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(getContext(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ayarlar");
        binding.txtEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        binding.txtUsername.setText(firebaseAuth.getCurrentUser().getDisplayName());

        Glide.with(requireContext()).load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(binding.imgProfile);

        if (firebaseAuth.getCurrentUser().isEmailVerified()) {
            binding.txtEEmail.setVisibility(View.GONE);
        } else {
            binding.txtEEmail.setVisibility(View.VISIBLE);
        }

        binding.txtEEmail.setOnClickListener(verify -> {
            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Mail sent verify the email", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onComplete: profile email " + task.getException());
                    }
                }
            });
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AllConstant.STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(getContext(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                imageUri = result.getUri();
                uploadImage(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception exception = result.getError();
                Toast.makeText(getContext(), "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImage(Uri imageUri) {

        loadingDialog.startLoading();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(firebaseAuth.getUid() + AllConstant.IMAGE_PATH).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> image = taskSnapshot.getStorage().getDownloadUrl();
                image.addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            String url = task.getResult().toString();

                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(Uri.parse(url))
                                    .build();

                            firebaseAuth.getCurrentUser().updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> profile) {

                                    if (profile.isSuccessful()) {

                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("image", url);
                                        databaseReference.child(firebaseAuth.getUid()).updateChildren(map);
                                        Glide.with(requireContext()).load(url).into(binding.imgProfile);
                                        loadingDialog.stopLoading();
                                        Toast.makeText(getContext(), "Image Updated", Toast.LENGTH_SHORT).show();

                                    } else {
                                        loadingDialog.stopLoading();
                                        Log.d("TAG", "Profile : " + profile.getException());
                                        Toast.makeText(getContext(), "Profile : " + profile.getException(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                        } else {
                            loadingDialog.stopLoading();
                            Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "onComplete: image url  " + task.getException());
                        }

                    }
                });
            }
        });
    }

    private void usernameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.username_dialog_layout, null, false);
        builder.setView(view);
        TextInputEditText edtUsername = view.findViewById(R.id.edtDialogUsername);

        builder.setTitle("Edit Username");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = edtUsername.getText().toString().trim();
                if (!username.isEmpty()) {

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build();
                    firebaseAuth.getCurrentUser().updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                                Map<String, Object> map = new HashMap<>();
                                map.put("username", username);
                                databaseReference.child(firebaseAuth.getUid()).updateChildren(map);

                                binding.txtUsername.setText(username);
                                Toast.makeText(getContext(), "Username is updated", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.d("TAG", "onComplete: " + task.getException());
                                Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Username is required", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }




}