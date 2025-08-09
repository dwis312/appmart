import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        ArrayList<Barang> barang = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        int pilihan;

        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));

        while (!exit) {
            System.out.println("\n=== APP MART ===");
            System.out.println("");
            System.out.println("1. Tambah barang");
            System.out.println("2. Edit barang");
            System.out.println("3. Data barang");
            System.out.println("4. Hapus barang");
            System.out.println("0. Keluar");
            System.out.println("");
            System.out.print("Pilih : ");

            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    System.out.println("\n=== Menu Tambah ===");
                    String id;
                    String nama;
                    int jumlah;
                    double harga;

                    System.out.print("\nMasukan Nama barang: ");
                    nama = input.nextLine();
                    System.out.print("Jumlah barang: ");
                    jumlah = input.nextInt();
                    System.out.print("Harga barang: ");
                    harga = input.nextDouble();
                    input.nextLine();

                    id = "B" + String.format("%03d", barang.size() + 1);
                    barang.add(new Barang(id, nama, jumlah, harga));
                    System.out.println("Barang berhasil ditambah.");

                    System.out.println("Enter untuk kembali ke menu...");
                    input.nextLine();
    
                    break;
                case 2:
                    System.out.println("\n=== Menu Edit ===");
                    System.out.print("\nMasukan Kode barang: ");
                    String keyword = input.nextLine();

                    for (int i = 0; i < barang.size(); i++) {
                        if (barang.get(i).getId().equalsIgnoreCase(keyword)) {
                            System.out.println("------------------------------------------------------------");
                            System.out.printf("| %-2s | %-5s | %-7s | %-7s | %-15s |\n",
                                            "No",
                                            "ID Barang",
                                            "Nama Barang",
                                            "Jumlah",
                                            "Harga");
                            System.out.println("------------------------------------------------------------");
                            
                            System.out.printf("| %-2s | %-9s | %-11s | %-7s | %-15s |\n",
                                            i+1,
                                            barang.get(i).getId(),
                                            barang.get(i).getNama(),
                                            barang.get(i).getJumlah(),
                                            formatRupiah.format(barang.get(i).getHarga()));
                            System.out.println("------------------------------------------------------------");

                            System.out.println();
                            System.out.print("**Kosongkan bila tidak inggin diubah.: ");
                            System.out.println();

                            System.out.print("Ganti nama baru: ");
                            String namaBaru = input.nextLine();
                            if (!namaBaru.trim().isEmpty()) {
                                barang.get(i).setNama(namaBaru);
                                System.out.println("Nama barang berhasil dirubah.");
                            }

                            System.out.print("Edit jumlah: ");
                            String editJumlah = input.nextLine();

                            if (!editJumlah.trim().isEmpty()) {
                                try {
                                    int parseJumlah = Integer.parseInt(editJumlah);
                                    if (parseJumlah > 0) {
                                        barang.get(i).setJumlah(parseJumlah);
                                        System.out.println("Jumlah barang berhasil dirubah.");
                                    } else {
                                        System.out.println("Angka yang dimasukan tidak valid.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Jumlah barang harus angka");
                                }
                            }

                            System.out.print("Edit harga: ");
                            String editHarga = input.nextLine();

                            if (!editHarga.trim().isEmpty()) {
                                try {
                                    double parseHarga = Double.parseDouble(editHarga);
                                    if (parseHarga > 0) {
                                        barang.get(i).setHarga(parseHarga);
                                        System.out.println("Harga berhasil diubah.");
                                    } else {
                                        System.out.println("Angka yang dimasukan tidak valid.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Harga barang harus angka");
                                }
                            }
                        }
                    }

                    System.out.println("Enter untuk kembali kemenu...");
                    input.nextLine();
                    
                    break;
                case 3:
                    System.out.println("\n=== Menu List Barang ===");
                    System.out.println();

                    if (barang.isEmpty()) {
                        System.out.println("Data masih kosong.");
                        return;
                    }

                    System.out.println("------------------------------------------------------------");
                    System.out.printf("| %-2s | %-5s | %-7s | %-7s | %-15s |\n",
                                            "No",
                                            "ID Barang",
                                            "Nama Barang",
                                            "Jumlah",
                                            "Harga");
                    System.out.println("------------------------------------------------------------");

                    for (int i = 0; i < barang.size(); i++) {
                            System.out.printf("| %-2s | %-9s | %-11s | %-7s | %-15s |\n",
                                            i+1,
                                            barang.get(i).getId(),
                                            barang.get(i).getNama(),
                                            barang.get(i).getJumlah(),
                                            formatRupiah.format(barang.get(i).getHarga()));
                    }
                    System.out.println("------------------------------------------------------------");
                    
                    System.out.println("Enter untuk kembali kemenu...");
                    input.nextLine();
                    break;
                case 4:
                    System.out.println("=== Menu Hapus ===");
                    System.out.println("Enter untuk kembali kemenu...");
                    input.nextLine();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        }
        System.out.println("Program berhenti..");
        input.close();
    }

}

class Barang {
    String id;
    String nama;
    int jumlah;
    double harga;

    public Barang(String id, String nama, int jumlah, double harga) {
        this.id = id;
        this.nama = nama;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }
    
    public String getNama() {
        return nama;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getHarga() {
        return harga;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return id + "|" +
               nama + "|" +
               jumlah + "|" +
               harga + "|";
    } 
}
