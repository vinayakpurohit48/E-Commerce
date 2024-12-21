package com.example.pcbazaar.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pcbazaar.Models.UserModel;
import com.example.pcbazaar.R;
import com.example.pcbazaar.Utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProfileFragment extends Fragment {

    EditText edMobileNumber,edEmail,edName,edAddress;
    UserModel user;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        edMobileNumber = view.findViewById(R.id.edMobileNumber);
        edName = view.findViewById(R.id.edName);
        edEmail = view.findViewById(R.id.edEmail);
        edAddress = view.findViewById(R.id.edAddress);

        loadUserDetails();
        setEditTextEnabled(false);

        if (user.getAddress().equals("") || user.getName().equals("") || user.getEmail().equals("") || user.getMobileNumber().equals("")) {
            setEditTextEnabled(true);
            if (user.getAddress().equals(""))
                edAddress.setError("Please Enter The Detail");
            if (user.getName().equals(""))
                edName.setError("Please Enter The Detail");
            if (user.getEmail().equals(""))
                edEmail.setError("Please Enter The Detail");
            if (user.getMobileNumber().equals(""))
                edMobileNumber.setError("Please Enter The Field");
        }


        return view;
    }

    void setInProgress(boolean inProgress){
        if (inProgress){
            setEditTextEnabled(false);
        } else {
            setEditTextEnabled(true);
        }
    }
    void setEditTextEnabled(Boolean iseditTextEnabled){
        if (iseditTextEnabled){
            edMobileNumber.setEnabled(true);
            edName.setEnabled(true);
            edAddress.setEnabled(true);
            edEmail.setEnabled(true);
        } else {
            edMobileNumber.setEnabled(false);
            edName.setEnabled(false);
            edAddress.setEnabled(false);
            edEmail.setEnabled(false);
        }
    }

    void loadUserDetails(){
        setInProgress(true);
        FirebaseUtil.getCurrentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                setEditTextEnabled(false);
                if (task.isSuccessful()){
                    user = task.getResult().toObject(UserModel.class);
                } else {

                }
            }
        });
    }
}