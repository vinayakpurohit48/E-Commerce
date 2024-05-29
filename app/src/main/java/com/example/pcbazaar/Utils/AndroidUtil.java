package com.example.pcbazaar.Utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.pcbazaar.Models.UserModel;

public class AndroidUtil {
    public static void setPreferencesbyModel(Context ctx, UserModel userModel){
        Log.d("SharedPrefrences","Data come for editing");
        SharedPreferences prefUserDetails = ctx.getSharedPreferences("prefUserDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefUserDetails.edit();
        editor.putString("UserName", userModel.getName());
        editor.putString("Email", userModel.getEmail());
        editor.putString("MobileNumber", userModel.getMobileNumber());
        editor.putString("Address", userModel.getAddress());
        editor.putString("PostalCode", userModel.getPostalCode());
        editor.putString("BirthDate", userModel.getBirthDate());
        editor.apply();
    }
}
