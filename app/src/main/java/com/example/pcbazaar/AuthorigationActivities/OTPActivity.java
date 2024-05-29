package com.example.pcbazaar.AuthorigationActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pcbazaar.MainActivity;
import com.example.pcbazaar.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    String verificationID, mobileNumber;
    EditText edOTP;
    TextView tvResendTimer;
    Button btnSubmit;
    ProgressBar progressBar;
    PhoneAuthProvider.ForceResendingToken resendingToken;

    long resendCodeTimer = 30L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edOTP = findViewById(R.id.edOTP);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar);
        tvResendTimer = findViewById(R.id.tvresendOTP);

        setInProgress(false);
        resendingTimer();

        Intent intent = getIntent();

        if (intent != null) {
            verificationID = intent.getStringExtra("VerificationID");
            mobileNumber = intent.getStringExtra("MobileNumber");
        }

        btnSubmit.setOnClickListener(v -> {
            String otp = edOTP.getText().toString().trim();
            if (!otp.isEmpty()) {
                setInProgress(true);
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, otp);
                signInWithCredential(credential);
            } else {
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            }
        });

        tvResendTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP(true);
                resendingTimer();
            }
        });

    }

    void signInWithCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(OTPActivity.this, "Successfully verified", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OTPActivity.this, BasicUserDetailsActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    setInProgress(false);
                    Toast.makeText(OTPActivity.this, "Failed to verify: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("OTP Verification", e.getMessage());
                });
    }

    void setInProgress(boolean inProgress) {
        progressBar.setVisibility(inProgress ? View.VISIBLE : View.GONE);
        btnSubmit.setVisibility(inProgress ? View.GONE : View.VISIBLE);
    }

    void resendingTimer(){
        tvResendTimer.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                resendCodeTimer -= 1;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resendCodeTimer > 0) {
                            tvResendTimer.setText("Resend code in " + resendCodeTimer + " Seconds");
                        } else {
                            tvResendTimer.setText("Resend code");
                            tvResendTimer.setEnabled(true);
                            timer.cancel();
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    void resendOTP(boolean isResended){
        PhoneAuthOptions.Builder builder = new PhoneAuthOptions.Builder(FirebaseAuth.getInstance())
                .setActivity(this)
                .setPhoneNumber(mobileNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(OTPActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationID = s;
                        resendingToken = forceResendingToken;
                        Toast.makeText(OTPActivity.this, "OTP Resended Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
        if (isResended)
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        else
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
    }

}
