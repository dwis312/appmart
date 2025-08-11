package controller;

import java.util.List;
import model.Barang;
import model.Elektronik;
import model.JenisElektronik;
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
            exit = menuPilihan(pilihan);;
        }
        view.displayMsg("Program Berhenti.");
    }

    public boolean  menuPilihan(int pilihan) {
        boolean ex = false;
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
                return ex = true;
            default:
                view.displayMsg("Pilihan tidak valid.");
                break;
        }

        return ex;
    }

    public void getTambah() {
        view.header("Tambah Barang");
        try {
            int jumlah = view.getJumlah();
            double harga = view.getHarga();
            String id = service.generateId();
            String merk = view.getMerk();
            JenisElektronik jenis = view.getJenisElektronik();
            
           Elektronik newElektronik = new Elektronik(id, jumlah, harga, merk, jenis);
           service.tambahData(newElektronik);

           view.displayMsg("Data ID: [ " + newElektronik.getId() + " ] berhasil ditambah.");
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
        Elektronik elektronik = (Elektronik) exBarang;
        String id = elektronik.getId();

        view.displayMsg("Data ditemukan: ");
        view.dataBarang(elektronik.getId(), elektronik.getJumlah(), elektronik.getHarga(), elektronik.getMerk(), elektronik.getKategori());

        view.displayMsg("");
        view.displayMsg("**Biarkan kosong jika tidak ingin mengubah.");
        view.displayMsg("");
        
        try {
            String jumlahBaru = view.getFormUpadate("Rubah Jumlah: ");
            String hargaBaru = view.getFormUpadate("Rubah Harga: ");
            
            String pesan = service.updateData(id, jumlahBaru, hargaBaru);
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

            view.displayMsg("Hapus Data: " + hapusBarang.getId());

            int konfirm = view.getFormInt("\nYakin hapus ?\n1. Ya hapus\n2. Batal\nPilih: ");

            if (konfirm == 1) {
                boolean berhasil = service.hapusData(hapusBarang.getId());
                if(berhasil) {
                    view.displayMsg("berhasil dihapus: " + hapusBarang.getId());
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
