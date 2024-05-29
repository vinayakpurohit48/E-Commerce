package com.example.pcbazaar.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtil {
    public static String getCurrentUserUID(){
        return FirebaseAuth.getInstance().getUid();
    }
    public static DocumentReference getCurrentUserDetails(){
        return FirebaseFirestore.getInstance().collection("Users").document(getCurrentUserUID());
    }

    public static CollectionReference getItems(){
         return FirebaseFirestore.getInstance().collection("Items");
    }

    public static StorageReference getTrandingItems(){
        return FirebaseStorage.getInstance().getReference().child("Treanding Items");
    }

    public static CollectionReference getSavedItems(){
        return FirebaseFirestore.getInstance().collection("Users").document("Interacted Items").collection("Saved Items");
    }

    public static CollectionReference getShoppedItems(){
        return FirebaseFirestore.getInstance().collection("Users").document("Interacted Items").collection("Shopped Items");
    }
}
