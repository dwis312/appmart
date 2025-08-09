package model;

public class Barang {
    String id;
    String nama;
    int jumlah;
    double harga;

    public Barang(String id, String nama, int jumlah, double harga) {
        this.id = id;
        this.nama = nama;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }
    
    public String getNama() {
        return nama;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getHarga() {
        return harga;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return id + "|" +
               nama + "|" +
               jumlah + "|" +
               harga + "|";
    } 

}
