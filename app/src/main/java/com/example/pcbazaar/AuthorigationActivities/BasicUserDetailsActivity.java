package com.example.pcbazaar.AuthorigationActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pcbazaar.MainActivity;
import com.example.pcbazaar.Models.UserModel;
import com.example.pcbazaar.R;
import com.example.pcbazaar.Utils.AndroidUtil;
import com.example.pcbazaar.Utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class BasicUserDetailsActivity extends AppCompatActivity {

    EditText edName;
    Button btnSubmit;
    ProgressBar progressBar;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basic_user_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar);
        edName = findViewById(R.id.edName);

        loadName();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edName.getText().toString().length() > 3){
                    setUserDetails();
                }
            }
        });
    }

    void setUserDetails() {
        setInProgress(true);
        if (userModel==null){
            userModel = new UserModel();
            userModel.setName(edName.getText().toString());
        } else
            userModel.setName(edName.getText().toString());
        FirebaseUtil.getCurrentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    setInProgress(false);
                    AndroidUtil.setPreferencesbyModel(getApplicationContext(),userModel);
                    Toast.makeText(BasicUserDetailsActivity.this, "Data Updated successfully", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("prefRegistration",MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean("isRegistered",true);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(BasicUserDetailsActivity.this, "Something getting wrong", Toast.LENGTH_SHORT).show();
                    setInProgress(false);
                }
            }
        });
    }
    void loadName(){
        setInProgress(true);
        FirebaseUtil.getCurrentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    userModel = task.getResult().toObject(UserModel.class);
                    if(userModel != null)
                        edName.setText(userModel.getName());
                }
                setInProgress(false);
            }
        });
    }
    void setInProgress(boolean inProgress) {
        progressBar.setVisibility(inProgress ? View.VISIBLE : View.GONE);
        btnSubmit.setVisibility(inProgress ? View.GONE : View.VISIBLE);
        edName.setEnabled(!inProgress);
    }
}