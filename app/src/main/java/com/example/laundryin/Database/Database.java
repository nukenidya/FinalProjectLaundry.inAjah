package com.example.laundryin.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.example.laundryin.Model.Pesan;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "LaundryInDB.db";
    private static final int DB_VER = 1;

    public Database (Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Pesan> getKeranjang() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProdukName", "ProdukId", "Quantity", "Harga", "Discount"};
        String sqlTables = "PesanDetail";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<Pesan> result = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                result.add(new Pesan(c.getString(c.getColumnIndex("ProdukId")),
                        c.getString(c.getColumnIndex("ProdukName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Harga")),
                        c.getString(c.getColumnIndex("Discount"))
                ));
            }
            while (c.moveToNext());
        }
        return result;
    }

    public void addToKeranjang (Pesan pesan) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO PesanDetail(ProdukId, ProdukName, Quantity, Harga, Discount) VALUES ('%s','%s','%s','%s','%s');",
                pesan.getProdukId(),
                pesan.getProdukName(),
                pesan.getQuantity(),
                pesan.getHarga(),
                pesan.getDiscount()
        );
        db.execSQL(query);
    }

    public void cleanKeranjang() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM PesanDetail");
        db.execSQL(query);
    }

}
