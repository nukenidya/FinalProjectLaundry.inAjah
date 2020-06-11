package com.example.laundryin.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.laundryin.Interface.ItemClickListener;
import com.example.laundryin.Model.Pesan;
import com.example.laundryin.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class KeranjangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_keranjang_name, txt_harga;
    public ImageView img_keranjang_count;

    public ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public KeranjangViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_keranjang_name = itemView.findViewById(R.id.keranjang_item_name);
        txt_harga = itemView.findViewById(R.id.keranjang_item_harga);
        img_keranjang_count = itemView.findViewById(R.id.keranjang_item_count);
    }

    @Override
    public void onClick(View v) {

    }
}

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangViewHolder> {

    private List<Pesan> listData = new ArrayList<>();
    private Context context;

    public KeranjangAdapter(List<Pesan> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public KeranjangViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.keranjang_layout, parent, false);
        return new KeranjangViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder (@NonNull KeranjangViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.img_keranjang_count.setImageDrawable(drawable);

        Locale locale = new Locale("ind", "INDONESIA");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        int harga = 0;
        try {
            harga = (Integer.parseInt(listData.get(position).getHarga()))*(Integer.parseInt((listData.get(position).getQuantity())));
        } catch (Exception ex) {

        }
        holder.txt_harga.setText(numberFormat.format(harga));
        holder.txt_keranjang_name.setText(listData.get(position).getProdukName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

}
