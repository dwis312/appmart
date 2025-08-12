package view;

import helper.Helper;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import model.Barang;
import model.JenisElektronik;
import model.JenisPakaian;
import model.JenisSize;

public class ConsoleView {
    private final Scanner input;
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));

    public ConsoleView(Scanner input) {
        this.input = input;
    }

    public int getInputInt() {
        int pilihan = Helper.inputInt(input);
        return pilihan;
    }
    
    public Scanner getInput() {
        return input;
    }

    public void backMenu() {
       Helper.enterToContinue(input);
    }

    public String getMerk() {
        System.out.print("\nMasukan Merk barang: ");
        return Helper.inputStr(input);
    }
    
    public String getKode() {
        System.out.print("\nMasukan kode barang: ");
        return Helper.inputStr(input);
    }

    public int getJumlah() {
        System.out.print("Jumlah barang: ");
        return Helper.inputInt(input);
    }

    public double getHarga() {
        System.out.print("Harga barang: ");
        return Helper.inputDouble(input);
    }

    public JenisElektronik getJenisElektronik() {
        header("Pilih Jenis Elektronik");
        int i = 1;

        for (JenisElektronik jenis : JenisElektronik.values()) {
            System.out.println(i++ +". " + jenis.getDeskripsi());
        }

        int pilihan = -1;
        while (true) {
            pilihan = getFormInt("Pilih: ");

            if (pilihan >= 1 && pilihan <= JenisElektronik.values().length) {
                return JenisElektronik.values()[pilihan - 1];
            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }

    public JenisPakaian getJenisPakaian() {
        header("Pilih Jenis Pakaian");
        int i = 1;

        for (JenisPakaian jenis : JenisPakaian.values()) {
            System.out.println(i++ +". " + jenis.getDeskripsi());
        }

        int pilihan = -1;
        while (true) {
            pilihan = getFormInt("Pilih: ");

            if (pilihan >= 1 && pilihan <= JenisPakaian.values().length) {
                return JenisPakaian.values()[pilihan - 1];
            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }

    public JenisSize getJenisSize() {
        header("Pilih Ukuran");
        int i = 1;

        for (JenisSize jenis : JenisSize.values()) {
            System.out.println(i++ +". " + jenis.getDeskripsi());
        }

        int pilihan = -1;
        while (true) {
            pilihan = getFormInt("Pilih: ");

            if (pilihan >= 1 && pilihan <= JenisSize.values().length) {
                return JenisSize.values()[pilihan - 1];
            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }

    public String getFormUpadate(String message) {
        System.out.print(message);
        return getInput().nextLine();
    }

    public String getFormStr(String message) {
        System.out.print(message);
        return Helper.inputStr(input);
    }

    public int getFormInt(String message) {
        System.out.print(message);
        return Helper.inputInt(input);
    }

    public double getFormDbl(String message) {
        System.out.print(message);
        return Helper.inputDouble(input);
    }

    public void displayMsg(String message) {
        System.out.println(message);
    }

    public void header(String text) {
        Helper.clearScreen();
        System.out.printf("=== %-5s ===\n", text);
    }

    public void menu() {
        header("APP MART");
        System.out.println("");
        System.out.println("1. Daftar barang");
        System.out.println("2. Tambah barang");
        System.out.println("3. Update Data");
        System.out.println("4. Hapus barang");
        System.out.println("0. Keluar");
        System.out.println("----------------");
        System.out.print("Pilih : ");
    }

    public void headerTabel() {
        System.out.println("------------------------------------------------------------");
        System.out.printf("| %-2s | %-5s | %-7s | %-7s | %-5s | %-15s |\n",
        "No",
        "ID",
        "Merk",
        "Kategori",
        "Jumlah",
        "Harga");
        System.out.println("------------------------------------------------------------");
    }

    public void allBarang(List<Barang> daftarBarang) {
        headerTabel();
        for (int i = 0; i < daftarBarang.size(); i++) {
            Barang barang = daftarBarang.get(i);

            System.out.printf("| %-2s | %-5s | %-7s | %-7s | %-5s | %-15s |\n",
                                barang.getId(),
                                barang.getMerk(),
                                barang.getKategori(),
                                barang.getJumlah(),
                                formatRupiah.format(barang.getHarga()));
        }
        System.out.println("------------------------------------------------------------");
    }

    public void dataBarang(String id, int jumlah, double harga, String merk, String jenis) {
        headerTabel();
        System.out.printf("| %-2s | %-5s | %-7s | %-7s | %-5s | %-15s |\n",
                        "1",
                        id,
                        jenis,
                        merk,
                        jumlah,
                        formatRupiah.format(harga));
        System.out.println("------------------------------------------------------------");
    }

    public void menuTambah() {
        header("Tambah Barang");
        System.out.println("1. Tambah Elektronik");
        System.out.println("2. Tambah Pakaian");
        System.out.println("0. Kembali");
        System.out.println("----------------");
    }

}
