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
    
    public String getFormPolos(String message) {
        System.out.print(message);
        return input.nextLine();
    }

    public void enterToContinue() {
       Helper.enterToContinue(input);
    }

    public String getMerk() {
        System.out.print("\nMasukan Merk barang: ");
        return Helper.inputStr(input);
    }

    public String getModel() {
        System.out.print("\nMasukan Model barang: ");
        return Helper.inputStr(input);
    }
    
    public String getKode() {
        System.out.print("\nMasukan kode barang: ");
        return Helper.inputStr(input);
    }

    public int getStok() {
        System.out.print("Stok barang: ");
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
        return input.nextLine();
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
        System.out.printf("\n***               %-5s               ***\n", text);
    }

    public void menu() {
        header("APP MART");
        System.out.println("");
        System.out.println("1. Daftar Semua barang");
        System.out.println("2. Pilih Kategori Barang");
        System.out.println("0. Keluar");
        System.out.println("----------------");
        System.out.print("Pilih : ");
    }

    public void menuKategori() {
        header("Pilih Kategori Barang");
        System.out.println("");
        System.out.println("1. Handphone");
        System.out.println("2. Laptop");
        System.out.println("3. Tv");
        System.out.println("4. Kaos");
        System.out.println("5. Baju");
        System.out.println("0. Keluar");
        System.out.println("----------------");
        System.out.print("Pilih : ");
    }

    public void detailBarangElektronik(String id, String merk, int stok, double harga, String model, JenisElektronik jenis) {
        header("Detail: " + merk);

        System.out.println();
        System.out.println("ID Barang        : " + id);
        System.out.println("Merk Barang      : " + merk);
        System.out.println("Model Barang     : " + model);
        System.out.println("Kategori Barang  : " + jenis);
        System.out.println("Stok Barang      : " + stok);
        System.out.println("Harga            : " + formatRupiah.format(harga) +"\n");
        System.out.println("---------------------------------------");
    }

    public void detailBarangPakaian(String id, String merk, int stok, double harga, JenisSize size, JenisPakaian jenis ) {
        header("Detail: " + merk);
        
        System.out.println();
        System.out.println("ID Barang        : " + id);
        System.out.println("Merk Barang      : " + merk);
        System.out.println("Ukuran Barang     : " + size);
        System.out.println("Kategori Barang  : " + jenis);
        System.out.println("Stok Barang      : " + stok);
        System.out.println("Harga            : " + formatRupiah.format(harga) +"\n");
        System.out.println("---------------------------------------");
    }

    public void headerTabel() {
        System.out.println("\n===========================================================================================");
        System.out.printf("| %-5s | %-5s | %-10s | %-25s | %-7s | %-20s |\n",
        "No",
        "ID",
        "Merk",
        "Kategori",
        "Stok",
        "Harga");
        System.out.println("-------------------------------------------------------------------------------------------");
    }

    public void headerByKategori() {
        System.out.println("\n===============================================================");
        System.out.printf("| %-5s | %-5s | %-10s | %-7s | %-20s |\n",
        "No",
        "ID",
        "Merk",
        "Stok",
        "Harga");
        System.out.println("---------------------------------------------------------------");
    }

    public void allBarang(List<Barang> daftarBarang, int halamanIni, int totalHalaman) {
        headerTabel();
        for (int i = 0; i < daftarBarang.size(); i++) {
            Barang barang = daftarBarang.get(i);
            String[] parts = barang.getKategori().split("\\-");

            int noUrut = (halamanIni - 1) * 10 + i + 1;

            System.out.printf("| %-5s | %-5s | %-10s | %-25s | %-7s | %-20s |\n",
                                noUrut,
                                barang.getId(),
                                barang.getMerk(),
                                parts[1],
                                barang.getStok(),
                                formatRupiah.format(barang.getHarga()));
        }
        System.out.println("===========================================================================================");
        System.out.printf("[ Halaman %d dari %d ]\n", halamanIni, totalHalaman);
    }

    public void barangByKategori(List<Barang> daftarBarang, int halamanIni, int totalHalaman) {
        headerByKategori();
        for (int i = 0; i < daftarBarang.size(); i++) {
            Barang barang = daftarBarang.get(i);
            int noUrut = (halamanIni - 1) * 10 + i + 1;

            System.out.printf("| %-5s | %-5s | %-10s | %-7s | %-20s |\n",
                                noUrut,
                                barang.getId(),
                                barang.getMerk(),
                                barang.getStok(),
                                formatRupiah.format(barang.getHarga()));
        }
        System.out.println("===============================================================");
        System.out.printf("[ Halaman %d dari %d ]\n", halamanIni, totalHalaman);
    }

    public void dataBarang(String id, int jumlah, double harga, String merk, String jenis) {
        headerTabel();
        System.out.printf("| %-2s | %-5s | %-7s | %-20s | %-7s | %-20s |\n",
                        "1",
                        id,
                        merk,
                        jenis,
                        jumlah,
                        formatRupiah.format(harga));
        System.out.println("================================================================================");
    }

    public void menuTambah() {
        header("Tambah Barang");
        System.out.println("\n1. Tambah Elektronik");
        System.out.println("2. Tambah Pakaian");
        System.out.println("0. Kembali");
        System.out.println("----------------");
    }

    public int getPilihPage() {
        System.out.println();
        System.out.println("[9] Selanjutnya | [8] Sebelumnya | [0] Kembali");
        System.out.println("");
        System.out.println("1. Detail Barang");
        System.out.println("2. Tambah Barang");
        System.out.println("3. Hapus Barang");
        System.out.println("4. Update Barang");
        System.out.print("Pilih: ");
        return  Helper.inputInt(input);
    }

    public String getNavigasi(int startIndex, int endIndex, String header) {
        System.out.println("\n(N) Selanjutnya | (P) Sebelumnya | enter untuk kembali");
        System.out.println("Pilih nomor (" + (startIndex +1)+ " - " + endIndex + ") untuk " + header.toLowerCase());
        System.out.print("\n**Pilihan anda : ");
        return input.nextLine();
    }

}
