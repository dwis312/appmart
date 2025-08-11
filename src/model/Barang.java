package model;

public abstract class Barang {
    String id;
    int jumlah;
    double harga;

    public Barang(String id, int jumlah, double harga) {
        this.id = id;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public String getId() {
        return id;
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

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public abstract String getKategori();
    
    @Override
    public String toString() {
        return id + " | " +
               jumlah + " | " +
               harga;
    }

}
