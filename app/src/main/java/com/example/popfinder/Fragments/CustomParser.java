package com.example.popfinder.Fragments;

import androidx.annotation.NonNull;

import com.example.popfinder.Model.AddGuideModel;
import com.firebase.ui.firestore.ClassSnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;

public class CustomParser extends ClassSnapshotParser<AddGuideModel> {


    public CustomParser() {
        super(AddGuideModel.class);
    }

    @NonNull
    @Override
    public AddGuideModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
        AddGuideModel person = super.parseSnapshot(snapshot);
        person.setId(snapshot.getId());
        return person;
    }
}
