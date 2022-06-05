package com.example.popfinder.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.popfinder.databinding.FragmentAddHelpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddHelpFragment extends Fragment {

    private EditText editName, editMessage;
    private Button btnSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";
    private FragmentAddHelpBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddHelpBinding.inflate(inflater, container, false);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Soru Ekle");

        editName = binding.name;
        editMessage = binding.message;
        btnSave = binding.btnSave;

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("kaydet...");

        btnSave.setOnClickListener(v -> {
            if (editName.getText().length() > 0 && editMessage.getText().length() > 0) {
                saveData(editName.getText().toString(), editMessage.getText().toString());
            } else {
                Toast.makeText(requireContext(), "Lütfen tüm verileri doldurun!", Toast.LENGTH_SHORT).show();
            }
        });

        if (getArguments() != null) {
            id = getArguments().getString("id");
            editName.setText(getArguments().getString("name"));
            editMessage.setText(getArguments().getString("message"));
        }

        return binding.getRoot();
    }

    private void saveData(String name, String message) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("location", message);
        user.put("answer", " ");
        user.put("isActive", false);

        progressDialog.show();
        if (!id.isEmpty()) {
            db.collection("questions").document(id)
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
            db.collection("questions")
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
}