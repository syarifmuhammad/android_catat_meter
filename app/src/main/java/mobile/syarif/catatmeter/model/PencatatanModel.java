package mobile.syarif.catatmeter.model;

import java.util.Date;

public class PencatatanModel {
    private int id_pencatatan;
    private int pemakaian_terakhir;
    private int pemakaian_bulan_ini;
    private int tarif;
    private String periode;
    private Date tanggal;
    private int pelanggan_id;
    private int status = 0;

    public PencatatanModel(int id_pencatatan, int pemakaian_terakhir, int pemakaian_bulan_ini, int tarif, String periode, Date tanggal, int pelanggan_id) {
        this.id_pencatatan = id_pencatatan;
        this.pemakaian_terakhir = pemakaian_terakhir;
        this.pemakaian_bulan_ini = pemakaian_bulan_ini;
        this.tarif = tarif;
        this.periode = periode;
        this.tanggal = tanggal;
        this.pelanggan_id = pelanggan_id;

    }
    public PencatatanModel(int id_pencatatan, int pemakaian_terakhir, int pemakaian_bulan_ini, int tarif, String periode, Date tanggal, int pelanggan_id, int status) {
        this.id_pencatatan = id_pencatatan;
        this.pemakaian_terakhir = pemakaian_terakhir;
        this.pemakaian_bulan_ini = pemakaian_bulan_ini;
        this.tarif = tarif;
        this.periode = periode;
        this.tanggal = tanggal;
        this.pelanggan_id = pelanggan_id;
        this.status = status;

    }

    public int getId_pencatatan() {
        return id_pencatatan;
    }

    public void setId_pencatatan(Integer id_pencatatan) {
        this.id_pencatatan = id_pencatatan;
    }

    public int getPemakaian_terakhir() {
        return pemakaian_terakhir;
    }

    public void setPemakaian_terakhir(int pemakaian_terakhir) {
        this.pemakaian_terakhir = pemakaian_terakhir;
    }

    public int getPemakaian_bulan_ini() {
        return pemakaian_bulan_ini;
    }

    public void setPemakaian_bulan_ini(int pemakaian_bulan_ini) {
        this.pemakaian_bulan_ini = pemakaian_bulan_ini;
    }

    public int getTarif() {
        return tarif;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getPelanggan_id() {
        return pelanggan_id;
    }

    public void setPelanggan_id(int pelanggan_id) {
        this.pelanggan_id = pelanggan_id;
    }

    @Override
    public String toString() {
        return "PencatatanModel{" +
                "id_pencatatan=" + id_pencatatan +
                ", pemakaian_terakhir=" + pemakaian_terakhir +
                ", pemakaian_bulan_ini=" + pemakaian_bulan_ini +
                ", tarif=" + tarif +
                ", periode='" + periode + '\'' +
                ", tanggal=" + tanggal +
                ", pelanggan_id=" + pelanggan_id +
                ", status=" + status +
                '}';
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
