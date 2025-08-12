package model;

public enum JenisPakaian {
    KAOS("Kaos"),
    BAJU("Baju");

    private final String deskripsi;

    JenisPakaian(String deskripsi) {
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
