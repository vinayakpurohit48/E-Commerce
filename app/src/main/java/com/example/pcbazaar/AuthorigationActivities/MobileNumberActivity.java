package com.example.pcbazaar.AuthorigationActivities;

import android.content.Intent;
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

import com.example.pcbazaar.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MobileNumberActivity extends AppCompatActivity {

    EditText edMobileNumber;
    Button btnSendOTP;
    ProgressBar progressBar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mobile_number);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edMobileNumber = findViewById(R.id.edMobileNumber);
        btnSendOTP = findViewById(R.id.btn_sendOTP);
        progressBar = findViewById(R.id.progress_bar);
        countryCodePicker = findViewById(R.id.countryCodePicker);

        setInProgress(false);
        edMobileNumber.requestFocus();
        edMobileNumber.setError("");

        btnSendOTP.setOnClickListener(v -> {
            countryCodePicker.registerCarrierNumberEditText(edMobileNumber);
            if (countryCodePicker.isValidFullNumber()){
                setInProgress(true);
                sendOTP(countryCodePicker.getFullNumberWithPlus());
            } else {
                edMobileNumber.setError("Invalid Mobile Number");
                edMobileNumber.requestFocus();
            }
        });

    }

    void sendOTP(String mobilenumber){
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setActivity(this)
                .setPhoneNumber(mobilenumber)
                .setTimeout(60L,TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        setInProgress(false);
                        Toast.makeText(MobileNumberActivity.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("Verification Failed", e.toString());
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        // Save verification ID and start OTPActivity
                        Intent intent = new Intent(MobileNumberActivity.this, OTPActivity.class);
                        intent.putExtra("MobileNumber", mobilenumber);
                        intent.putExtra("VerificationID", s);
                        startActivity(intent);
                        setInProgress(false);
                    }
                })
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    void setInProgress(boolean inProgress){
        progressBar.setVisibility(inProgress ? View.VISIBLE : View.GONE);
        btnSendOTP.setVisibility(inProgress ? View.GONE : View.VISIBLE);
    }
}
