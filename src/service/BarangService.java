package service;

import java.util.ArrayList;
import java.util.List;
import model.Barang;

public class BarangService {
    private ArrayList<Barang> daftarBarang = new ArrayList<>();

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

    public void tambahData(Barang barang) {
        daftarBarang.add(barang);
    }

    public String updateData(String id, String jumlahBaru, String hargaBaru) {
        Barang exBarang = cariId(id);
        StringBuilder pesan = new StringBuilder();

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

}
