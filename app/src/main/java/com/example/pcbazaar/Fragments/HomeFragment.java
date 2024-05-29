package com.example.pcbazaar.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.pcbazaar.Adapters.HorizontalItemsRecyclerAdapter;
import com.example.pcbazaar.Models.ItemDetailModel;
import com.example.pcbazaar.Models.offerItemDetailModel;
import com.example.pcbazaar.R;
import com.example.pcbazaar.Utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView tvShowUserName;
    EditText edSearchItems;
    RecyclerView popularItemsRecyclerView, watchedItemsRecyclerView, moreProductsRecyclerView;
    ViewFlipper mainViewFlipper;
    List<ItemDetailModel> itemList = new ArrayList<>();
    List<offerItemDetailModel> bannerList = new ArrayList<>();
    HorizontalItemsRecyclerAdapter horizontalItemsRecyclerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvShowUserName = view.findViewById(R.id.tvShowUserName);
        edSearchItems = view.findViewById(R.id.edSearchItems);
        mainViewFlipper = view.findViewById(R.id.mainViewFlipper);
        popularItemsRecyclerView = view.findViewById(R.id.popularItemsRecyclerView);
        watchedItemsRecyclerView = view.findViewById(R.id.watchedItemsRecyclerView);
        moreProductsRecyclerView = view.findViewById(R.id.moreProductsRecyclerView);

        horizontalItemsRecyclerAdapter = new HorizontalItemsRecyclerAdapter(getContext(), itemList);
        popularItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularItemsRecyclerView.setAdapter(horizontalItemsRecyclerAdapter);

        watchedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        watchedItemsRecyclerView.setAdapter(horizontalItemsRecyclerAdapter);

        moreProductsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        moreProductsRecyclerView.setAdapter(horizontalItemsRecyclerAdapter);

        if (itemList.isEmpty())
            setTrendingItems();
        setupBannerItems(inflater);

        return view;
    }

    void setTrendingItems() {
        FirebaseUtil.getItems().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snap = task.getResult();
                    if (snap != null) {
                        for (DocumentSnapshot document : snap.getDocuments()) {
                            ItemDetailModel item = document.toObject(ItemDetailModel.class);
                            if (item != null) {
                                itemList.add(item);
                            }
                        }
                        horizontalItemsRecyclerAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("HomeFragment", "Error getting items: ", task.getException());
                }
            }
        });
    }

    void setupBannerItems(LayoutInflater inflater) {
        bannerList.add(new offerItemDetailModel());
        bannerList.add(new offerItemDetailModel());
        bannerList.add(new offerItemDetailModel());

        int i = 0;
        for (offerItemDetailModel banner : bannerList) {
            i++;
            View bannerView = inflater.inflate(R.layout.flipher_offer_items, mainViewFlipper, false);
            ((TextView) bannerView.findViewById(R.id.tvDiscount)).setText("Offer " + i);
            mainViewFlipper.addView(bannerView);
        }

        mainViewFlipper.startFlipping();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainViewFlipper.stopFlipping();
    }
}
