package com.example.stokbat;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Adapter extends FirebaseRecyclerAdapter<Obat, Adapter.ObatViewHolder> {

//    private AdapterView.OnItemClickListener listener;
    private OnItemClickListener listener;

    public Adapter(@NonNull FirebaseRecyclerOptions<Obat> options) {
        super(options);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ObatViewHolder holder, int position, @NonNull Obat model) {
        holder.Nama.setText(": " + model.getNama());
        holder.Kategori.setText(": " + model.getKategori());
        holder.Stok.setText(": " + model.getStok());

        holder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onViewClick(adapterPosition);
                }
            }
        });
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onEditClick(adapterPosition);
                }
            }
        });
    }


    @NonNull
    @Override
    public ObatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.obat, parent, false);
        return new ObatViewHolder(itemView);
    }

    public class ObatViewHolder extends RecyclerView.ViewHolder {
        public TextView Nama;
        public TextView Kategori;
        public TextView Stok;
        public Button buttonView;
        public Button buttonEdit;

        public ObatViewHolder(View itemView) {
            super(itemView);
            Nama = itemView.findViewById(R.id.namaobat);
            Kategori = itemView.findViewById(R.id.jenisobat);
            Stok = itemView.findViewById(R.id.stokobat);
            buttonView = itemView.findViewById(R.id.buttonView);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
        }
    }

    public interface OnItemClickListener {
        void onViewClick(int position);
        void onEditClick(int position);
    }
}
