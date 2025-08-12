package model;

public abstract class Barang {
    private String id;
    private String merk;
    private int jumlah;
    private double harga;

    public Barang(String id, String merk, int jumlah, double harga) {
        this.id = id;
        this.merk = merk;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public String getMerk() {
        return merk;
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

    public void setMerk(String merk) {
        this.merk = merk;
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
