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

import com.example.pcbazaar.R;
import com.example.pcbazaar.Utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProfileFragment extends Fragment {

    EditText edMobileNumber,edEmail,edName,edAddress,edPostalCode;


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
        edPostalCode = view.findViewById(R.id.edPostalCode);
        edAddress = view.findViewById(R.id.edAddress);

        SharedPreferences preferences = getContext().getSharedPreferences("prefUserDetails", Context.MODE_PRIVATE);
        edName.setText(preferences.getString("UserName","null"));

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
            edPostalCode.setEnabled(true);
            edEmail.setEnabled(true);
        } else {
            edMobileNumber.setEnabled(false);
            edName.setEnabled(false);
            edAddress.setEnabled(false);
            edPostalCode.setEnabled(false);
            edEmail.setEnabled(false);
        }
    }
}