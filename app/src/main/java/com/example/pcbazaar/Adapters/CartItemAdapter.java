package com.example.pcbazaar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pcbazaar.ItemDetailActivity;
import com.example.pcbazaar.Models.ItemDetailModel;
import com.example.pcbazaar.R;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    Context context;
    List<ItemDetailModel> itemList;
    int total = 0;
    OnTotalChangedListener onTotalChangedListener;

    public CartItemAdapter(Context context, List<ItemDetailModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setOnTotalChangedListener(OnTotalChangedListener onTotalChangedListener) {
        this.onTotalChangedListener = onTotalChangedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        updateSubTotal();
        holder.itemPrice = Integer.parseInt(itemList.get(position).getItemPrice());
        holder.tvItemName.setText(itemList.get(position).getItemName());
        holder.tvItemPrice.setText(String.valueOf(holder.itemPrice));
        holder.tvItemCountedPrice.setText(String.valueOf(holder.itemPrice * holder.itemCounter));
        holder.tvItemCount.setText(String.valueOf(holder.itemCounter));
        Glide.with(context)
                .load(itemList.get(position).getItemImageLink())
                .into(holder.itemImageView);

        holder.tvBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.itemCounter<10){
                    holder.itemCounter++;
                    holder.tvItemCount.setText(String.valueOf(holder.itemCounter));
                    holder.tvItemCountedPrice.setText(String.valueOf(holder.itemPrice * holder.itemCounter));
                    itemList.get(position).setItemPrice(String.valueOf(holder.itemPrice * holder.itemCounter));
                    updateSubTotal();
                }
            }
        });
        holder.tvBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.itemCounter > 1) {
                    holder.itemCounter--;
                    holder.tvItemCount.setText(String.valueOf(holder.itemCounter));
                    holder.tvItemCountedPrice.setText(String.valueOf(holder.itemPrice * holder.itemCounter));
                    itemList.get(position).setItemPrice(String.valueOf(holder.itemPrice * holder.itemCounter));
                    updateSubTotal();
                }
            }
        });

        holder.itemView.setOnClickListener(v -> {
            int id = holder.getBindingAdapterPosition();
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra("itemName",itemList.get(id).getItemName());
            intent.putExtra("itemPrice",itemList.get(id).getItemPrice());
            intent.putExtra("itemDescription",itemList.get(id).getItemDescription());
            intent.putExtra("itemRating",itemList.get(id).getItemRating());
            intent.putExtra("itemComments",itemList.get(id).getItemComments());
            intent.putExtra("imageUrl",itemList.get(id).getItemImageLink());
            intent.putExtra("itemId",itemList.get(id).getItemId());
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView tvItemName;
        TextView tvItemPrice;
        TextView tvItemCountedPrice;
        TextView tvBtnAdd;
        TextView tvItemCount;
        TextView tvBtnRemove;
        int itemCounter = 1;
        int itemPrice = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvItemCountedPrice = itemView.findViewById(R.id.tvItemCountedPrice);
            tvBtnAdd = itemView.findViewById(R.id.tvBtnAdd);
            tvItemCount = itemView.findViewById(R.id.tvItemCount);
            tvBtnRemove = itemView.findViewById(R.id.tvBtnRemove);
        }
    }

    void updateSubTotal() {
        int total = 0;
        for (ItemDetailModel item : itemList) {
            try {
                total += Integer.parseInt(item.getItemPrice());
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Invalid item price: " + item.getItemPrice(), Toast.LENGTH_SHORT).show();
            }
        }
        if (onTotalChangedListener != null) {
            onTotalChangedListener.OnTotalChange(total);
        } else {
            // Log or handle the case where the listener is not set
            Toast.makeText(context, "TotalChangedListener is not set", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnTotalChangedListener {
        void OnTotalChange(int total);
    }
}

