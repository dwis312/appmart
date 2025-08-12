package controller;

import java.util.List;
import model.Barang;
import model.Elektronik;
import model.JenisElektronik;
import model.JenisPakaian;
import model.JenisSize;
import model.Pakaian;
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
        getLoad();
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
                getAll();
                break;
            case 2:
                getTambah();
                break;
            case 3:
                getUpdate();
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

    public void getAll() {
        view.header("List Daftar Barang");
        List<Barang> daftarBarang = service.getAllData();
        if (daftarBarang.isEmpty()) {
            view.displayMsg("\nData masih kosong.");
        } else {
            view.allBarang(daftarBarang);
        }

        view.backMenu();

    }

    public void getTambah() {
        view.menuTambah();
        int pilihanAdd = view.getFormInt("Pilih: ");

        switch (pilihanAdd) {
            case 1:
                tambahElektronik();
                break;
            case 2:
                tambahPakaian();
                break;
            case 0:
                return;
            default:
                view.displayMsg("Pilihan tidak valid.");
                break;
        }
        view.backMenu();
    }
    
    public void tambahElektronik() {
        view.header("Elektronik");
        try {
            String merk = view.getMerk();
            int jumlah = view.getJumlah();
            double harga = view.getHarga();
            JenisElektronik jenis = view.getJenisElektronik();
            String id = service.generateId();

            Elektronik newElektronik = new Elektronik(id, merk, jumlah, harga, jenis);
            service.tambahData(newElektronik);

            view.displayMsg("Data ID: [ " + newElektronik.getId() + " ] berhasil ditambah.");
        } catch (IllegalArgumentException e) {
            view.displayMsg("Error: "+ e.getMessage());
        }
    }

    public void tambahPakaian() {
        view.header("Pakaian");

        try {
            String merk = view.getMerk();
            int jumlah = view.getJumlah();
            double harga = view.getHarga();
            JenisSize size = view.getJenisSize();
            JenisPakaian jenis = view.getJenisPakaian();
            String id = service.generateId();

            Pakaian newPakaian = new Pakaian(id, merk, jumlah, harga, size, jenis);
            service.tambahData(newPakaian);

            view.displayMsg("Data ID: [ " + newPakaian.getId() + " ] berhasil ditambah.");
        } catch (IllegalArgumentException e) {
            view.displayMsg("Error: " + e.getMessage());
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
        Barang exBarang = service.cariId(keyword);

        if (exBarang instanceof Elektronik) {
            Elektronik elektronik = (Elektronik) exBarang;
            view.dataBarang(elektronik.getId(), elektronik.getJumlah(), elektronik.getHarga(), elektronik.getMerk(), elektronik.getKategori());
        } else if (exBarang instanceof Pakaian) {
            Pakaian pakaian = (Pakaian) exBarang;
            view.dataBarang(pakaian.getId(), pakaian.getJumlah(), pakaian.getHarga(), pakaian.getMerk(), pakaian.getKategori());
        }

        view.displayMsg("");
        view.displayMsg("**Biarkan kosong jika tidak ingin mengubah.");
        view.displayMsg("");
        
        try {
            String merkBaru = view.getFormUpadate("Rubah Merk: ");
            String jumlahBaru = view.getFormUpadate("Rubah Jumlah: ");
            String hargaBaru = view.getFormUpadate("Rubah Harga: ");
            
            String pesan = service.updateData(exBarang.getId(), merkBaru, jumlahBaru, hargaBaru);
            view.displayMsg(pesan);
        } catch (IllegalArgumentException e) {
            view.displayMsg("Error: " + e.getMessage());
        } finally {
            view.backMenu();
        }

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

    private void getLoad() {
        String[] pesan = new String[2];
        pesan[0] = service.bacaData("data/elektronik.txt");
        pesan[1] = service.bacaData("data/pakaian.txt");
        
        for (int i = 0; i < pesan.length; i++) {
            view.displayMsg(pesan[i]);
        }

        service.tambahElektronik(service.ggetAllElektronik());
        service.tambahPakaian(service.getAllPakaian());
    }

}
