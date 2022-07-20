package mobile.syarif.catatmeter.model;

public class PelangganModel {
    private int id_pelanggan;
    private String nama;
    private String no_telepon;
    private String alamat;
    private int tarif;
    private boolean tercatat = false;

    public PelangganModel(int id_pelanggan, String nama, String no_telepon, int tarif, String alamat) {
        this.id_pelanggan = id_pelanggan;
        this.nama = nama;
        this.no_telepon = no_telepon;
        this.alamat = alamat;
        this.tarif = tarif;
    }
    public PelangganModel(int id_pelanggan, String nama, String no_telepon, int tarif, String alamat, boolean tercatat) {
        this.id_pelanggan = id_pelanggan;
        this.nama = nama;
        this.no_telepon = no_telepon;
        this.alamat = alamat;
        this.tarif = tarif;
        this.tercatat = tercatat;
    }

    public int getId_pelanggan() {
        return id_pelanggan;
    }
    public void setId_pelanggan(Integer id_pelanggan) {
        id_pelanggan = id_pelanggan;
    }

    public String getNama() {
        return nama;
    }
    public String getNo_telepon() { return no_telepon; }

    public String getAlamat() {
        return alamat;
    }
    public int getTarif() { return tarif; }

    public boolean isTercatat() { return tercatat; }
    public void setTercatat(boolean tercatat) { tercatat = tercatat; }
}
