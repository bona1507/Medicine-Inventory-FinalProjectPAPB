package com.example.stokbat;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddActivity extends AppCompatActivity {

    private EditText editTextNama;
    private EditText editTextId;
    private EditText editTextKategori;
    private EditText editTextStok;
    private EditText editTextDesc;
    private DatabaseReference obatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        editTextNama = findViewById(R.id.editTextNama);
        editTextId = findViewById(R.id.editTextId);
        editTextKategori = findViewById(R.id.editTextKategori);
        editTextStok = findViewById(R.id.editTextStok);
        editTextDesc = findViewById(R.id.editTextDesc);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Mendapatkan referensi ke node "obat" di dalam database
        obatRef = database.getReference("obat");


        Button addButton = findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil data dari EditText fields
                String nama = editTextNama.getText().toString();
                String produkID = editTextId.getText().toString();
                String kategori = editTextKategori.getText().toString();
                int stok = Integer.parseInt(editTextStok.getText().toString());
                String desc = editTextDesc.getText().toString();

                // Buat objek obat baru
                Obat newObat = new Obat(nama, produkID, kategori, desc, stok);

                // Simpan data obat ke Firebase Database
                obatRef.push().setValue(newObat)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Notifikasi obat berhasil ditambahkan
                                Toast.makeText(AddActivity.this, "Obat Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                                // Kembali ke MainActivity atau halaman sebelumnya
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Penanganan jika gagal menyimpan data ke Firebase Database
                                Toast.makeText(AddActivity.this, "Obat Gagal Ditambahkan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}