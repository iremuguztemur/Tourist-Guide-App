package com.example.popfinder.Fragments;

import static com.google.maps.android.Context.getApplicationContext;

import com.example.popfinder.Adapter.GuideAdapter;
import com.example.popfinder.Adapter.GuideDetailAdapter;
import com.example.popfinder.KullaniciDetayActivity;
import com.example.popfinder.Model.AddGuideModel;
import com.example.popfinder.R;
import com.example.popfinder.databinding.FragmentGuideDetailBinding;
import com.example.popfinder.databinding.FragmentShowGuideBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.github.nikartm.button.FitButton;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;


public class GuideDetailFragment extends Fragment {

    public static final String TITLE = "title";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String AGE = "age";
    private FitButton addPerson, savePerson, queryPersonList;
    private View personLayout;
    private FirebaseFirestore firebaseFirestoreDb;
    private EditText userTitle, userName, userSurname, userAge;
    private RecyclerView recyclerView;
    private CollectionReference collectionReference;
    private GuideDetailAdapter adapter;
    private Query query;

    private FragmentGuideDetailBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<AddGuideModel> list = new ArrayList<>();
    private GuideDetailAdapter GuideDetailAdapter;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGuideDetailBinding.inflate(inflater, container, false);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Turist Rehberi Görüntüle");

        recyclerView = binding.recyclerView;

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Data Yükleniyor...");
        GuideDetailAdapter = new GuideDetailAdapter(requireContext(), list);
        GuideDetailAdapter.setDialog(new GuideDetailAdapter.Dialog() {
            @Override
            public void onClick(int pos) {


                // Intent myIntent = new Intent((getActivity()), KullaniciDetayActivity.class);
                //myIntent.putExtra("key", list.get(0).getName()); //Optional parameters
                //getActivity().startActivity(myIntent);

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(GuideDetailAdapter);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {
        progressDialog.show();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getBoolean("isActive")){
                                    AddGuideModel guide = new AddGuideModel(
                                            document.getString("location"),
                                            document.getString("name"),
                                            document.getString("phone"),
                                            document.getString("explain"),
                                            document.getString("profile_photo"));

                                    guide.setId(document.getId());
                                    list.add(guide);
                                }

                            }
                            GuideDetailAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(requireContext(), "Veri alımı başarısız oldu!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void deleteData(String id) {
        progressDialog.show();
        db.collection("users").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Veriler silinemedi!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        getData();
                    }
                });
    }

}