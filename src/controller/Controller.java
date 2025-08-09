package controller;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import helper.Helper;
import model.Barang;
import service.BarangService;
import view.ConsoleView;

public class Controller {
    private Scanner input = new Scanner(System.in);
    private boolean exit;
    private int pilihan;
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
    
    private ConsoleView menu;
    private BarangService barang = new BarangService();

    public void run() {
        menu = new ConsoleView();
        exit = false;

        while (!exit) {
            menu.menu();
            pilihan = Helper.inputInt(input);
            exit = menu.menuPilihan(pilihan);
        }
        Helper.clearScreen();
        System.out.println("Program Berhenti.");
        input.close();

    }

    public void getInput() {
        this.input = input;
    }

    public boolean isExit() {
        return true;
    }

    public void getTambah() {
        String id;
        String nama;
        int jumlah;
        double harga;

        System.out.print("\nMasukan Nama barang: ");
        nama = Helper.inputStr(input);
        System.out.print("Jumlah barang: ");
        jumlah = Helper.inputInt(input);
        System.out.print("Harga barang: ");
        harga = Helper.inputDouble(input);

        barang.tambahData(nama, jumlah, harga);
        Helper.enterToContinue(input);
    }

    public void getUpdate() {
        System.out.print("\nMasukan Kode barang: ");
        String keyword = input.nextLine();

        if (barang.getAllData() == null) {
            System.out.println("Data masih kosong.");
            return;
        } else if (barang.cariId(keyword) == null) {
            System.out.println("Data tidak ditemukan.");
            return;
        }

        Barang exBarang = barang.cariId(keyword);
        String id = exBarang.getId();

        System.out.println("Data ditemukan: ");

        System.out.println("------------------------------------------------------------");
        System.out.printf("| %-2s | %-5s | %-7s | %-7s | %-15s |\n",
                        "No",
                        "ID Barang",
                        "Nama Barang",
                        "Jumlah",
                        "Harga");
        System.out.println("------------------------------------------------------------");
        
        System.out.printf("| %-2s | %-9s | %-11s | %-7s | %-15s |\n",
                        "1",
                        exBarang.getId(),
                        exBarang.getNama(),
                        exBarang.getJumlah(),
                        formatRupiah.format(exBarang.getHarga()));
        System.out.println("------------------------------------------------------------");

        System.out.println("");
        System.out.println("**Biarkan kosong jika tidak ingin mengubah.");
        System.out.println("");
        System.out.print("Ganti Nama: ");
        String namaBaru = input.nextLine();

        System.out.print("Rubah Jumlah: ");
        String jumlahBaru = input.nextLine();

        System.out.print("Rubah Harga: ");
        String hargaBaru = input.nextLine();

        barang.updateData(id, namaBaru, jumlahBaru, hargaBaru);
        Helper.enterToContinue(input);
    }

    public void getAll() {
        if (barang.getAllData() == null) {
            System.out.println("Data masih kosong.");
            return;
        }

        List<Barang> daftarBarang = barang.getAllData();

        for (int i = 0; i < daftarBarang.size(); i++) {
                System.out.printf("| %-2s | %-9s | %-11s | %-7s | %-15s |\n",
                                i+1,
                                daftarBarang.get(i).getId(),
                                daftarBarang.get(i).getNama(),
                                daftarBarang.get(i).getJumlah(),
                                formatRupiah.format(daftarBarang.get(i).getHarga()));
        }
        System.out.println("------------------------------------------------------------");
        Helper.enterToContinue(input);

    }

    public void getHapus() {
        if (barang.getAllData() == null) {
            System.out.println("Data masih kosong.");
            return;
        }

        List<Barang> daftarBarang = barang.getAllData();

        for (int i = 0; i < daftarBarang.size(); i++) {
                System.out.printf("| %-2s | %-9s | %-11s | %-7s | %-15s |\n",
                                i+1,
                                daftarBarang.get(i).getId(),
                                daftarBarang.get(i).getNama(),
                                daftarBarang.get(i).getJumlah(),
                                formatRupiah.format(daftarBarang.get(i).getHarga()));
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("\n**Pilih nomor 0 untuk kembali...");
        System.out.println("\nPilih Data yang akan dihapus: ");
        System.out.print("Pilih No: ");
        int no = Helper.inputInt(input);

        if (no == 0) {
            return;
        }

        Barang exBarang = barang.hapusData(no);
        System.out.println(exBarang.getId());
        System.out.println(exBarang.getNama());

    }

    public void getLoad() {

    }

}
