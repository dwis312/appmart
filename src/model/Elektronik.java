package model;

public class Elektronik extends Barang implements Kategori {
    private String merk;
    private JenisElektronik jenis;

    public Elektronik(String id, int jumlah, double harga, String merk, JenisElektronik jenis) {
        super(id, jumlah, harga);
        this.merk = merk;
        this.jenis = jenis;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    @Override
    public String getKategori() {
        return "Elektronik" + jenis.getDeskripsi();
    }

    @Override
    public String toString() {
        return super.toString() + " | " +
               merk + " | " +
               jenis;
    }

}
