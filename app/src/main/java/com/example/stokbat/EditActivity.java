package com.example.stokbat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditActivity extends AppCompatActivity {

    private EditText editTextNama;
    private EditText editTextId;
    private EditText editTextKategori;
    private EditText editTextStok;
    private EditText editTextDesc;
    private DatabaseReference obatRef;
    // Deklarasi variabel NodeKey dari data yang ingin diubah
    private String nodeKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        editTextNama = findViewById(R.id.editTextNama);
        editTextId = findViewById(R.id.editTextId);
        editTextKategori = findViewById(R.id.editTextKategori);
        editTextStok = findViewById(R.id.editTextStok);
        editTextDesc = findViewById(R.id.editTextDesc);

        Obat obat = getIntent().getParcelableExtra("Obat");
        editTextNama.setText(obat.getNama());
        editTextId.setText(obat.getProdukID());
        editTextKategori.setText(obat.getKategori());
        editTextStok.setText(String.valueOf(obat.getStok()));
        editTextDesc.setText(obat.getDeskripsi());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        obatRef = database.getReference("obat");

        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedNama = editTextNama.getText().toString();
                String updatedID = editTextId.getText().toString();
                String updatedKategori = editTextKategori.getText().toString();
                int updatedStok = Integer.parseInt(editTextStok.getText().toString());
                String updatedDesc = editTextDesc.getText().toString();

                Obat updatedObat = new Obat(updatedNama, updatedID, updatedKategori, updatedDesc, updatedStok);

                Query query = obatRef.orderByChild("produkID").equalTo(obat.getProdukID());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Menyimpan nodeKey ke variabel instance
                            nodeKey = snapshot.getKey();
                        }

                        // Memastikan nodeKey tidak null sebelum melakukan update
                        if (nodeKey != null) {
                            obatRef.child(nodeKey).setValue(updatedObat)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(EditActivity.this, "Perubahan Disimpan", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EditActivity.this, "Gagal Menyimpan Perubahan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Handle jika nodeKey null (tidak ditemukan)
                            Toast.makeText(EditActivity.this, "NodeKey tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Firebase", "Error: " + databaseError.getMessage());
                    }
                });
            }
        });
    }
}
