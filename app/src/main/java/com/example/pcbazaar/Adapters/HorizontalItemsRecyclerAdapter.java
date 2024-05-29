package com.example.pcbazaar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class HorizontalItemsRecyclerAdapter extends RecyclerView.Adapter<HorizontalItemsRecyclerAdapter.ViewHolder> {
    Context context;
    List<ItemDetailModel> itemsList;

    public HorizontalItemsRecyclerAdapter(Context context, List<ItemDetailModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvMainItemPrice.setText(itemsList.get(position).getItemPrice());
        holder.tvMainItemTitle.setText(itemsList.get(position).getItemName());
        holder.tvMainItemComment.setText(itemsList.get(position).getItemComments());
        holder.tvMainItemRating.setText(itemsList.get(position).getItemRating());
        Glide.with(context).load(itemsList.get(position).getItemImagelink()).into(holder.mainItemImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = holder.getAdapterPosition();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra("itemName",itemsList.get(id).getItemName());
                intent.putExtra("itemPrice",itemsList.get(id).getItemPrice());
                intent.putExtra("itemDescription",itemsList.get(id).getItemDescription());
                intent.putExtra("itemRating",itemsList.get(id).getItemRating());
                intent.putExtra("itemComments",itemsList.get(id).getItemComments());
                intent.putExtra("imageUrl",itemsList.get(id).getItemImagelink());
                intent.putExtra("itemId",itemsList.get(id).getItemId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mainItemImage;
        TextView tvMainItemRating, tvMainItemTitle, tvMainItemPrice, tvMainItemComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainItemImage = itemView.findViewById(R.id.mainItemImage);
            tvMainItemRating = itemView.findViewById(R.id.tvMainItemRating);
            tvMainItemTitle = itemView.findViewById(R.id.tvMainItemTitle);
            tvMainItemPrice = itemView.findViewById(R.id.tvMainItemPrice);
            tvMainItemComment = itemView.findViewById(R.id.tvMainItemComment);

        }
    }
}
