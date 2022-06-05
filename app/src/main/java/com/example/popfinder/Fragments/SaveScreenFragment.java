package com.example.popfinder.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.popfinder.Constant.AllConstant;
import com.example.popfinder.CurrentSavedPlacesModel;
import com.example.popfinder.FileUtils;
import com.example.popfinder.Permissions.AppPermissions;
import com.example.popfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;


public class SaveScreenFragment extends Fragment {


    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    EditText edplcname, edinfo;
    Button btnSavethisplaces;
    ImageView imgthisplace;
    private AppPermissions appPermissions;
    String encodeImage;
    String longdata;
    String latdata;
    private Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v = inflater.inflate ( R.layout.fragment_save_screen, container, false );

       firebaseDatabase=FirebaseDatabase.getInstance ("https://pop-finder-c631e-default-rtdb.firebaseio.com/");
       edplcname=v.findViewById ( R.id.edplcname );
       edinfo=v.findViewById ( R.id.edplcinfo );
       btnSavethisplaces=v.findViewById ( R.id.btnSavethisplaces );
        imgthisplace=v.findViewById ( R.id.imgthisplace );
        appPermissions = new AppPermissions();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String kullanıci = firebaseAuth.getCurrentUser().getEmail ();

        getParentFragmentManager ().setFragmentResultListener ( "dataForm1", this, new FragmentResultListener () {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String data = result.getString ( "df1" );
                TextView textView = v.findViewById ( R.id.dataform1 );
                textView.setText ( data );
                longdata=data;

            }
        } );
        getParentFragmentManager ().setFragmentResultListener ( "dataForm2", this, new FragmentResultListener () {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String data = result.getString ( "df1" );
                TextView textView = v.findViewById ( R.id.dataform2 );
                textView.setText ( data );
                latdata=data;
            }
        } );









        imgthisplace.setOnClickListener(camera -> {

            if (appPermissions.isStorageOk(getContext())) {
                takeImage ();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE}, AllConstant.STORAGE_REQUEST_CODE);
            }
        });

        btnSavethisplaces.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                CurrentSavedPlacesModel places = new CurrentSavedPlacesModel ();
                //List<CurrentSavedPlacesModel> placeList = new ArrayList<> ();

                if(edplcname.getText ()!=null && edinfo.getText ()!=null && encodeImage != null){
                places.setPlcName ( edplcname.getText ().toString () );
                places.setPlcinfo ( edinfo.getText ().toString () );
                places.setPlcimage (encodeImage);
                places.setPlclong (longdata);
                places.setPlcLatit ( latdata );
                places.setKullanici ( kullanıci );



                databaseReference=firebaseDatabase.getReference ("Placesinfo").child ( edplcname.getText ().toString () );
                databaseReference.addValueEventListener ( new ValueEventListener () {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.setValue ( places );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                } );


                    Toast.makeText(requireContext(), "Successful ", Toast.LENGTH_SHORT).show();
                    Fragment newFragment = new HomeFragment ();

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragmentContainer, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else{
                    edplcname.setText ( "" );
                    edinfo.setText ("");

                    Toast.makeText(requireContext(), "Please Fill All Entries ", Toast.LENGTH_SHORT).show();
                }
            }

        } );






        return v;
    }

    public boolean checkCameraPermission(){

        int result1= ContextCompat.checkSelfPermission ( getActivity (), Manifest.permission.CAMERA );
        int result2= ContextCompat.checkSelfPermission ( getActivity (), Manifest.permission.WRITE_EXTERNAL_STORAGE );
        int result3= ContextCompat.checkSelfPermission ( getActivity (), Manifest.permission.READ_EXTERNAL_STORAGE );

        return  result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED;

    }

    void takeImage(){
        CropImage.activity ()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(getActivity (),this );
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                Glide.with(this).load(imageUri).into(imgthisplace);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception exception = result.getError();
                Log.d("TAG", "onActivityResult: " + exception);
            }
            String path = imageUri.getPath ();
            compressImage ( path );
        }
    }

    void compressImage(String path){
        Luban.compress ( getActivity (),new File ( path ))
            .setMaxSize ( 50 ).launch ( new OnCompressListener () {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(File file) {
                Bitmap bitmap = BitmapFactory.decodeFile ( file.getAbsolutePath () );
                ByteArrayOutputStream b = new ByteArrayOutputStream ();
                bitmap.compress ( Bitmap.CompressFormat.JPEG,100,b );
                byte[] byteArray = b.toByteArray ();
                encodeImage = Base64.encodeToString ( byteArray,Base64.DEFAULT );
                Picasso.with ( getContext () ).load ( file ).into ( imgthisplace );


            }

            @Override
            public void onError(Throwable e) {

            }
        } );

    }





}