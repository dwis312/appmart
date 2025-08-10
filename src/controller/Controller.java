package controller;

import helper.Helper;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
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

        while (!exit) {
            menu.menu();
            pilihan = Helper.inputInt(input);
            menu.menuPilihan(pilihan);
            exit = isExit(pilihan);
        }
        Helper.clearScreen();
        System.out.println("Program Berhenti.");
        input.close();

    }

    public void getInput() {
        this.input = input;
    }

    public boolean isExit(int num) {
        if(num == 0) {
            return exit = true;
        } else {
            return false;
        }
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
        
        if (barang.getAllData().isEmpty()) {
            System.out.println("Data masih kosong.");
            Helper.enterToContinue(input);
            return;
        }
        
        System.out.print("\nMasukan Kode barang: ");
        String keyword = input.nextLine();
        
        if (barang.cariId(keyword) == null) {
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
        if (barang.getAllData().isEmpty()) {
            System.out.println("Data masih kosong.");
            Helper.enterToContinue(input);
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
        int index = Helper.inputInt(input);

        if (index == 0) {
            return;
        }
        
        int[] indexMap = new int[daftarBarang.size()];
        int count = 0;

        for (int i = 0; i < daftarBarang.size(); i++) {
            indexMap[count] = i;
            count++;
        }

        if (index >= 1 && index <= count) {
            int numIndex = indexMap[index - 1];
            System.out.print("Hapus Data: ");
            System.out.print(" " + daftarBarang.get(numIndex).getId());
            System.out.print(" - " + daftarBarang.get(numIndex).getNama());
            System.out.println();

            String id = daftarBarang.get(numIndex).getId();
            int konfirm = -1;

            System.out.println("Yakin hapus ?");
            System.out.println("1. Ya, Hapus data.");
            System.out.println("2. Batalkan.");
            System.out.print("Pilih: ");
            konfirm = Helper.inputInt(input);

            if (konfirm == 1 && !id.isEmpty()) {
                System.out.println("Hapus data berhasil");
                barang.hapusData(id);
            } else {
                System.out.println("Batalkan.");
            }
        } else {
            System.out.println("Pilihan tidak valid.");
        }
        Helper.enterToContinue(input);

    }

    public void getLoad() {

    }

}
