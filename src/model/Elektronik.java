package model;

public class Elektronik extends Barang implements Kategori {
    private String model;
    private JenisElektronik jenis;

    public Elektronik(String id, String merk, int stok, double harga, String model, JenisElektronik jenis) {
        super(id, merk, stok, harga);
        this.model = model;
        this.jenis = jenis;
    }

    public JenisElektronik getJenis() {
        return jenis;
    }

    public String getModel() {
        return model;
    }

    public void setJenis(JenisElektronik jenis) {
        this.jenis = jenis;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String getKategori() {
        return "Elektronik-" + jenis.getDeskripsi();
    }

    @Override
    public String toString() {
        return super.toString() + " | " +
               model + " | " +
               jenis;
    }

}
