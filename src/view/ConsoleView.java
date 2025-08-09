package view;

import controller.Controller;
import helper.Helper;

public class ConsoleView {
    private Controller app = new Controller();

    public void menu() {
        Helper.clearScreen();
        System.out.println("\n=== APP MART ===");
        System.out.println("");
        System.out.println("1. Tambah barang");
        System.out.println("2. Update Data");
        System.out.println("3. List barang");
        System.out.println("4. Hapus barang");
        System.out.println("0. Keluar");
        System.out.println("----------------");
        System.out.print("Pilih : ");
    }

    public boolean menuPilihan(int pilihan) {
        switch (pilihan) {
            case 1:
                Helper.clearScreen();
                System.out.println("\n=== Menu Tambah ===");
                app.getTambah();
                break;
            case 2:
                Helper.clearScreen();
                System.out.println("\n=== Update Data Barang ===");
                app.getUpdate();
                break;
            case 3:
                Helper.clearScreen();
                System.out.println("\n=== List Daftar Barang ===");
                listBarang();
                app.getAll();
                break;
            case 4:
                Helper.clearScreen();
                System.out.println("\n=== Hapus Data Barang ===");
                listBarang();
                app.getHapus();
                break;
            case 0:
                app.isExit();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                break;
        }
        return false;
    }

    public void listBarang() {
        System.out.println("------------------------------------------------------------");
        System.out.printf("| %-2s | %-5s | %-7s | %-7s | %-15s |\n",
                                "No",
                                "ID Barang",
                                "Nama Barang",
                                "Jumlah",
                                "Harga");
        System.out.println("------------------------------------------------------------");
    }

}
