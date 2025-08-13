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
            view.enterToContinue();
            return;
        }

        int itemPage = 10;
        int totalItem = daftarBarang.size();
        int totalHalaman = (int) Math.ceil((double) totalItem / itemPage);
        int halamanIni = 1;

        boolean ulang = true;

        while (ulang) {
            String header = "Daftar Barang";
            int startIndex = (halamanIni - 1) * itemPage;
            // int endIndex = startIndex + (totalHalaman - itemPage);
            int endIndex = Math.min(startIndex + itemPage, totalItem);

            List<Barang> subList = daftarBarang.subList(startIndex, endIndex);
            
            view.header(header);
            view.allBarang(subList, halamanIni, totalHalaman);


            int pilihHalaman = view.getPilihPage();

            switch (pilihHalaman) {
                case 1:
                    if (halamanIni < totalHalaman) {
                        halamanIni++;
                    } else {
                        view.displayMsg("ini adalah halaman terakhir.");
                        view.enterToContinue();
                    }

                    break;
                case 2:
                    if (halamanIni > 1) {
                        halamanIni--;
                    } else {
                        view.displayMsg("ini adalah halaman pertama.");
                        view.enterToContinue();
                    }
                    
                    break;
                case 3:
                    getTambah();
                    break;
                case 4:
                    getHapus();
                    break;
                case 5:
                    getUpdate2();
                    break;
                case 0:
                    ulang = false;
                    break;
                default:
                    view.displayMsg("Pilihan tidak valid");
                    view.enterToContinue();
                    break;
            }
        }


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
        view.enterToContinue();
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
            view.enterToContinue();
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
            view.enterToContinue();
        }

    }

    public void getHapus() {
        view.header("Hapus Data Barang");
        List<Barang> daftarBarang = service.getAllData();

        if (daftarBarang.isEmpty()) {
            view.displayMsg("\nData masih kosong.");
            view.enterToContinue();
            return;
        }

        int itemPage = 10;
        int totalItem = daftarBarang.size();
        int totalHalaman = (int) Math.ceil((double) totalItem / itemPage);
        int halamanIni = 1;

        boolean ulang = true;

        while (ulang) {
            String header = "Hapus Barang";
            int startIndex = (halamanIni - 1) * itemPage;
            int endIndex = Math.min(startIndex + itemPage, totalItem);

            List<Barang> subList = daftarBarang.subList(startIndex, endIndex);
            
            view.header(header);
            view.allBarang(subList, halamanIni, totalHalaman);
            
            view.displayMsg("\nKetik (N) Selanjutnya");
            view.displayMsg("Ketik (P) Sebelumnya");
            view.displayMsg("Ketik enter untuk kembali");
            view.displayMsg("Pilih nomor (" + (startIndex +1)+ " - " + endIndex + ") yang inggin dihapus");
            
            int nomor = -1;
            try {
                String indexInput = view.getFormPolos("\n**Pilihan anda : ");
                
                if(indexInput.equalsIgnoreCase("n")) {
                    if (halamanIni < totalHalaman) {
                            halamanIni++;
                    } else {
                        view.displayMsg("ini adalah halaman terakhir.");
                        view.enterToContinue();
                    }
                } else if (indexInput.equalsIgnoreCase("p")) {
                    if (halamanIni > 1) {
                            halamanIni--;
                    } else {
                        view.displayMsg("ini adalah halaman pertama.");
                        view.enterToContinue();
                    }
                } else if (indexInput.trim().isEmpty()) {
                    return;
                } else {
                    nomor = Integer.parseInt(indexInput);

                    if (nomor >= 1 && nomor <= daftarBarang.size()) {
                         Barang hapusBarang = daftarBarang.get(nomor -1);

                         view.displayMsg("Hapus Data: " + hapusBarang.getId());
                         int konfirm = view.getFormInt("\nYakin hapus ?\n1. Ya hapus\n2. Batal\nPilih: ");

                         if (konfirm == 1) {
                                boolean berhasil = service.hapusData(hapusBarang.getId());
                                if(berhasil) {
                                    view.displayMsg("berhasil dihapus: " + hapusBarang.getId());
                                    view.enterToContinue();
                                }
                            } else {
                                view.displayMsg("Dibatalkan.");
                            }
                    } else {
                        view.displayMsg("Pilihan tidak valid.");
                    }
                }
            } catch (Exception e) {
                view.displayMsg("Terjadi kesalahan: " + e.getMessage());
            }
        }
        view.enterToContinue();
    }

    public void getUpdate2() {
        view.header("Update Data Barang");
        List<Barang> daftarBarang = service.getAllData();

        if (daftarBarang.isEmpty()) {
            view.displayMsg("\nData masih kosong.");
            view.enterToContinue();
            return;
        }

        int itemPage = 10;
        int totalItem = daftarBarang.size();
        int totalHalaman = (int) Math.ceil((double) totalItem / itemPage);
        int halamanIni = 1;

        boolean ulang = true;

        while (ulang) {
            String header = "Update Data Barang";
            int startIndex = (halamanIni - 1) * itemPage;
            int endIndex = Math.min(startIndex + itemPage, totalItem);

            List<Barang> subList = daftarBarang.subList(startIndex, endIndex);
            
            view.header(header);
            view.allBarang(subList, halamanIni, totalHalaman);
            
            view.displayMsg("\nKetik (N) Selanjutnya");
            view.displayMsg("Ketik (P) Sebelumnya");
            view.displayMsg("Ketik enter untuk kembali");
            view.displayMsg("Pilih nomor (" + (startIndex +1)+ " - " + endIndex + ") data yang ingin dirubah");
            
            int nomor = -1;
            try {
                String indexInput = view.getFormPolos("\n**Pilihan anda : ");
                
                if(indexInput.equalsIgnoreCase("n")) {
                    if (halamanIni < totalHalaman) {
                            halamanIni++;
                    } else {
                        view.displayMsg("ini adalah halaman terakhir.");
                        view.enterToContinue();
                    }
                } else if (indexInput.equalsIgnoreCase("p")) {
                    if (halamanIni > 1) {
                            halamanIni--;
                    } else {
                        view.displayMsg("ini adalah halaman pertama.");
                        view.enterToContinue();
                    }
                } else if (indexInput.trim().isEmpty()) {
                    return;
                } else {
                    nomor = Integer.parseInt(indexInput);

                    if (nomor >= 1 && nomor <= daftarBarang.size()) {
                         Barang ubahData = daftarBarang.get(nomor -1);

                         view.displayMsg("Rubah Data: " + ubahData.getId());
                         int konfirm = view.getFormInt("\nYakin dirubah ?\n1. Ya Rubah\n2. Batal\nPilih: ");

                         if (konfirm == 1) {
                                view.displayMsg("");
                                view.displayMsg("**Biarkan kosong jika tidak ingin mengubah.");
                                view.displayMsg("");

                                try {
                                    String merkBaru = view.getFormUpadate("Rubah Merk: ");
                                    String jumlahBaru = view.getFormUpadate("Rubah Jumlah: ");
                                    String hargaBaru = view.getFormUpadate("Rubah Harga: ");
                                    
                                    String pesan = service.updateData(ubahData.getId(), merkBaru, jumlahBaru, hargaBaru);
                                    view.displayMsg(pesan);
                                } catch (IllegalArgumentException e) {
                                    view.displayMsg("Error: " + e.getMessage());
                                } finally {
                                    view.enterToContinue();
                                }
                            } else {
                                view.displayMsg("Dibatalkan.");
                            }
                    } else {
                        view.displayMsg("Pilihan tidak valid.");
                    }
                }
            } catch (Exception e) {
                view.displayMsg("Terjadi kesalahan: " + e.getMessage());
            }
        }
        view.enterToContinue();
        

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
