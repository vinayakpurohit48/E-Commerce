package com.example.pcbazaar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pcbazaar.AuthorigationActivities.MobileNumberActivity;
import com.example.pcbazaar.Fragments.CartFragment;
import com.example.pcbazaar.Fragments.HomeFragment;
import com.example.pcbazaar.Fragments.ProfileFragment;
import com.example.pcbazaar.Fragments.QrCodeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (FirebaseAuth.getInstance().getUid() == null){
            Intent intent = new Intent(getApplicationContext(), MobileNumberActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return;
        }
        SharedPreferences preferences = getSharedPreferences("prefRegistration",MODE_PRIVATE);
        if (preferences.getBoolean("isRegistered",false)){
            Intent intent = new Intent(getApplicationContext(), MobileNumberActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return;
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,new HomeFragment());
        fragmentTransaction.commit();
        
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.item_home){
                    loadFragment(new HomeFragment());
                } else if (id == R.id.item_whishlist) {

                } else if (id == R.id.item_qr_code) {
                    loadFragment(new QrCodeFragment());
                } else if (id == R.id.item_cart){
                    loadFragment(new CartFragment());
                }  else if (id == R.id.item_profile) {
                    loadFragment(new ProfileFragment());
                }
                return true;
            }
        });

    }

    void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
}