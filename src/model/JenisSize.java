package model;

public enum JenisSize {
    S("s"),
    M("m"),
    L("l"),
    XL("Xl"),
    XXL("Xxl");

    private final String deskripsi;

    JenisSize(String deskripsi) {
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
