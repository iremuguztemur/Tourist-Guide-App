package com.example.popfinder.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.popfinder.CurrentSavedPlacesModel;
import com.example.popfinder.DirectionActivity;
import com.example.popfinder.MainActivity;
import com.example.popfinder.R;
import com.example.popfinder.Utility.LoadingDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SavedPlacesFragment extends Fragment {
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    FirebaseDatabase firebaseDatabase;
    FirebaseDatabase firebaseDatabase2;
    List<CurrentSavedPlacesModel> placeList = new ArrayList<> ();
    CurrentSavedPlacesModel places ;
    ListView listView;

    private LoadingDialog loadingDialog;



    public SavedPlacesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_saved_places, container, false );
        listView = view.findViewById ( R.id.listview );

        firebaseDatabase2=FirebaseDatabase.getInstance ("https://pop-finder-c631e-default-rtdb.firebaseio.com/");
        firebaseDatabase=FirebaseDatabase.getInstance ("https://pop-finder-c631e-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference ("Placesinfo");
        databaseReference.addChildEventListener ( new ChildEventListener () {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                    Map<String,String> map = (Map<String, String>) snapshot.getValue ();

                    places = new CurrentSavedPlacesModel();

                    places.setPlcName ( map.get ( "plcName" ) );
                    places.setPlcinfo ( map.get("plcinfo") );
                    places.setPlcimage ( map.get("plcimage") );
                    places.setPlclong ( map.get("plclong") );
                    places.setPlcLatit ( map.get("plcLatit") );
                    places.setKullanici ( map.get ( "kullanici" ) );

                    placeList.add(places);

                MyAdapter myAdapter = new MyAdapter (getContext (),placeList);


                listView.setAdapter ( myAdapter );


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }




        } );



        return view ;
    }

    //////////////
    public class MyAdapter extends BaseAdapter{
        public int tutulan_pozition;
        Context context;
        List<CurrentSavedPlacesModel> stringList;
        TextView txtPlace;
        TextView txtinf2;
        TextView lat2;
        TextView long2;
        TextView txtkullanici;
        ImageView imgPlace;
        String basitname;
        String basitinfo;
        String basitlat;
        String basitlong;
        String basitimg;
        String kullanici;
        FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
        String kullanıcimevcut = firebaseAuth1.getCurrentUser().getEmail ();

        public MyAdapter(Context context, List<CurrentSavedPlacesModel> stringList) {
            this.context = context;
            this.stringList = stringList;
        }

        @Override
        public int getCount() {
            return stringList.size ();
        }

        @Override
        public Object getItem(int i) {
                return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view=LayoutInflater.from ( context ).inflate ( R.layout.items2,viewGroup, false );
            txtPlace= view.findViewById ( R.id.txtCity );
            txtinf2=view.findViewById ( R.id.txtinf2 );
            imgPlace=view.findViewById ( R.id.imgPlace );
            lat2=view.findViewById ( R.id.lat2 );
            long2=view.findViewById ( R.id.long2 );
            txtkullanici=view.findViewById ( R.id.txtkullanici );
            ImageButton button_edit = view.findViewById ( R.id.button_edit );
            ImageButton button_delete = view.findViewById ( R.id.button_delete );
            ImageButton button_direction = view.findViewById ( R.id.button_direction );
            tutulan_pozition=i;
            lat2.setText ( stringList.get ( i ).getPlcLatit () );
            long2.setText ( stringList.get ( i ).getPlclong () );
            txtkullanici.setText ( stringList.get ( i ).getKullanici () );

            txtinf2.setText ( stringList.get ( i ).getPlcinfo () );

            byte[] imageAsByte = Base64.decode ( placeList.get ( i ).getPlcimage ().getBytes( StandardCharsets.UTF_8 ),Base64.DEFAULT );
            txtPlace.setText ( stringList.get ( i ).getPlcName () );
            Bitmap bitmap = BitmapFactory.decodeByteArray ( imageAsByte,0, imageAsByte.length );
            imgPlace.setImageBitmap ( bitmap );


                button_direction.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {


                        //rastgele noktalara adres çıkartma
                        Intent intent = new Intent(requireContext(), DirectionActivity.class);
                        Double lat = Double.valueOf ( stringList.get ( i ).getPlcLatit () );
                        Double lng = Double.valueOf ( stringList.get ( i ).getPlclong () );

                        intent.putExtra("lat", lat);
                        intent.putExtra("lng", lng);

                        startActivity(intent);

                    }
                } );


                button_edit.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick(View view) {

                                basitname = stringList.get ( i ).getPlcName ();
                                basitinfo = stringList.get ( i ).getPlcinfo ();
                                basitlat = stringList.get ( i ).getPlcLatit ();
                                basitlong = stringList.get ( i ).getPlclong ();
                                basitimg = stringList.get ( i ).getPlcimage ();
                                kullanici=stringList.get ( i ).getKullanici ();
                                System.out.println ("mevcutkullanıcı=="+kullanıcimevcut+"mevcutkullanıcı=="+stringList.get ( i ).getKullanici ());
                                if( kullanici.equals ( kullanıcimevcut )){
                                showUpdateDialog(basitname,basitinfo,basitlat,basitlong,basitimg,kullanici);
                                }
                                else{

                                    Toast.makeText ( requireContext (), "can only be updated by the person who registered it ", Toast.LENGTH_SHORT ).show ();

                                }
                            }
                } );




                button_delete.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        kullanici=stringList.get ( i ).getKullanici ();
                        basitname = stringList.get ( i ).getPlcName ();
                        System.out.println ("mevcutkullanıcı=="+kullanıcimevcut+"mevcutkullanıcı=="+stringList.get ( i ).getKullanici ());
                        if(kullanici.equals ( kullanıcimevcut )){
                        showDeleteDialog(basitname);
                        }
                        else {

                            Toast.makeText ( requireContext (), "can only be deleted by the person who registered it ", Toast.LENGTH_SHORT ).show ();

                        }

                    }
                } );


            view.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View view) {

                    Fragment newFragment = new ToGoogleMap ();

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.rec, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    String long22 = String.valueOf ( stringList.get ( i ).getPlclong () );
                    String lat22 = String.valueOf ( stringList.get ( i ).getPlcLatit () );
                    String name22 = String.valueOf ( stringList.get ( i ).getPlcName () );
                    String info22 = String.valueOf ( stringList.get ( i ).getPlcinfo () );
                    String img22 = String.valueOf ( stringList.get ( i ).getPlcimage () );


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

                }
            } );

            return view;


        }



        public void showDeleteDialog(String name){

            AlertDialog.Builder builder  = new AlertDialog.Builder ( SavedPlacesFragment.this.getView ().getContext ());
            builder.setTitle ("Delete");
            builder.setMessage ( "Are You Sure to Delete This Places" );

            builder.setPositiveButton ( "yes", new DialogInterface.OnClickListener () {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Query query = databaseReference.orderByChild ("plcName").equalTo (name);
                    query.addListenerForSingleValueEvent ( new ValueEventListener () {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren ()){

                                dataSnapshot.getRef ().removeValue ();
                                Toast.makeText ( SavedPlacesFragment.this.getView ().getContext (), "Deleted", Toast.LENGTH_SHORT ).show ();



                            }
                            SavedPlacesFragment fragment = new SavedPlacesFragment ();
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction transaction=fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragmentContainer, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

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

        public void showUpdateDialog(String name,String info,String lat, String longg, String basitimg,String kullanici){
            EditText placesname;
            EditText placesinfo;



            AlertDialog.Builder builder  = new AlertDialog.Builder ( SavedPlacesFragment.this.getView ().getContext ());
            builder.setTitle ("Edit");
            builder.setMessage ( "Please Fill This Attributes" );

            View view = getLayoutInflater ().inflate ( R.layout.edit_places,null);
            placesname=view.findViewById ( R.id.Edit_placesname );
            placesinfo=view.findViewById ( R.id.Edit_placesinfo );
            builder.setView (view);
            placesname.setHint (name);
            placesinfo.setHint (info);
            placesname.setText ( name );
            placesinfo.setText ( info );

            CurrentSavedPlacesModel places = new CurrentSavedPlacesModel ();
            builder.setPositiveButton ( "yes", new DialogInterface.OnClickListener () {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String txtname = placesname.getText ().toString ();
                    String txtinfo = placesinfo.getText ().toString ();

                    places.setPlcName ( txtname );
                    places.setPlcinfo ( txtinfo );
                    places.setPlcimage (basitimg);
                    places.setPlclong (longg);
                    places.setPlcLatit ( lat );
                    places.setKullanici ( kullanici );

                    databaseReference2=firebaseDatabase.getReference ("Placesinfo").child ( txtname );
                    databaseReference2.addValueEventListener ( new ValueEventListener () {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference2.setValue ( places );
                            databaseReference.child(name).removeValue();

                            SavedPlacesFragment fragment = new SavedPlacesFragment ();
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction transaction=fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragmentContainer, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

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





    }


  ////////////////////////////////




}