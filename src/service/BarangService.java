package service;

import helper.DbHelper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Barang;
import model.Elektronik;
import model.JenisElektronik;
import model.JenisPakaian;
import model.JenisSize;
import model.Pakaian;

public class BarangService {
    private List<Barang> daftarBarang = new ArrayList<>();
    private ArrayList<Pakaian> pakaian = new ArrayList<>();
    private ArrayList<Elektronik> elektronik = new ArrayList<>();

    public String generateId() {
        return "B" + String.format("%03d", daftarBarang.size()+1);
    }

    public Barang cariId(String keyword) {
        for (int i = 0; i < daftarBarang.size(); i++) {
            if (daftarBarang.get(i).getId().equalsIgnoreCase(keyword)) {
                return daftarBarang.get(i);
            }
        }
        return null;
    }

    public List<Barang> getAllData() {
        return daftarBarang;
    }

    public List<Elektronik> getAllElektronik() {
        return elektronik;
    }

    public List<Pakaian> getAllPakaian() {
        return pakaian;
    }

    public void tambahData(Barang barang) {
        // daftarBarang.add(barang);
        try (Connection conn = DbHelper.getConnection()) {
            conn.setAutoCommit(false);

            String sqlBarang = "INSERT INTO barang (id, merk, stok, harga, kategori) VALUES (?,?,?,?,?)";
            PreparedStatement pstmtBarang = conn.prepareStatement(sqlBarang);
            pstmtBarang.setString(1, barang.getId());
            pstmtBarang.setString(2, barang.getMerk());
            pstmtBarang.setInt(3, barang.getStok());
            pstmtBarang.setDouble(4, barang.getHarga());
            pstmtBarang.setString(5, barang.getKategori().split("-")[0]);
            pstmtBarang.executeUpdate();

            if (barang instanceof Elektronik) {
                Elektronik elektronik = (Elektronik) barang;
                String sqlElektronik = "INSERT INTO elektronik (id_barang, model, jenis) VALUES (?,?,?)";
                PreparedStatement pstmtElektronik = conn.prepareStatement(sqlElektronik);
                pstmtElektronik.setString(1, elektronik.getId());
                pstmtElektronik.setString(2, elektronik.getModel());
                pstmtElektronik.setString(3, elektronik.getJenis().name());
                pstmtElektronik.executeUpdate();

            } else if (barang instanceof Pakaian) {
                Pakaian pakaian = (Pakaian) barang;
                String sqlPakaian = "INSERT INTO pakaian (id_barang, size, jenis) VALUES (?,?,?)";
                PreparedStatement pstmtPakaian = conn.prepareStatement(sqlPakaian);
                pstmtPakaian.setString(1, pakaian.getId());
                pstmtPakaian.setString(2, pakaian.getSize().name());
                pstmtPakaian.setString(3, pakaian.getJenis().name());
                pstmtPakaian.executeUpdate();
                
            }

            conn.commit();
            daftarBarang.add(barang);
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan data ke database: " + e.getMessage());
            try (Connection conn = DbHelper.getConnection()) {
                conn.rollback();
            } catch (SQLException rollbackEx) {
               System.err.println("Rollback gagal: " + rollbackEx.getMessage());
            }
        }
    }

    public String updateElektronik(String id, String merkBaru, String stokBaru, String hargaBaru, String modelBaru, JenisElektronik jenisBaru) {
        StringBuilder pesan = new StringBuilder();

        try (Connection conn = DbHelper.getConnection()) {
            conn.setAutoCommit(false);

            String sqlUpdateBarang = "UPDATE barang SET merk = ?, stok = ?, harga = ? WHERE id = ?";
            PreparedStatement pstmtBarang = conn.prepareStatement(sqlUpdateBarang);
            pstmtBarang.setString(1, merkBaru);
            pstmtBarang.setInt(2, Integer.parseInt(stokBaru));
            pstmtBarang.setDouble(3, Double.parseDouble(hargaBaru));
            pstmtBarang.setString(4, id);
            pstmtBarang.executeUpdate();

            String sqlUpdateElektronik = "UPDATE eleketronik SET model = ?, jenis = ? WHERE id_barang = ?";
            PreparedStatement pstmtElektronik = conn.prepareStatement(sqlUpdateElektronik);
            pstmtElektronik.setString(1, modelBaru);
            pstmtElektronik.setString(2, jenisBaru.name());
            pstmtElektronik.setString(3, id);
            pstmtElektronik.executeUpdate();

            conn.commit();
            pesan.append("Data Elektronik berhasil diupdate.");
            loadDb();

        } catch (SQLException e) {
            try (Connection conn = DbHelper.getConnection()) {
                conn.rollback();
            } catch (SQLException ex) {
               System.err.println("Rollback gagal: " + ex.getMessage());
            }
            pesan.append("Gagal mengupdate data: " + e.getMessage());
        } catch (NumberFormatException e) {
            pesan.append("Error: Stok atau harga harus berupa angka.");
        }
        return pesan.toString();
    }

    public String updatePakaian(String id, String merkBaru, String stokBaru, String hargaBaru, JenisSize sizeBaru, JenisPakaian jenisBaru) {
        StringBuilder pesan = new StringBuilder();
        try (Connection conn = DbHelper.getConnection()) {
            conn.setAutoCommit(false);
            
            String sqlUpdateBarang = "UPDATE barang SET merk = ?, stok = ?, harga = ? WHERE id = ?";
            PreparedStatement pstmtBarang = conn.prepareStatement(sqlUpdateBarang);
            pstmtBarang.setString(1, merkBaru);
            pstmtBarang.setInt(2, Integer.parseInt(stokBaru));
            pstmtBarang.setDouble(3, Double.parseDouble(hargaBaru));
            pstmtBarang.setString(4, id);
            pstmtBarang.executeUpdate();
            
            String sqlUpdatePakaian = "UPDATE pakaian SET size = ?, jenis = ? WHERE id_barang = ?";
            PreparedStatement pstmtPakaian = conn.prepareStatement(sqlUpdatePakaian);
            pstmtPakaian.setString(1, sizeBaru.name());
            pstmtPakaian.setString(2, jenisBaru.name());
            pstmtPakaian.setString(3, id);
            pstmtPakaian.executeUpdate();
            
            conn.commit();
            pesan.append("Data Pakaian berhasil diupdate.");
            loadDb();
        } catch (SQLException e) {
            try (Connection conn = DbHelper.getConnection()) { conn.rollback(); } catch (SQLException ex) {}
            pesan.append("Gagal mengupdate data: " + e.getMessage());
        } catch (NumberFormatException e) {
            pesan.append("Error: Stok atau harga harus berupa angka.");
        }
        return pesan.toString().trim();
    }
    
    public void tambahPakaian(List<Pakaian> listPakaian) {
        daftarBarang.addAll(listPakaian);
    }

    public void tambahElektronik(List<Elektronik> listElektronik) {
        daftarBarang.addAll(listElektronik);
    }

    public String updateData(String id, String merkBaru, String stokBaru, String hargaBaru) {
        Barang exBarang = cariId(id);
        StringBuilder pesan = new StringBuilder();

        if (!merkBaru.trim().isEmpty()) {
            exBarang.setMerk(merkBaru);
            pesan.append("\nMerk Barang berhasil dirubah.");
        }

        if (!stokBaru.trim().isEmpty()) {
            try {
                int parseStok = Integer.parseInt(stokBaru);
                if (parseStok > 0) {
                    exBarang.setStok(parseStok);
                    pesan.append("\nstok barang berhasil dirubah");
                } else {
                    pesan.append("\nAngka yang dimasukan tidak valid.");
                }
            } catch (Exception e) {
                pesan.append("\nstok barang harus angka " + e.getMessage());
            }
        }

        if (!hargaBaru.trim().isEmpty()) {
            try {
                double parseHarga = Double.parseDouble(hargaBaru);
                if (parseHarga > 0) {
                    exBarang.setHarga(parseHarga);;
                    pesan.append("\nHarga berhasil diubah.");
                } else {
                    pesan.append("\nAngka yang dimasukan tidak valid.");
                }
            } catch (Exception e) {
                pesan.append("\nGagal diperbarui: Harga barang harus angka " + e.getMessage());
            }
        }
        return pesan.toString().trim();
    }

    public boolean hapusData(String id) {
        try (Connection conn = DbHelper.getConnection()) {
            String sql = "DELETE FROM barang WHERE id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            int hapusBaris = pstmt.executeUpdate();
            if (hapusBaris > 0) {
                for (int i = 0; i < daftarBarang.size(); i++) {
                    if (daftarBarang.get(i).getId().equals(id)) {
                        daftarBarang.remove(i);
                        break;
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Gagal menghapus data dari database: " + e.getMessage());
        }
        return false;
    }

    
    

    // public String updateElektronik(String id, String merkBaru, String stokBaru, String hargaBaru, String modelBaru, JenisElektronik jenisBaru) {
    //     Barang exBarang = cariId(id);
    //     StringBuilder pesan = new StringBuilder();

        
    //     if (exBarang instanceof Elektronik) {
    //         Elektronik dataElektronik = (Elektronik) exBarang;
            
    //         pesan.append(updateData(id, merkBaru, stokBaru, hargaBaru));
            
    //         if (!modelBaru.trim().isEmpty()) {
    //             dataElektronik.setModel(modelBaru);
    //             pesan.append("\nModel Barang berhasil dirubah.");
    //         }

    //         if(jenisBaru != null && !dataElektronik.getJenis().equals(jenisBaru)) {
    //             dataElektronik.setJenis(jenisBaru);
    //             pesan.append("\nJenis Elektronik berhasil dirubah.");
    //         }
    //     } else {
    //         return "Barang dengan ID " + id + " bukan kategori Elektronik.";
    //     }

    //     return pesan.toString().trim();
    // }
    
    // public String updatePakaian(String id, String merkBaru, String stokBaru, String hargaBaru, JenisSize sizeBaru, JenisPakaian jenisBaru) {
    //     Barang exBarang = cariId(id);
    //     StringBuilder pesan = new StringBuilder();

    //     if (exBarang instanceof Pakaian) {
    //         Pakaian dataPakaian = (Pakaian) exBarang;

    //         pesan.append(updateData(id, merkBaru, stokBaru, hargaBaru));

    //         if (sizeBaru != null && !dataPakaian.getSize().equals(sizeBaru)) {
    //             dataPakaian.setSize(sizeBaru);
    //             pesan.append("\nUkuran Pakaian berhasil dirubah.");
    //         }

    //         if (jenisBaru != null && !dataPakaian.getJenis().equals(jenisBaru)) {
    //             dataPakaian.setJenis(jenisBaru);
    //             pesan.append("\nJenis Pakaian berhasil dirubah.");
    //         }

    //     } else {
    //         return "Barang dengan ID " + id + " bukan kategori Pakaian.";
    //     }

    //     return pesan.toString().trim();
    // }

    // public boolean hapusData(String id) {
    //     Barang exBarang = cariId(id);

    //     if(exBarang != null) {
    //         daftarBarang.remove(exBarang);
    //         return true;
    //     } else {
    //         return false;
    //     }

    // }

    // public String bacaData(String path) {
    //     StringBuilder logPesan = new StringBuilder();
    //     daftarBarang.clear();

    //     try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
    //         String line;

    //         logPesan.append("Memulai membaca data dari file...\n");
    //         while ((line = reader.readLine()) != null) {
    //             String[] parts = line.split("\\|");

    //             try {
    //                 if (path.contains("elektronik.txt")) {
    //                 String id = parts[0];
    //                 String merk = parts[1];
    //                 int stok = Integer.parseInt(parts[2]);
    //                 double harga = Double.parseDouble(parts[3]);
    //                 String model = parts[4];
    //                 JenisElektronik jenis = JenisElektronik.valueOf(parts[5].toUpperCase());

    //                 Elektronik daftarElektronik = new Elektronik(id, merk, stok, harga, model, jenis);
    //                 elektronik.add(daftarElektronik);
                    
    //                 } else if(path.contains("pakaian.txt")) {
    //                     String id = parts[0];
    //                     String merk = parts[1];
    //                     int stok = Integer.parseInt(parts[2]);
    //                     double harga = Double.parseDouble(parts[3]);
    //                     JenisSize size = JenisSize.valueOf(parts[4].toUpperCase());
    //                     JenisPakaian jenis = JenisPakaian.valueOf(parts[5].toUpperCase());
                        
    //                     Pakaian daftarPakaian = new Pakaian(id, merk, stok, harga, size, jenis);
    //                     pakaian.add(daftarPakaian);

    //                 } else {
    //                     logPesan.append("Warning: Invalid line, skipped: " + line);
    //                 }
                    
    //             } catch (NumberFormatException e) {
    //                 logPesan.append("Error: Gagal mengonversi angka pada baris: " + line + ". Kesalahan: " + e.getMessage() + "\n");
    //             } catch (IllegalArgumentException e) {
    //                 logPesan.append("Error: Nilai enum tidak valid pada baris: " + line + ". Kesalahan: " + e.getMessage() + "\n");
    //             }
    //         }
    //         logPesan.append("Selesai membaca data. Total Elektronik: " + elektronik.size() + ", Total Pakaian: " + pakaian.size());
    //     } catch (IOException e) {
    //         logPesan.append("Error reading file: " + e.getMessage());
    //     }
    //     return logPesan.toString().trim();
    // }

    public String loadDb() {
        daftarBarang.clear();
        String pesan = "";

        try (Connection conn = DbHelper.getConnection()) {
            Statement stmt = conn.createStatement();
            
            String sqlBarang = "SELECT * FROM barang";
            ResultSet rsBarang = stmt.executeQuery(sqlBarang);

            while (rsBarang.next()) {
                String id = rsBarang.getString("id");
                String merk = rsBarang.getString("merk");
                int stok = rsBarang.getInt("stok");
                double harga = rsBarang.getDouble("harga");
                String kategori = rsBarang.getString("kategori");

                if (kategori.equals("Elektronik")) {
                    String sqlElektronik = "SELECT model, jenis FROM elektronik WHERE id_barang = ?";
                    PreparedStatement psmt = conn.prepareStatement(sqlElektronik);
                    psmt.setString(1, id);
                    ResultSet rsElektronik = psmt.executeQuery();
                    
                    if (rsElektronik.next()) {
                        String model = rsElektronik.getString("model");
                        JenisElektronik jenis = JenisElektronik.valueOf(rsElektronik.getString("jenis"));
                        daftarBarang.add(new Elektronik(id, merk, stok, harga, model, jenis));
                    }
                    
                } else if (kategori.equals("Pakaian")) {
                    String sqlPakaian = "SELECT size, jenis FROM pakaian WHERE id_barang = ?";
                    PreparedStatement psmt = conn.prepareStatement(sqlPakaian);
                    psmt.setString(1, id);
                    ResultSet rsPakaian = psmt.executeQuery();

                    if (rsPakaian.next()) {
                        JenisSize size = JenisSize.valueOf(rsPakaian.getString("size"));
                        JenisPakaian jenis = JenisPakaian.valueOf(rsPakaian.getString("jenis"));
                        daftarBarang.add(new Pakaian(id, merk, stok, harga, size, jenis));
                    }
                }
                
            }
            pesan = "Data berhasil dimuat dari database.";
        } catch (SQLException e) {
            pesan = "Gagal memuat data dari database: " + e.getMessage();
        }
        return pesan;
    }

    public String migrateElektronik() {
        String path = "data/elektronik.txt";
        String pesan = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                try {
                    String id = parts[0];
                    String merk = parts[1];
                    int stok = Integer.parseInt(parts[2]);
                    double harga = Double.parseDouble(parts[3]);
                    String model = parts[4];
                    JenisElektronik jenis = JenisElektronik.valueOf(parts[5].toUpperCase());

                    Elektronik newElektronik = new Elektronik(id, merk, stok, harga, model, jenis);
                    tambahData(newElektronik);
                } catch (Exception e) {
                    pesan = "Error membaca baris: " + line + ". Kesalahan: " + e.getMessage();
                    return pesan;
                }
            }
            pesan = "Migrasi data elektronik berhasil.";
        } catch (IOException e) {
            pesan = "Gagal membaca file: " + path + ". Kesalahan: " + e.getMessage();
        }
        return pesan;
    }

    public String migratePakaian() {
        String path = "data/pakaian.txt";
        String pesan = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                try {
                    String id = parts[0];
                    String merk = parts[1];
                    int stok = Integer.parseInt(parts[2]);
                    double harga = Double.parseDouble(parts[3]);
                    JenisSize size = JenisSize.valueOf(parts[4].toUpperCase());
                    JenisPakaian jenis = JenisPakaian.valueOf(parts[5].toUpperCase());

                    Pakaian newPakaian = new Pakaian(id, merk, stok, harga, size, jenis);
                    tambahData(newPakaian);
                } catch (Exception e) {
                    pesan = "Error membaca baris: " + line + ". Kesalahan: " + e.getMessage();
                    return pesan;
                }
            }
            pesan = "Migrasi data pakaian berhasil.";
        } catch (IOException e) {
            pesan = "Gagal membaca file: " + path + ". Kesalahan: " + e.getMessage();
        }
        return pesan;
    }
}
