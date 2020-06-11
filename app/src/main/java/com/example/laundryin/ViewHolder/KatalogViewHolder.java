package com.example.laundryin.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.laundryin.Interface.ItemClickListener;
import com.example.laundryin.R;

public class KatalogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView katalog_item_name;
    public ImageView katalog_item_image;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public KatalogViewHolder (@NonNull View itemView) {
        super(itemView);

        katalog_item_name = itemView.findViewById(R.id.katalog_item_name);
        katalog_item_image = itemView.findViewById(R.id.katalog_item_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
