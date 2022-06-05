package com.example.popfinder.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.popfinder.Constant.AllConstant;
import com.example.popfinder.Permissions.AppPermissions;
import com.example.popfinder.R;
import com.example.popfinder.databinding.FragmentAddGuideBinding;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.grpc.Context;

public class AddGuideFragment extends Fragment {
    private Uri imageUriPP;
    private EditText editName, editPhone, editLocation,editExplain;
    private Button btnSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";
    private FragmentAddGuideBinding binding;
    private CircleImageView imgCircle ;
    private AppPermissions appPermissions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddGuideBinding.inflate(inflater, container, false);
        // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Turist Rehberi Ekle");

        editName = binding.name;
        editPhone = binding.phone;
        editLocation = binding.location;
        editExplain = binding.explain;
        btnSave = binding.btnSave;
        imgCircle = binding.imgPickPP;
        appPermissions = new AppPermissions();

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("kaydet...");

        btnSave.setOnClickListener(v -> {
            if (editName.getText().length() > 0 &&
                    editPhone.getText().length() > 0 &&
                    editExplain.getText().length() > 0 &&
                    editLocation.getText().length() > 0 &&
                    imageUriPP != null) {
                saveData(editName.getText().toString(), editPhone.getText().toString(),editExplain.getText().toString());
            } else {
                Toast.makeText(requireContext(), "Lütfen tüm verileri doldurun!", Toast.LENGTH_SHORT).show();
            }
        });

        if (getArguments() != null) {
            id = getArguments().getString("id");
            editName.setText(getArguments().getString("name"));
            editPhone.setText(getArguments().getString("phone"));
            editLocation.setText(getArguments().getString("location"));
            editExplain.setText(getArguments().getString("explain"));
        }
        imgCircle.setOnClickListener(view -> {
            if (appPermissions.isStorageOk(getActivity())) {
                pickImage();
            } else {
                appPermissions.requestStoragePermission(getActivity());
            }

        });

        return binding.getRoot();
    }
    private void pickImage() {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(getContext(), this);
    }
    private void saveData(String name, String phone, String location) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference().child("user_photo")

                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_photo");
        ref.putFile(imageUriPP)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photo_st = uri.toString();
                                    System.out.println(photo_st + " iremsu");

                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", name);
                                    user.put("phone", phone);
                                    user.put("location", location);
                                    user.put("explain", true);
                                    user.put("isActive", false);
                                    user.put("profile_photo", photo_st);

                                    progressDialog.show();
                                    if (!id.isEmpty()) {
                                        db.collection("users").document(id)
                                                .set(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(requireContext(), "basarili!", Toast.LENGTH_SHORT).show();
                                                            requireActivity().onBackPressed();
                                                        } else {
                                                            Toast.makeText(requireContext(), "basarisiz!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        db.collection("users")
                                                .add(user)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(requireContext(), "basarili!", Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                        requireActivity().onBackPressed();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                    }
                                                });
                                    }
                                }
                            });


                        }
                    }
                });




    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUriPP = result.getUri();
                Glide.with(this).load(imageUriPP).into(binding.imgPickPP);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception exception = result.getError();
                Log.d("TAG", "onActivityResult: " + exception);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AllConstant.STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(getActivity(), "Storage permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}