package com.example.laundryin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundryin.Common.Common;
import com.example.laundryin.Database.Database;
import com.example.laundryin.Model.Pesan;
import com.example.laundryin.Model.Request;
import com.example.laundryin.ViewHolder.KeranjangAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Keranjang extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalHarga;
    Button btnBayar;

    List<Pesan> keranjang = new ArrayList<>();

    KeranjangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listKeranjang);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalHarga = findViewById(R.id.total);
        btnBayar = findViewById(R.id.btnBayar);

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlrtDialog();
            }
        });
        loadListKatalog();
    }

    private void showAlrtDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Keranjang.this);
        alertDialog.setTitle("Pesanan Akan Segera Di Proses");
        alertDialog.setMessage("Masukkan Alamat Anda");

        final EditText editAlamat = new EditText(Keranjang.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        editAlamat.setLayoutParams(lp);
        alertDialog.setView(editAlamat);

        alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        editAlamat.getText().toString(),
                        txtTotalHarga.getText().toString(),
                        keranjang
                );

                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                new Database(getBaseContext()).cleanKeranjang();
                Toast.makeText(Keranjang.this, "Terima Kasih Sudah Mencuci di Laundry.in Ajah", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadListKatalog() {
        keranjang = new Database(this).getKeranjang();
        adapter = new KeranjangAdapter(keranjang, this);
        recyclerView.setAdapter(adapter);

        int total = 0;
        for (Pesan pesan:keranjang) {
            try {
                total = (Integer.parseInt(pesan.getHarga()))*(Integer.parseInt(pesan.getQuantity()));
            } catch (Exception ex) {

            }
        }

        Locale locale = new Locale("ind", "INDONESIA");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        txtTotalHarga.setText(numberFormat.format(total));
    }

}
