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

    public List<Elektronik> ggetAllElektronik() {
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

    public String updateData(String id, String merkBaru, String jumlahBaru, String hargaBaru) {
        Barang exBarang = cariId(id);
        StringBuilder pesan = new StringBuilder();

        if (!merkBaru.trim().isEmpty()) {
            exBarang.setMerk(merkBaru);
            pesan.append("\nMerk Barang berhasil dirubah.");
        }

        if (!jumlahBaru.trim().isEmpty()) {
            try {
                int parseJumlah = Integer.parseInt(jumlahBaru);
                if (parseJumlah > 0) {
                    exBarang.setJumlah(parseJumlah);
                    pesan.append("\nJumlah barang berhasil dirubah");
                } else {
                    pesan.append("\nAngka yang dimasukan tidak valid.");
                }
            } catch (Exception e) {
                pesan.append("\nJumlah barang harus angka " + e.getMessage());
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
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                logPesan.append("Memulai membaca data dari file...\n");

                if (parts.length == 5 && path.contains("elektronik.txt")) {
                    String id = parts[0];
                    String merk = parts[1];
                    int jumlah = Integer.parseInt(parts[2]);
                    double harga = Double.parseDouble(parts[3]);
                    JenisElektronik jenis = JenisElektronik.valueOf(parts[4].toUpperCase());

                    Elektronik daftarElektronik = new Elektronik(id, merk, jumlah, harga, jenis);
                    elektronik.add(daftarElektronik);
                    
                } else if(parts.length == 6 && path.contains("pakaian.txt")) {
                    String id = parts[0];
                    String merk = parts[1];
                    int jumlah = Integer.parseInt(parts[2]);
                    double harga = Double.parseDouble(parts[3]);
                    JenisSize size = JenisSize.valueOf(parts[4].toUpperCase());
                    JenisPakaian jenis = JenisPakaian.valueOf(parts[5].toUpperCase());
                    
                    Pakaian daftarPakaian = new Pakaian(id, merk, jumlah, harga, size, jenis);
                    pakaian.add(daftarPakaian);

                } else {
                    logPesan.append("Warning: Invalid line, skipped: " + line);
                }
            }
            logPesan.append("Selesai membaca data. Total Elektronik: " + elektronik.size() + ", Total Pakaian: " + pakaian.size());
        } catch (IOException e) {
            logPesan.append("Error reading file: " + e.getMessage());
        }
        return logPesan.toString().trim();
    }

}
