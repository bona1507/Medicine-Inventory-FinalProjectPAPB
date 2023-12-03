package com.example.stokbat;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        // Mendapatkan data obat yang dikirim dari MainActivity
        Obat obat = getIntent().getParcelableExtra("Obat");

        // Menampilkan data obat pada tampilan detail
        displayObatDetail(obat);
    }

    // Menampilkan informasi obat pada tampilan detail
    private void displayObatDetail(Obat obat) {
        TextView namaObatTextView = findViewById(R.id.nama);
        TextView idObatTextView = findViewById(R.id.produkID);
        TextView kategoriObatTextView = findViewById(R.id.kategori);
        TextView deskripsiObatTextView = findViewById(R.id.desc);
        TextView stokObatTextView = findViewById(R.id.stok);

        if (obat != null) {
            namaObatTextView.setText(obat.getNama());
            idObatTextView.setText("ID: " + obat.getProdukID());
            kategoriObatTextView.setText("Kategori: " + obat.getKategori());
            deskripsiObatTextView.setText("Deskripsi: " + obat.getDeskripsi());
            stokObatTextView.setText("Stok: " + obat.getStok());
        }
    }
}