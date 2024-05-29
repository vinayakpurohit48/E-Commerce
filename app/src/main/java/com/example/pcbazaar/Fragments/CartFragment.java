package com.example.pcbazaar.Fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pcbazaar.Adapters.CartItemAdapter;
import com.example.pcbazaar.Models.ItemDetailModel;
import com.example.pcbazaar.R;
import com.example.pcbazaar.Utils.FirebaseUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements CartItemAdapter.OnTotalChangedListener{
    TextView tvEmptyCart, tvTotalCost, tvTaxCost, tvDeliveryCost, tvSubTotal;
    RecyclerView cartItemsRecyclerView;
    Button btnOrderNow;
    CartItemAdapter cartItemAdapter;

    List<ItemDetailModel> itemList = new ArrayList<>();



    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        tvEmptyCart = view.findViewById(R.id.tvEmptyCart);
        tvTotalCost = view.findViewById(R.id.tvTotalCost);
        tvTaxCost = view.findViewById(R.id.tvTaxCost);
        tvDeliveryCost = view.findViewById(R.id.tvDeliveryCost);
        tvSubTotal = view.findViewById(R.id.tvSubTotal);
        btnOrderNow = view.findViewById(R.id.btnOrderNow);
        cartItemsRecyclerView = view.findViewById(R.id.cartItemsRecyclerView);

        cartItemAdapter = new CartItemAdapter(getContext(), itemList);
        fetchCartItems();
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItemsRecyclerView.setAdapter(cartItemAdapter);

        cartItemAdapter.setOnTotalChangedListener(new CartItemAdapter.OnTotalChangedListener() {
            @Override
            public void OnTotalChange(int total) {
                tvSubTotal.setText(String.valueOf(total));
            }
        });


        return view;
    }

    void fetchCartItems() {
        FirebaseUtil.getSavedItems().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots != null) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        ItemDetailModel item = document.toObject(ItemDetailModel.class);
                        if (item != null) {
                            itemList.add(item);
                        }
                    }
                    cartItemAdapter.notifyDataSetChanged();
                    Log.d("In Fragment", "ViewHolder: ItemConut : " + itemList.size());
                } else {
                    Log.e(TAG, "No items found");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error fetching cart items", e);
                Toast.makeText(getContext(), "Error fetching cart items", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void OnTotalChange(int total) {
        Toast.makeText(getContext(), "idar aaya ", Toast.LENGTH_SHORT).show();
        tvSubTotal.setText(String.valueOf(total));
    }
}
