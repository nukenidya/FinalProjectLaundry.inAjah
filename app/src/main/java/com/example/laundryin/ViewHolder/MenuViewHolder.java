package com.example.laundryin.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundryin.Interface.ItemClickListener;
import com.example.laundryin.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtKatalogName;
    public ImageView imageView;
    private ItemClickListener itemClickListener;
    private View view;

    public MenuViewHolder (View itemView) {
        super(itemView);
        txtKatalogName = itemView.findViewById(R.id.katalog_name);
        imageView = itemView.findViewById(R.id.katalog_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
