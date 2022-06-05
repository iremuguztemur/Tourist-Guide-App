package com.example.popfinder.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popfinder.Fragments.HomeFragment;
import com.example.popfinder.Fragments.SaveScreenFragment;
import com.example.popfinder.Fragments.SettingsFragment;
import com.example.popfinder.Fragments.ToGoogleMap;
import com.example.popfinder.Fragments.WebPageFragment;
import com.example.popfinder.R;
import com.example.popfinder.TouristicPlaceModelClass;

import java.util.List;

public class TouristicAdapter extends RecyclerView.Adapter<TouristicAdapter.ViewHolder> {

    private List<TouristicPlaceModelClass> touristicplaceslist;

    public TouristicAdapter(List<TouristicPlaceModelClass>touristicplaceslist){
        this.touristicplaceslist=touristicplaceslist;
    }

    @NonNull
    @Override
    public TouristicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.items,parent,false );
        return  new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull TouristicAdapter.ViewHolder holder, int position) {

        int resource = touristicplaceslist.get ( position ).getImageview1 ();
        String name = touristicplaceslist.get ( position ).getTextview1 ();
        String msg = touristicplaceslist.get ( position ).getTextview2 ();
        String lnt = touristicplaceslist.get ( position ).getLats ();
        String longs = touristicplaceslist.get ( position ).getLongs ();


        holder.setData(resource,name,msg,lnt,longs);

        holder.itemView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                    //google link ayarla
                AppCompatActivity activity = (AppCompatActivity)view.getContext ();
                WebPageFragment toGoogleMap = new WebPageFragment ();
                activity.getSupportFragmentManager ().beginTransaction ().replace ( R.id.fragmentContainer,toGoogleMap ).addToBackStack ( null ).commit ();

            }
        } );

    }

    @Override
    public int getItemCount() {
        return touristicplaceslist.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;



        public ViewHolder(@NonNull View itemView) {
            super ( itemView );


            imageView = itemView.findViewById (R.id.imageview1 );
            textView = itemView.findViewById ( R.id.textview );
            textView1 = itemView.findViewById ( R.id.textview1 );
            textView2 = itemView.findViewById ( R.id.lat1 );
            textView3 = itemView.findViewById ( R.id.long1 );


        }

        public void setData(int resource, String name, String msg, String lnt, String longs) {

            imageView.setImageResource ( resource );
            textView.setText ( name );
            textView1.setText ( msg );
            textView2.setText ( lnt );
            textView3.setText ( longs );

        }

    }
}
