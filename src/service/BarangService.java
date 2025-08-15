package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Barang;
import model.Elektronik;
import model.JenisElektronik;
import model.JenisPakaian;
import model.JenisSize;
import model.Pakaian;

public class BarangService {
    private ArrayList<Barang> daftarBarang = new ArrayList<>();
    private ArrayList<Pakaian> pakaian = new ArrayList<>();
    private ArrayList<Elektronik> elektronik = new ArrayList<>();

    public String generateId() {
        return "B" + String.format("%03d", daftarBarang.size()+1);
    }

    public Barang cariId(String keyword) {
        for (int i = 0; i < daftarBarang.size(); i++) {
            if (daftarBarang.get(i).getId().equalsIgnoreCase(keyword)) {
                return daftarBarang.get(i);
            }
        }
        return null;
    }

    public List<Barang> getAllData() {
        return daftarBarang;
    }

    public List<Elektronik> getAllElektronik() {
        return elektronik;
    }

    public List<Pakaian> getAllPakaian() {
        return pakaian;
    }

    public void tambahData(Barang barang) {
        daftarBarang.add(barang);
    }

    public void tambahPakaian(List<Pakaian> listPakaian) {
        daftarBarang.addAll(listPakaian);
    }

    public void tambahElektronik(List<Elektronik> listElektronik) {
        daftarBarang.addAll(listElektronik);
    }

    public String updateData(String id, String merkBaru, String stokBaru, String hargaBaru) {
        Barang exBarang = cariId(id);
        StringBuilder pesan = new StringBuilder();

        if (!merkBaru.trim().isEmpty()) {
            exBarang.setMerk(merkBaru);
            pesan.append("\nMerk Barang berhasil dirubah.");
        }

        if (!stokBaru.trim().isEmpty()) {
            try {
                int parseStok = Integer.parseInt(stokBaru);
                if (parseStok > 0) {
                    exBarang.setStok(parseStok);
                    pesan.append("\nstok barang berhasil dirubah");
                } else {
                    pesan.append("\nAngka yang dimasukan tidak valid.");
                }
            } catch (Exception e) {
                pesan.append("\nstok barang harus angka " + e.getMessage());
            }
        }

        if (!hargaBaru.trim().isEmpty()) {
            try {
                double parseHarga = Double.parseDouble(hargaBaru);
                if (parseHarga > 0) {
                    exBarang.setHarga(parseHarga);;
                    pesan.append("\nHarga berhasil diubah.");
                } else {
                    pesan.append("\nAngka yang dimasukan tidak valid.");
                }
            } catch (Exception e) {
                pesan.append("\nGagal diperbarui: Harga barang harus angka " + e.getMessage());
            }
        }
        return pesan.toString().trim();
    }

    public String updateElektronik(String id, String merkBaru, String stokBaru, String hargaBaru, String modelBaru, JenisElektronik jenisBaru) {
        Barang exBarang = cariId(id);
        StringBuilder pesan = new StringBuilder();

        
        if (exBarang instanceof Elektronik) {
            Elektronik dataElektronik = (Elektronik) exBarang;
            
            pesan.append(updateData(id, merkBaru, stokBaru, hargaBaru));
            
            if (!modelBaru.trim().isEmpty()) {
                dataElektronik.setModel(modelBaru);
                pesan.append("\nModel Barang berhasil dirubah.");
            }

            if(jenisBaru != null && !dataElektronik.getJenis().equals(jenisBaru)) {
                dataElektronik.setJenis(jenisBaru);
                pesan.append("\nJenis Elektronik berhasil dirubah.");
            }
        } else {
            return "Barang dengan ID " + id + " bukan kategori Elektronik.";
        }

        return pesan.toString().trim();
    }
    
    public String updatePakaian(String id, String merkBaru, String stokBaru, String hargaBaru, JenisSize sizeBaru, JenisPakaian jenisBaru) {
        Barang exBarang = cariId(id);
        StringBuilder pesan = new StringBuilder();

        if (exBarang instanceof Pakaian) {
            Pakaian dataPakaian = (Pakaian) exBarang;

            pesan.append(updateData(id, merkBaru, stokBaru, hargaBaru));

            if (sizeBaru != null && !dataPakaian.getSize().equals(sizeBaru)) {
                dataPakaian.setSize(sizeBaru);
                pesan.append("\nUkuran Pakaian berhasil dirubah.");
            }

            if (jenisBaru != null && !dataPakaian.getJenis().equals(jenisBaru)) {
                dataPakaian.setJenis(jenisBaru);
                pesan.append("\nJenis Pakaian berhasil dirubah.");
            }

        } else {
            return "Barang dengan ID " + id + " bukan kategori Pakaian.";
        }

        return pesan.toString().trim();
    }

    public boolean hapusData(String id) {
        Barang exBarang = cariId(id);

        if(exBarang != null) {
            daftarBarang.remove(exBarang);
            return true;
        } else {
            return false;
        }

    }

    public String bacaData(String path) {
        StringBuilder logPesan = new StringBuilder();
        daftarBarang.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            logPesan.append("Memulai membaca data dari file...\n");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                try {
                    if (path.contains("elektronik.txt")) {
                    String id = parts[0];
                    String merk = parts[1];
                    int stok = Integer.parseInt(parts[2]);
                    double harga = Double.parseDouble(parts[3]);
                    String model = parts[4];
                    JenisElektronik jenis = JenisElektronik.valueOf(parts[5].toUpperCase());

                    Elektronik daftarElektronik = new Elektronik(id, merk, stok, harga, model, jenis);
                    elektronik.add(daftarElektronik);
                    
                    } else if(path.contains("pakaian.txt")) {
                        String id = parts[0];
                        String merk = parts[1];
                        int stok = Integer.parseInt(parts[2]);
                        double harga = Double.parseDouble(parts[3]);
                        JenisSize size = JenisSize.valueOf(parts[4].toUpperCase());
                        JenisPakaian jenis = JenisPakaian.valueOf(parts[5].toUpperCase());
                        
                        Pakaian daftarPakaian = new Pakaian(id, merk, stok, harga, size, jenis);
                        pakaian.add(daftarPakaian);

                    } else {
                        logPesan.append("Warning: Invalid line, skipped: " + line);
                    }
                    
                } catch (NumberFormatException e) {
                    logPesan.append("Error: Gagal mengonversi angka pada baris: " + line + ". Kesalahan: " + e.getMessage() + "\n");
                } catch (IllegalArgumentException e) {
                    logPesan.append("Error: Nilai enum tidak valid pada baris: " + line + ". Kesalahan: " + e.getMessage() + "\n");
                }
            }
            logPesan.append("Selesai membaca data. Total Elektronik: " + elektronik.size() + ", Total Pakaian: " + pakaian.size());
        } catch (IOException e) {
            logPesan.append("Error reading file: " + e.getMessage());
        }
        return logPesan.toString().trim();
    }

}
