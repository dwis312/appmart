package model;

public abstract class Barang {
    private String id;
    private String merk;
    private int stok;
    private double harga;

    public Barang(String id, String merk, int stok, double harga) {
        this.id = id;
        this.merk = merk;
        this.stok = stok;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public String getMerk() {
        return merk;
    }

    public int getStok() {
        return stok;
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

    public void setStok(int stok) {
        this.stok = stok;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public abstract String getKategori();
    
    @Override
    public String toString() {
        return id + " | " +
               stok + " | " +
               harga;
    }

}
