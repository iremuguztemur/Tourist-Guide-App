package com.example.popfinder.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.popfinder.CurrentSavedPlacesModel;
import com.example.popfinder.R;
import com.example.popfinder.Fragments.ToGoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.nio.charset.StandardCharsets;
import java.util.List;



public class SavedPlacesAdapter extends RecyclerView.Adapter<SavedPlacesAdapter.ViewHolder> {

    private List<CurrentSavedPlacesModel> currentSavedPlacesModels;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth1;



    public SavedPlacesAdapter(List<CurrentSavedPlacesModel>currentSavedPlacesModels){
        this.currentSavedPlacesModels=currentSavedPlacesModels;
    }

    @NonNull
    @Override
    public SavedPlacesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.items2,parent,false );
        return  new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedPlacesAdapter.ViewHolder holder, int position) {


        String basitname = currentSavedPlacesModels.get ( position).getPlcName ();
        String basitinfo = currentSavedPlacesModels.get ( position ).getPlcinfo ();
        String  basitlat = currentSavedPlacesModels.get ( position ).getPlcLatit ();
        String basitlong = currentSavedPlacesModels.get ( position ).getPlclong ();
        String basitimg = currentSavedPlacesModels.get ( position ).getPlcimage ();
        String kullanici=currentSavedPlacesModels.get ( position ).getKullanici ();

        firebaseDatabase=FirebaseDatabase.getInstance ("https://pop-finder-c631e-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference ("Placesinfo");

        firebaseAuth1 = FirebaseAuth.getInstance();
        String kullanıcimevcut = firebaseAuth1.getCurrentUser().getEmail ();

        holder.setData(basitname,basitinfo,basitlat,basitlong,basitimg,kullanici);

        holder.itemView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                if (kullanici.equals ( kullanıcimevcut )) {
                    AlertDialog.Builder builder = new AlertDialog.Builder ( view.getContext () );
                    builder.setPositiveButton ( "DELETE", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            AlertDialog.Builder builder = new AlertDialog.Builder ( view.getContext () );
                            builder.setTitle ( "Delete" );
                            builder.setMessage ( "Are You Sure to Delete This Places" );

                            builder.setPositiveButton ( "yes", new DialogInterface.OnClickListener () {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Query query = databaseReference.orderByChild ( "plcName" ).equalTo ( basitname );
                                    query.addListenerForSingleValueEvent ( new ValueEventListener () {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren ()) {

                                                dataSnapshot.getRef ().removeValue ();
                                                Toast.makeText ( view.getContext (), "Deleted", Toast.LENGTH_SHORT ).show ();

                                            }
                                            // buraya refresh kodu at
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    } );
                                }
                            } );
                            builder.setNegativeButton ( "No", new DialogInterface.OnClickListener () {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss ();
                                }
                            } );
                            AlertDialog alertDialog = builder.create ();
                            alertDialog.show ();

                        }
                    } );

                    builder.setNegativeButton ( "EDIT", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
/*
                            Fragment newFragment = new ToGoogleMap ();

                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.rec, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

                            String long22 = String.valueOf ( currentSavedPlacesModels.get ( i ).getPlclong () );
                            String lat22 = String.valueOf ( currentSavedPlacesModels.get ( i ).getPlcLatit () );
                            String name22 = String.valueOf ( currentSavedPlacesModels.get ( i ).getPlcName () );
                            String info22 = String.valueOf ( currentSavedPlacesModels.get ( i ).getPlcinfo () );
                            String img22 = String.valueOf ( currentSavedPlacesModels.get ( i ).getPlcimage () );


                            Bundle result11 =  new Bundle ();
                            result11.putString ( "df11", long22 );

                            Bundle result22 =  new Bundle ();
                            result22.putString ( "df11",lat22 );


                            Bundle result33 =  new Bundle ();
                            result33.putString ( "df11", img22 );

                            Bundle result44 =  new Bundle ();
                            result44.putString ( "df11",name22 );

                            Bundle result55 =  new Bundle ();
                            result55.putString ( "df11",info22 );


                            getParentFragmentManager ().setFragmentResult ( "dataForm11",result11 );
                            getParentFragmentManager ().setFragmentResult ( "dataForm22",result22 );
                            getParentFragmentManager ().setFragmentResult ( "dataForm33",result33 );
                            getParentFragmentManager ().setFragmentResult ( "dataForm44",result44 );
                            getParentFragmentManager ().setFragmentResult ( "dataForm55",result55 );

*/
                        }
                    } );
                    AlertDialog alertDialog = builder.create ();
                    alertDialog.show ();

                }
                else{

                    Toast.makeText ( view.getContext (), "can only be deleted or updated by the person who registered it ", Toast.LENGTH_SHORT ).show ();

                }
            }
        } );




    }

    @Override
    public int getItemCount() {
        return currentSavedPlacesModels.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtPlace;
        TextView txtinf2;
        TextView lat2;
        TextView long2;
        TextView txtkullanici;
        ImageView imgPlace;



        public ViewHolder(@NonNull View view) {
            super ( view );


            txtPlace= view.findViewById ( R.id.txtCity );
            txtinf2=view.findViewById ( R.id.txtinf2 );
            imgPlace=view.findViewById ( R.id.imgPlace );
            lat2=view.findViewById ( R.id.lat2 );
            long2=view.findViewById ( R.id.long2 );
            txtkullanici=view.findViewById ( R.id.txtkullanici );
        }

        public void setData(String basitname,String basitinfo,String basitlat,String basitlong,String basitimg,String kullanici) {

            byte[] imageAsByte = Base64.decode ( basitimg.getBytes( StandardCharsets.UTF_8 ),Base64.DEFAULT );
            Bitmap bitmap = BitmapFactory.decodeByteArray ( imageAsByte,0, imageAsByte.length );

            txtPlace.setText ( basitname );
            txtinf2.setText ( basitinfo );
            imgPlace.setImageBitmap ( bitmap );
            lat2.setText ( basitlat );
            long2.setText ( basitlong );
            txtkullanici.setText ( kullanici );

        }

    }
}
