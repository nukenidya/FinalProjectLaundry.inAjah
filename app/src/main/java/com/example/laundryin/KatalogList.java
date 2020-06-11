package com.example.laundryin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.laundryin.Interface.ItemClickListener;
import com.example.laundryin.Model.Katalog;
import com.example.laundryin.ViewHolder.KatalogViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class KatalogList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference katalogList;

    String categoryId="";

    FirebaseRecyclerAdapter<Katalog, KatalogViewHolder> adapter;

    FirebaseRecyclerAdapter<Katalog, KatalogViewHolder> searchadapter;
    List<String> suggestList = new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katalog_list);

        database = FirebaseDatabase.getInstance();
        katalogList = database.getReference("Katalog");

        recyclerView = findViewById(R.id.recycler_katalog);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null)
        {
            loadListKatalog(categoryId);
        }

        searchView = findViewById(R.id.searchBar);
        searchView.setQueryHint("Cari Item Katalog");
        loadSuggest();
    }

    private void loadSuggest() {
        katalogList.orderByChild("KatalogID").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                    Katalog item = postSnapshot.getValue(Katalog.class);
                    suggestList.add(item.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadListKatalog(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Katalog, KatalogViewHolder>(Katalog.class, R.layout.katalog_item, KatalogViewHolder.class, katalogList.orderByChild("KatalogID").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(KatalogViewHolder viewHolder, Katalog model, int position) {
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.katalog_item_image);

                final Katalog local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(KatalogList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                        Intent katalogDetail = new Intent(KatalogList.this, KatalogDetail.class);
                        katalogDetail.putExtra("KatalogItemID", adapter.getRef(position).getKey());
                        startActivity(katalogDetail);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
