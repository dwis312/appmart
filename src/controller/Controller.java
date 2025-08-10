package controller;

import helper.Helper;
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

        Helper.clearScreen();
        view.displayMsg("Program Berhenti.");
    }

    public void menuPilihan(int pilihan) {
        switch (pilihan) {
            case 1:
                Helper.clearScreen();
                view.displayMsg("\n=== Menu Tambah ===");
                getTambah();
                break;
            case 2:
                Helper.clearScreen();
                view.displayMsg("\n=== Update Data Barang ===");
                getUpdate();
                break;
            case 3:
                Helper.clearScreen();
                view.displayMsg("\n=== List Daftar Barang ===");
                getAll();
                break;
            case 4:
                Helper.clearScreen();
                view.displayMsg("\n=== Hapus Data Barang ===");
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
        try {
           String nama = view.getNama();
           int jumlah = view.getJumlah();
           double harga = view.getHarga();
            
           service.tambahData(nama, jumlah, harga);
           view.displayMsg("Data berhasil ditambah.");
        } catch (IllegalArgumentException e) {
            view.displayMsg("Erorr: " + e.getMessage());
        } finally {
            Helper.enterToContinue(view.getInput());
        }


    }

    public void getUpdate() {
        if (service.getAllData().isEmpty()) {
            view.displayMsg("Data masih kosong.");
            Helper.enterToContinue(view.getInput());
            return;
        }
        
        String keyword = view.getKode();
        
        if (service.cariId(keyword) == null) {
            view.displayMsg("Data tidak ditemukan.");
            return;
        }


        Barang exBarang = service.cariId(keyword);
        String id = exBarang.getId();

        view.displayMsg("Data ditemukan: ");

        view.listBarang();
        view.dataBarang(exBarang.getId(), exBarang.getNama(), exBarang.getJumlah(), exBarang.getHarga());

        view.displayMsg("");
        view.displayMsg("**Biarkan kosong jika tidak ingin mengubah.");
        view.displayMsg("");
        
        try {
            String namaBaru = view.getFormStr("Ganti Nama: ");
            String jumlahBaru = view.getFormStr("Rubah Jumlah: ");
            String hargaBaru = view.getFormStr("Rubah Harga: ");
            
            service.updateData(id, namaBaru, jumlahBaru, hargaBaru);
        } catch (IllegalArgumentException e) {
            view.displayMsg("Erorr: " + e.getMessage());
        } finally {
            Helper.enterToContinue(view.getInput());
        }

    }

    public void getAll() {
        List<Barang> daftarBarang = service.getAllData();
        if (daftarBarang.isEmpty()) {
            view.displayMsg("Data masih kosong.");
        } else {
            view.listBarang();
            view.allBarang(daftarBarang);
        }

        Helper.enterToContinue(view.getInput());

    }

    public void getHapus() {
        List<Barang> daftarBarang = service.getAllData();

        if (daftarBarang.isEmpty()) {
            view.displayMsg("Data masih kosong.");
            Helper.enterToContinue(view.getInput());
            return;
        }

        view.listBarang();
        view.allBarang(daftarBarang);

        view.displayMsg("\n**Pilih nomor 0 untuk kembali...");
        view.displayMsg("\nPilih Data yang akan dihapus: ");
    
        int indexInput = view.getFormInt("Pilih No: ");

        if (indexInput == 0) {
            return;
        }

        if (indexInput >= 1 && indexInput <= daftarBarang.size()) {
            Barang hapusBarang = daftarBarang.get(indexInput -1);

            view.displayMsg("Hapus Data: ");
            view.displayMsg(" " + hapusBarang.getId());
            view.displayMsg(" - " + hapusBarang.getNama());
            view.displayMsg(" ");

            view.displayMsg("Yakin hapus ?");
            view.displayMsg("1. Ya, Hapus data.");
            view.displayMsg("2. Batalkan.");
            int konfirm = view.getFormInt("Pilih: ");

            if (konfirm == 1) {
                view.displayMsg("Hapus data berhasil");
                service.hapusData(hapusBarang.getId());
            } else {
                view.displayMsg("Batalkan.");
            }
        } else {
            view.displayMsg("Pilihan tidak valid.");
        }
        Helper.enterToContinue(view.getInput());

    }

    public void getLoad() {

    }

}
