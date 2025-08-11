package model;

public enum JenisElektronik {
    HANDPHONE("Handphone"),
    LAPTOP("Laptop"),
    TV("Tv");

    private final String deskripsi;

    JenisElektronik(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    @Override
    public String toString() {
        return deskripsi;
    }

}

