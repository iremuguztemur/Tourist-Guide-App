package com.example.popfinder.Adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.popfinder.KullaniciDetayActivity;
import com.example.popfinder.Model.AddGuideModel;
import com.example.popfinder.R;

import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.MyViewHolder> {
    private Context context;
    private List<AddGuideModel> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public GuideAdapter(Context context, List<AddGuideModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_guide, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.phone.setText(list.get(position).getPhone());
//        holder.explain.setText(list.get(position).getExplain());
        holder.location.setText(list.get(position).getLocation());
        Glide.with(context).load(list.get(position).getProfilePhoto()).into(holder.profile_photo);

        holder.listenData(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, phone,explain, location;
        ImageView profile_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.guide_name);
            phone = itemView.findViewById(R.id.guide_phone);
            location = itemView.findViewById(R.id.guide_location);
            profile_photo = itemView.findViewById(R.id.img_pp);

        }

        public void listenData(int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  ///  Toast.makeText(context, list.get(position).getName(), Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(context, KullaniciDetayActivity.class);
                    myIntent.putExtra("name", list.get(position).getName()); //Optional parameters
                    myIntent.putExtra("profile_photo", list.get(position).getProfilePhoto()); //Optional parameters
                    myIntent.putExtra("phone", list.get(position).getPhone()); //Optional parameters
                    myIntent.putExtra("location", list.get(position).getLocation()); //Optional parameters
                    myIntent.putExtra("explain", list.get(position).getExplain());
                   context.startActivity(myIntent);
                }
            });
        }
    }
}