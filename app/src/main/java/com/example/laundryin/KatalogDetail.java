package com.example.laundryin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.laundryin.Database.Database;
import com.example.laundryin.Model.Katalog;
import com.example.laundryin.Model.Pesan;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class KatalogDetail extends AppCompatActivity {

    TextView katalog_item_name, harga, katalog_description;
    ImageView katalog_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnKeranjang;
    ElegantNumberButton numberButton;

    String itemId="";

    FirebaseDatabase database;
    DatabaseReference katalog;

    Katalog curentKatalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katalog_detail);

        database = FirebaseDatabase.getInstance();
        katalog = database.getReference("Katalog");

        numberButton = findViewById(R.id.number_button);
        btnKeranjang = findViewById(R.id.btnKeranjang);

        btnKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToKeranjang(new Pesan(
                        itemId,
                        curentKatalog.getName(),
                        numberButton.getNumber(),
                        curentKatalog.getPrice(),
                        curentKatalog.getDiscount()
                ));
                Toast.makeText(KatalogDetail.this, "Ditambahkan kedalam Keranjang", Toast.LENGTH_SHORT).show();
            }
        });

        katalog_description = findViewById(R.id.katalog_description);
        katalog_item_name = findViewById(R.id.katalog_item_name);
        harga = findViewById(R.id.harga);
        katalog_image = findViewById(R.id.img_katalog);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandeAdapter);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAdapter);

        if (getIntent() != null)
            itemId = getIntent().getStringExtra("KatalogItemID");
        if (!itemId.isEmpty()) {
            getDetailKatalog(itemId);
        }
    }

    private void getDetailKatalog(final String katalogId) {
        katalog.child(katalogId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                curentKatalog = dataSnapshot.getValue(Katalog.class);
                Picasso.with(getBaseContext()).load(curentKatalog.getImage()).into(katalog_image);

                collapsingToolbarLayout.setTitle(curentKatalog.getName());
                harga.setText(curentKatalog.getPrice());
                katalog_item_name.setText(curentKatalog.getName());
                katalog_description.setText(curentKatalog.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
