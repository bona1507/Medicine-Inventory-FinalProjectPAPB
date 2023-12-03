package com.example.stokbat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Mengambil referensi ke Firebase Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("obat");

        // Menggunakan FirebaseRecyclerOptions untuk membaca data dari Firebase Database
        FirebaseRecyclerOptions<Obat> options =
                new FirebaseRecyclerOptions.Builder<Obat>()
                        .setQuery(databaseReference, Obat.class)
                        .build();

        // Inisialisasi Adapter dengan FirebaseRecyclerAdapter
        adapter = new Adapter(options);
        adapter.setOnItemClickListener(this); // Set the click listener

        recyclerView.setAdapter(adapter);

        // Mengambil referensi ke tombol "Tambah Obat"
        Button tambahObatButton = findViewById(R.id.buttonTambah);

        // Mengatur listener untuk tombol "Tambah Obat"
        tambahObatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil method atau buat fungsi untuk menangani logika ketika tombol "Tambah Obat" diklik
                openTambahObatActivity();
            }
        });

    }

    private void openTambahObatActivity() {
        Intent intent = new Intent(this, AddActivity.class); // Ganti TambahObatActivity dengan nama Activity yang sesuai
        startActivity(intent);
    }
    @Override
    public void onViewClick(int position) {
        // Dapatkan obat dari adapter
        Obat obat = adapter.getItem(position);

        // Intent untuk menuju ke DetailObatActivity dan mengirim data obat yang dipilih
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("Obat", obat);
        startActivity(intent);
    }

    @Override
    public void onEditClick(int position) {
        // Dapatkan obat dari adapter
        Obat obat = adapter.getItem(position);

        // Intent untuk menuju ke EditActivity dan mengirim data obat yang dipilih
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("Obat", obat);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Mulai mendeteksi perubahan pada Firebase Database
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Hentikan mendeteksi perubahan pada Firebase Database
        adapter.stopListening();
    }

    protected void onResume() {
        super.onResume();
        // Refresh RecyclerView jika ada perubahan setelah Activity di-resume
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}