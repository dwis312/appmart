package model;

public class Pakaian extends Barang implements Kategori {
    private JenisSize size;
    private JenisPakaian jenis;

    public Pakaian(String id, String merk, int jumlah, double harga, JenisSize size, JenisPakaian jenis) {
        super(id, merk, jumlah, harga);
        this.size = size;
        this.jenis = jenis;
    }

    @Override
    public String getKategori() {
        return "Pakaian" + jenis.getDeskripsi();
    }

    @Override
    public String toString() {
        return super.toString() + " | " +
               size + " | " +
               jenis;
    }

}
