package com.example.pcbazaar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.pcbazaar.Models.ItemDetailModel;
import com.example.pcbazaar.Utils.FirebaseUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class ItemDetailActivity extends AppCompatActivity {
    ImageView imageBackButton, imageAddToFavorite, imageShareButton, itemImage;
    TextView tvItemTitle, tvItemPrice, tvRating, tvComment, tvDescription;
    Button btnAddToCart;
    ItemDetailModel itemDetailModel;
    boolean isItemInCart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageBackButton = findViewById(R.id.imageBackButton);
        imageAddToFavorite = findViewById(R.id.imageAddToFavorite);
        imageShareButton = findViewById(R.id.imageShareButton);
        itemImage = findViewById(R.id.itemImage);
        tvItemTitle = findViewById(R.id.tvItemTitle);
        tvItemPrice = findViewById(R.id.tvItemPrice);
        tvRating = findViewById(R.id.tvRating);
        tvComment = findViewById(R.id.tvComment);
        tvDescription = findViewById(R.id.tvDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        
        if (getIntent() == null){
            onBackPressed();
            Toast.makeText(this, "Something getting wrong", Toast.LENGTH_SHORT).show();
        }

        itemDetailModel = new ItemDetailModel(getIntent().getStringExtra("itemId"),getIntent().getStringExtra("itemName"),getIntent().getStringExtra("itemDescription"), getIntent().getStringExtra("itemRating"), getIntent().getStringExtra("itemPrice"), getIntent().getStringExtra("itemComments"), getIntent().getStringExtra("imageUrl"));
        tvItemTitle.setText(itemDetailModel.getItemName());
        tvItemPrice.setText(itemDetailModel.getItemPrice());
        tvRating.setText(itemDetailModel.getItemRating());
        tvComment.setText(itemDetailModel.getItemComments());
        tvDescription.setText(itemDetailModel.getItemDescription());
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("imageUrl")).into(itemImage);



        imageBackButton.setOnClickListener(v -> onBackPressed());

        isItemInCart();

        btnAddToCart.setOnClickListener(v -> {
            if (!isItemInCart){
                FirebaseUtil.getSavedItems().document(itemDetailModel.getItemId()).set(itemDetailModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ItemDetailActivity.this, "Data Saved successfully", Toast.LENGTH_SHORT).show();
                        isItemInCart();
                    }
                });
            } else {
                FirebaseUtil.getSavedItems().document(itemDetailModel.getItemId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ItemDetailActivity.this, "Removed Successfully", Toast.LENGTH_SHORT).show();
                        isItemInCart();
                    }
                });
            }
        });
    }
    boolean isItemInCart(){
        FirebaseUtil.getSavedItems().document(itemDetailModel.getItemId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.exists()){
                    isItemInCart = false;
                    btnAddToCart.setText("Add To Cart");
                } else {
                    isItemInCart = true;
                    btnAddToCart.setText("Remove From Cart");
                }
            }
        });
        return isItemInCart;
    }
}