package view;

import helper.Helper;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import model.Barang;
import model.Elektronik;
import model.JenisElektronik;

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
        System.out.println("1. Tambah barang");
        System.out.println("2. Update Data");
        System.out.println("3. List barang");
        System.out.println("4. Hapus barang");
        System.out.println("0. Keluar");
        System.out.println("----------------");
        System.out.print("Pilih : ");
    }

    public void headerTabel() {
        System.out.println("------------------------------------------------------------");
        System.out.printf("| %-2s | %-5s | %-7s | %-7s | %-5s | %-15s |\n",
        "No",
        "ID Barang",
        "Kategori",
        "Merk",
        "Jumlah",
        "Harga");
        System.out.println("------------------------------------------------------------");
    }

    public void allBarang(List<Barang> daftarBarang) {
        headerTabel();
        for (int i = 0; i < daftarBarang.size(); i++) {
            Barang barang = daftarBarang.get(i);
            if(barang instanceof Elektronik) {
                Elektronik elektronik = (Elektronik) barang;

                System.out.printf("| %-2s | %-5s | %-7s | %-7s | %-5s | %-15s |\n",
                                i+1,
                                elektronik.getId(),
                                elektronik.getKategori(),
                                elektronik.getMerk(),
                                elektronik.getJumlah(),
                                formatRupiah.format(elektronik.getHarga()));
            }
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

}
