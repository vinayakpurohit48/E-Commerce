package com.example.pcbazaar.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.pcbazaar.Adapters.HorizontalItemsRecyclerAdapter;
import com.example.pcbazaar.Models.ItemDetailModel;
import com.example.pcbazaar.Models.offerItemDetailModel;
import com.example.pcbazaar.R;
import com.example.pcbazaar.Utils.FirebaseUtil;
import com.facebook.shimmer.ShimmerFrameLayout;
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
    ShimmerFrameLayout moreProductsShimmerLayout, watchedItemsShimmerLayout, popularItemsShimmerLayout;

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
        popularItemsShimmerLayout = view.findViewById(R.id.popularItemsShimmerLayout);
        watchedItemsShimmerLayout = view.findViewById(R.id.watchedItemsShimmerLayout);
        moreProductsShimmerLayout = view.findViewById(R.id.moreProductsShimmerLayout);

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
        setInProgress(true);
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
                        setInProgress(false);
                        horizontalItemsRecyclerAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), "Network Error !!", Toast.LENGTH_SHORT).show();
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

    void setInProgress(Boolean isVisible){
        if (isVisible){
            popularItemsShimmerLayout.setVisibility(View.VISIBLE);
            moreProductsShimmerLayout.setVisibility(View.VISIBLE);
            watchedItemsShimmerLayout.setVisibility(View.VISIBLE);
            popularItemsRecyclerView.setVisibility(View.GONE);
            moreProductsRecyclerView.setVisibility(View.GONE);
            watchedItemsRecyclerView.setVisibility(View.GONE);
        } else {
            popularItemsShimmerLayout.setVisibility(View.GONE);
            moreProductsShimmerLayout.setVisibility(View.GONE);
            watchedItemsShimmerLayout.setVisibility(View.GONE);
            popularItemsRecyclerView.setVisibility(View.VISIBLE);
            moreProductsRecyclerView.setVisibility(View.VISIBLE);
            watchedItemsRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
