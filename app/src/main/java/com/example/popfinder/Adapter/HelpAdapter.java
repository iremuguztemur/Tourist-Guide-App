package com.example.popfinder.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popfinder.Model.AddHelpModel;
import com.example.popfinder.R;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder> {
    private Context context;
    private List<AddHelpModel> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public HelpAdapter(Context context, List<AddHelpModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.row_help, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.answer.setText(list.get(position).getAnswer());
        holder.location.setText(list.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, answer, location;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById( R.id.guide_name);
            answer = itemView.findViewById( R.id.guide_location);
            location = itemView.findViewById( R.id.answer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}