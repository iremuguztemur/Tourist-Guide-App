package com.example.popfinder.Fragments;

import com.example.popfinder.Adapter.HelpAdapter;
import com.example.popfinder.Model.AddHelpModel;
import com.example.popfinder.R;
import com.example.popfinder.databinding.FragmentHelpBinding;
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


public class HelpFragment extends Fragment {

    public static final String TITLE = "title";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String AGE = "age";
    public static final String ANSWER = "answer";
    private FitButton addPerson, savePerson, queryPersonList;
    private View personLayout;
    private FirebaseFirestore firebaseFirestoreDb;
    private EditText userTitle, userName, userSurname, userAge;
    private RecyclerView recyclerView;
    private CollectionReference collectionReference;
    private HelpAdapter adapter;
    private Query query;

    private FragmentHelpBinding binding;
    private FloatingActionButton btnAdd;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<AddHelpModel> list = new ArrayList<>();
    private HelpAdapter helpAdapter;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHelpBinding.inflate(inflater, container, false);
        // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Soruları Görüntüle");

        recyclerView = binding.recyclerView;
        btnAdd = binding.btnAdd;

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Data Yükleniyor...");
        helpAdapter = new HelpAdapter(requireContext(), list);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(helpAdapter);

        btnAdd.setOnClickListener(v -> {
            AddHelpFragment fragment = new AddHelpFragment();
            FragmentManager fragmentManager=getFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer,fragment);
            transaction.commit();
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {
        progressDialog.show();
        db.collection("questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getBoolean("isActive")){
                                    AddHelpModel help = new AddHelpModel(document.getString("location"), document.getString("name"), document.getString("answer"));
                                    help.setId(document.getId());
                                    list.add(help);
                                }

                            }
                            helpAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(requireContext(), "Veri alımı başarısız oldu!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void deleteData(String id) {
        progressDialog.show();
        db.collection("questions").document(id)
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