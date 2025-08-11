package controller;

import java.util.List;
import model.Barang;
import service.BarangService;
import view.ConsoleView;

public class Controller {
    private boolean exit;
    private int pilihan;
    
    private ConsoleView view;
    private BarangService service;

    public Controller(ConsoleView view, BarangService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        while (!exit) {
            view.menu();
            pilihan = view.getInputInt();
            menuPilihan(pilihan);
            exit = isExit(pilihan);
        }
        view.displayMsg("Program Berhenti.");
    }

    public void menuPilihan(int pilihan) {
        switch (pilihan) {
            case 1:
                getTambah();
                break;
            case 2:
                getUpdate();
                break;
            case 3:
                getAll();
                break;
            case 4:
                getHapus();
                break;
            case 0:
                break;
            default:
                view.displayMsg("Pilihan tidak valid.");
                break;
        }
    }

    public boolean isExit(int num) {
        if(num == 0) {
            return exit = true;
        } else {
            return false;
        }
    }

    public void getTambah() {
        view.header("Tambah Barang");
        try {
           String nama = view.getNama();
           int jumlah = view.getJumlah();
           double harga = view.getHarga();
            
           Barang barang = service.tambahData(nama, jumlah, harga);
           view.displayMsg("Data ID: [ " + barang.getId() + " ] berhasil ditambah.");
        } catch (IllegalArgumentException e) {
            view.displayMsg("Error: " + e.getMessage());
        } finally {
            view.backMenu();
        }
    }

    public void getUpdate() {
        view.header("Update Data Barang");
        if (service.getAllData().isEmpty()) {
            view.displayMsg("Data masih kosong.");
            view.backMenu();
            return;
        }
        
        String keyword = view.getKode();
        
        if (service.cariId(keyword) == null) {
            view.displayMsg("Data tidak ditemukan.");
            view.backMenu();
            return;
        }


        Barang exBarang = service.cariId(keyword);
        String id = exBarang.getId();

        view.displayMsg("Data ditemukan: ");

        view.dataBarang(exBarang.getId(), exBarang.getNama(), exBarang.getJumlah(), exBarang.getHarga());

        view.displayMsg("");
        view.displayMsg("**Biarkan kosong jika tidak ingin mengubah.");
        view.displayMsg("");
        
        try {
            String namaBaru = view.getFormUpadate("Ganti Nama: ");
            String jumlahBaru = view.getFormUpadate("Rubah Jumlah: ");
            String hargaBaru = view.getFormUpadate("Rubah Harga: ");
            
            String pesan = service.updateData(id, namaBaru, jumlahBaru, hargaBaru);
            view.displayMsg(pesan);
        } catch (IllegalArgumentException e) {
            view.displayMsg("Error: " + e.getMessage());
        } finally {
            view.backMenu();
        }

    }

    public void getAll() {
        view.header("List Daftar Barang");
        List<Barang> daftarBarang = service.getAllData();
        if (daftarBarang.isEmpty()) {
            view.displayMsg("Data masih kosong.");
        } else {
            view.allBarang(daftarBarang);
        }

        view.backMenu();

    }

    public void getHapus() {
        view.header("Hapus Data Barang");
        List<Barang> daftarBarang = service.getAllData();

        if (daftarBarang.isEmpty()) {
            view.displayMsg("Data masih kosong.");
            view.backMenu();
            return;
        }

        view.allBarang(daftarBarang);

        int indexInput = view.getFormInt("\n**Pilih nomor 0 untuk kembali...\nPilih Data yang akan dihapus: \nPilih No: ");

        if (indexInput == 0) {
            return;
        }

        if (indexInput >= 1 && indexInput <= daftarBarang.size()) {
            Barang hapusBarang = daftarBarang.get(indexInput -1);

            view.displayMsg("Hapus Data: " + hapusBarang.getId() + " - " + hapusBarang.getNama());

            int konfirm = view.getFormInt("\nYakin hapus ?\n1. Ya hapus\n2. Batal\nPilih: ");

            if (konfirm == 1) {
                boolean berhasil = service.hapusData(hapusBarang.getId());
                if(berhasil) {
                    view.displayMsg("berhasil dihapus: " + hapusBarang.getId() + " - " + hapusBarang.getNama());
                }
            } else {
                view.displayMsg("Dibatalkan.");
            }
        } else {
            view.displayMsg("Pilihan tidak valid.");
        }
        view.backMenu();

    }

    public void getLoad() {

    }

}
