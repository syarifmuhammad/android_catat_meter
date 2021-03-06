package mobile.syarif.catatmeter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

import mobile.syarif.catatmeter.database.PelangganQueryImplementation;
import mobile.syarif.catatmeter.database.PencatatanQueryImplementation;
import mobile.syarif.catatmeter.database.QueryContract;
import mobile.syarif.catatmeter.database.QueryResponse;
import mobile.syarif.catatmeter.model.PelangganModel;
import mobile.syarif.catatmeter.model.PencatatanModel;

public class DetailPelanggan extends AppCompatActivity {
    private int id;
    private QueryContract.PencatatanQuery query;
    private QueryContract.PelangganQuery queryPelanggan;
    private TextView tv_id_pelanggan;
    private TextView tv_nama;
    private TextView tv_no_telepon;
    private TextView tv_alamat;
    private Integer tarif;
    private TextView tv_periode;
    EditText et_meter_bulan_lalu;
    EditText et_meter_sekarang;
    LinearLayout buttonCatatanMeter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_detail_pelanggan);
        tv_id_pelanggan = findViewById(R.id.tv_id_pelanggan);
        tv_nama = findViewById(R.id.tv_nama);
        tv_no_telepon = findViewById(R.id.tv_no_telepon);
        tv_alamat = findViewById(R.id.tv_alamat);
        tv_periode = findViewById(R.id.tv_periode);
        tv_periode.setText(getPeriode());
        buttonCatatanMeter = findViewById(R.id.buttonConfirmation);
        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailPelanggan.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        id = intent.getIntExtra("id_pelanggan", -1);
        queryPelanggan = new PelangganQueryImplementation();
        setDataPelangganView(intent);
        query = new PencatatanQueryImplementation();
        query.getCurrentPeriodeWhereIdPelanggan(id, new QueryResponse<PencatatanModel>() {
            @Override
            public void onSuccess(PencatatanModel data) {
                et_meter_bulan_lalu = findViewById(R.id.et_meter_bulan_lalu);
                et_meter_sekarang = findViewById(R.id.et_meter_sekarang);
                et_meter_bulan_lalu.setInputType(0);
                getLastPencatatan(id);
                if(data != null){
                    et_meter_sekarang.setText(String.valueOf(data.getPemakaian_bulan_ini()));
                    buttonCatatanMeter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PencatatanModel pencatatan = data;
                            data.setPemakaian_bulan_ini(Integer.parseInt(et_meter_sekarang.getText().toString()));
                            update(pencatatan);
                        }
                    });
                }else{
                    et_meter_sekarang.setText(et_meter_bulan_lalu.getText().toString());
                    buttonCatatanMeter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PencatatanModel pencatatan = prepareInsert(id);
                            insert(pencatatan);
                        }
                    });
                }
            }

            @Override
            public void onFailure(String message) {
                Log.v("ERROR", message);
            }
        });
    }
    private void setDataPelangganView(Intent intent){
        int id = intent.getIntExtra("id_pelanggan", -1);
        queryPelanggan.get(id, new QueryResponse<PelangganModel>() {
            @Override
            public void onSuccess(PelangganModel data) {
                tv_id_pelanggan.setText(String.valueOf(data.getId_pelanggan()));
                tv_nama.setText(data.getNama());
                tv_no_telepon.setText(data.getNo_telepon());
                tv_alamat.setText(data.getAlamat());
                tarif = data.getTarif();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void getLastPencatatan(int id){
        query.getLastWhereIdPelanggan(id, new QueryResponse<PencatatanModel>() {
            @Override
            public void onSuccess(PencatatanModel data) {
                int meter_bulan_lalu = 0;
                if(data != null){
                    meter_bulan_lalu = data.getPemakaian_bulan_ini();
                }
                et_meter_bulan_lalu.setText(String.valueOf(meter_bulan_lalu));
            }

            @Override
            public void onFailure(String message) {
//                recyclerView.setVisibility(View.GONE);
//                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    public PencatatanModel prepareInsert(int pelanggan_id){
        int bulan_lalu = Integer.parseInt(et_meter_bulan_lalu.getText().toString());
        int bulan_sekarang = Integer.parseInt(et_meter_sekarang.getText().toString());
        String periode = getPeriode();
        Date tanggal = new Date();
        return new PencatatanModel(-1, bulan_lalu, bulan_sekarang, tarif, periode, tanggal, pelanggan_id);
    }

    public void insert(PencatatanModel pencatatan){
        query.create(pencatatan, new QueryResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                Intent intent = new Intent(DetailPelanggan.this, TagihanActivity.class);
                intent.putExtra("id_pelanggan", id);
                intent.putExtra("nama", tv_nama.getText());
                intent.putExtra("no_telepon", tv_no_telepon.getText());
                intent.putExtra("alamat", tv_alamat.getText());
                intent.putExtra("tarif", tarif);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                DetailPelanggan.this.startActivity(intent);
            }

            @Override
            public void onFailure(String message) {
//                recyclerView.setVisibility(View.GONE);
//                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });

    }
    public void update(PencatatanModel pencatatan){
        query.update(pencatatan, new QueryResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                Intent intent = new Intent(DetailPelanggan.this, TagihanActivity.class);
                intent.putExtra("id_pelanggan", id);
                intent.putExtra("nama", tv_nama.getText());
                intent.putExtra("no_telepon", tv_no_telepon.getText());
                intent.putExtra("alamat", tv_alamat.getText());
                intent.putExtra("tarif", tarif);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                DetailPelanggan.this.startActivity(intent);
            }

            @Override
            public void onFailure(String message) {
//                recyclerView.setVisibility(View.GONE);
//                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });

    }

    public void updatePelangganActivity(View view){
        Intent intent = new Intent(this, UpdatePelanggan.class);
        intent.putExtra("id_pelanggan", Integer.parseInt(tv_id_pelanggan.getText().toString()));
        intent.putExtra("nama", tv_nama.getText().toString());
        intent.putExtra("no_telepon", tv_no_telepon.getText().toString());
        intent.putExtra("alamat", tv_alamat.getText().toString());
        intent.putExtra("tarif", tarif.intValue());
        this.startActivity(intent);
    }

    private String getPeriode(){
        Calendar calendar = Calendar.getInstance();
        String month = getMonth(calendar.get(Calendar.MONTH));
        int year = calendar.get(Calendar.YEAR);
        return month + " - " + year;
    }


    private String getMonth(int monthValue){
        String[] month = {"JANUARI", "FEBRUARI", "MARET", "APRIL", "MEI", "JUNI", "JULI", "AGUSTUS", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DESEMBER"};
        return month[monthValue];
    }


}