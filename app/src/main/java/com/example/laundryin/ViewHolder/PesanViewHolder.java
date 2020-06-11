package com.example.laundryin.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundryin.Interface.ItemClickListener;
import com.example.laundryin.R;

public class PesanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtPesanId, txtPesanStatus, txtPesanPhone, txtPesanAlamat;
    private ItemClickListener itemClickListener;

    public PesanViewHolder (@NonNull View itemView) {
        super(itemView);
        txtPesanAlamat = itemView.findViewById(R.id.pesan_alamat);
        txtPesanId = itemView.findViewById(R.id.pesan_id);
        txtPesanStatus = itemView.findViewById(R.id.pesan_status);
        txtPesanPhone = itemView.findViewById(R.id.pesan_phone);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick (View v) {
    }
}
