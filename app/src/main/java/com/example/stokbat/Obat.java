package com.example.stokbat;

import android.os.Parcel;
import android.os.Parcelable;

public class Obat implements Parcelable {

    private String nama;
    private String produkID;
    private String kategori;
    private String deskripsi;
    private int stok;

    public String getNama() {
        return nama;
    }

    public String getProdukID() {
        return produkID;
    }

    public String getKategori() {
        return kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getStok() {
        return stok;
    }

    public Obat() {
        // Konstruktor tanpa argumen diperlukan difirebase (fix execption error)
    }
    public Obat(String nama, String produkID, String kategori, String deskripsi, int stok) {
        this.nama = nama;
        this.produkID = produkID;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.stok = stok;
    }

    protected Obat(Parcel in) {
        nama = in.readString();
        produkID = in.readString();
        kategori = in.readString();
        deskripsi = in.readString();
        stok = in.readInt();
    }

    public static final Creator<Obat> CREATOR = new Creator<Obat>() {
        @Override
        public Obat createFromParcel(Parcel in) {
            return new Obat(in);
        }

        @Override
        public Obat[] newArray(int size) {
            return new Obat[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(produkID);
        dest.writeString(kategori);
        dest.writeString(deskripsi);
        dest.writeInt(stok);
    }

}
