package com.example.popfinder.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popfinder.R;

public class ToGoogleMap extends Fragment {

    private String Long222;
    private  String lat222;
    private  String name222;
    private  String info222;
    private ImageView imageView222;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =inflater.inflate ( R.layout.fragment_to_google_map, container, false );

        getParentFragmentManager ().setFragmentResultListener ( "dataForm11", this, new FragmentResultListener () {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String long22 = result.getString ( "df11" );
                TextView textView = v.findViewById ( R.id.longolsun );
                textView.setText ( long22 );
                Long222=long22;

            }
        } );
        getParentFragmentManager ().setFragmentResultListener ( "dataForm22", this, new FragmentResultListener () {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String lat22 = result.getString ( "df11" );
                TextView textView = v.findViewById ( R.id.latolsun );
                textView.setText ( lat22 );
                lat222=lat22;
            }
        } );
        getParentFragmentManager ().setFragmentResultListener ( "dataForm33", this, new FragmentResultListener () {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String img22 = result.getString ( "df11" );
                byte [] encodeByte = Base64.decode(img22,Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                ImageView imageView222 = v.findViewById ( R.id.imageolsun );
                imageView222.setImageBitmap ( bitmap );


            }
        } );

        getParentFragmentManager ().setFragmentResultListener ( "dataForm44", this, new FragmentResultListener () {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String name22 = result.getString ( "df11" );
                TextView textView = v.findViewById ( R.id.savedplacestoname );
                textView.setText ( name22 );
                Long222=name22;

            }
        } );

        getParentFragmentManager ().setFragmentResultListener ( "dataForm55", this, new FragmentResultListener () {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String info22 = result.getString ( "df11" );
                TextView textView = v.findViewById ( R.id.infoolsun );
                textView.setText ( info22 );
                Long222=info22;

            }
        } );

        //addMarker3 (lnttut11,longtut11);

        return v;
    }
}