package mobile.syarif.catatmeter.model;

public class PelangganModel {
    private Integer id_pelanggan;
    private String nama;
    private String alamat;

    public PelangganModel(Integer id_pelanggan, String nama, String alamat) {
        this.id_pelanggan = id_pelanggan;
        this.nama = nama;
        this.alamat = alamat;
    }

    public Integer getId_pelanggan() {
        return id_pelanggan;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }
}
