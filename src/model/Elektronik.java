package model;

public class Elektronik extends Barang implements Kategori {
    private JenisElektronik jenis;

    public Elektronik(String id, String merk, int jumlah, double harga, JenisElektronik jenis) {
        super(id, merk, jumlah, harga);
        this.jenis = jenis;
    }

    @Override
    public String getKategori() {
        return "Elektronik - " + jenis.getDeskripsi();
    }

    @Override
    public String toString() {
        return super.toString() + " | " +
               jenis;
    }

}
