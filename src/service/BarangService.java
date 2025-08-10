package service;

import java.util.ArrayList;
import java.util.List;
import model.Barang;

public class BarangService {
    private ArrayList<Barang> daftarBarang = new ArrayList<>();

    private String generateId() {
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
        return new ArrayList<>(daftarBarang);
    }

    public void tambahData(String nama, int jumlah, double harga) {
        String id = generateId();
        daftarBarang.add(new Barang(id, nama, jumlah, harga));
        System.out.println("Data ID: [ " + id +" ] berhasil ditambah.");
    }

    public void updateData(String id, String namaBaru, String jumlahBaru, String hargaBaru) {
        Barang exBarang = cariId(id);

        if (!namaBaru.trim().isEmpty()) {
            exBarang.setNama(namaBaru);
            System.out.println("Nama berhasil dirubah");
        }

        if (!jumlahBaru.trim().isEmpty()) {
            try {
                int parseJumlah = Integer.parseInt(jumlahBaru);
                if (parseJumlah > 0) {
                    exBarang.setJumlah(parseJumlah);
                    System.out.println("Jumlah barang berhasil dirubah");
                } else {
                    System.out.println("Angka yang dimasukan tidak valid.");
                }
            } catch (Exception e) {
                System.out.println("Jumlah barang harus angka");
            }
        }

        if (!hargaBaru.trim().isEmpty()) {
            try {
                double parseHarga = Double.parseDouble(hargaBaru);
                if (parseHarga > 0) {
                    exBarang.setHarga(parseHarga);;
                    System.out.println("Harga berhasil diubah.");
                } else {
                    System.out.println("Angka yang dimasukan tidak valid.");
                }
            } catch (Exception e) {
                System.out.println("Harga barang harus angka");
            }
        }
    }

    public void hapusData(String id) {
        Barang exBarang = cariId(id);

        if(exBarang.getId().equalsIgnoreCase(id)) {
            System.out.println(exBarang.getId()+ " - " + exBarang.getNama() + " Dihapus.");
            daftarBarang.remove(exBarang);
        }

    }

}
