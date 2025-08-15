package model;

public class Pakaian extends Barang implements Kategori {
    private JenisSize size;
    private JenisPakaian jenis;

    public Pakaian(String id, String merk, int stok, double harga, JenisSize size, JenisPakaian jenis) {
        super(id, merk, stok, harga);
        this.size = size;
        this.jenis = jenis;
    }

    public JenisSize getSize() {
        return size;
    }

    public void setSize(JenisSize size) {
        this.size = size;
    }

    public JenisPakaian getJenis() {
        return jenis;
    }

    public void setJenis(JenisPakaian jenis) {
        this.jenis = jenis;
    }

    @Override
    public String getKategori() {
        return "Pakaian-" + jenis.getDeskripsi();
    }

    @Override
    public String toString() {
        return super.toString() + " | " +
               size + " | " +
               jenis;
    }

}
