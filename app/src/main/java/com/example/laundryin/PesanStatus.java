package com.example.laundryin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.laundryin.Common.Common;
import com.example.laundryin.Model.Request;
import com.example.laundryin.ViewHolder.PesanViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PesanStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, PesanViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_status);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listPesan);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadPesan (Common.currentUser.getPhone());
    }

    private void loadPesan(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, PesanViewHolder>(
                Request.class,
                R.layout.pesan_layout,
                PesanViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(PesanViewHolder viewHolder, Request model, int position) {
                viewHolder.txtPesanId.setText(adapter.getRef(position).getKey());
                viewHolder.txtPesanStatus.setText(convertCodeToString(model.getStatus()));
                viewHolder.txtPesanAlamat.setText(model.getAlamat());
                viewHolder.txtPesanPhone.setText(model.getPhone());
            }
        };
        recyclerView.setAdapter(adapter);
    }
    private String convertCodeToString(String status) {
        if(status.equals("0"))
            return "Diterima";
        else if (status.equals("1"))
            return "Dalam Perjalanan";
        else
            return "Pengemasan";

    };
}
