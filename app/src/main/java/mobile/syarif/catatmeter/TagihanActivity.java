package mobile.syarif.catatmeter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mobile.syarif.catatmeter.database.PelangganQueryImplementation;
import mobile.syarif.catatmeter.database.PencatatanQueryImplementation;
import mobile.syarif.catatmeter.database.QueryContract;
import mobile.syarif.catatmeter.database.QueryResponse;
import mobile.syarif.catatmeter.model.PelangganModel;
import mobile.syarif.catatmeter.model.PencatatanModel;

public class TagihanActivity extends AppCompatActivity {
    private int id;
    private PencatatanModel pencatatan;
    private QueryContract.PencatatanQuery query;
    private QueryContract.PelangganQuery queryPelanggan;
    private TextView tv_id_pelanggan;
    private TextView tv_nama;
    private TextView tv_no_telepon;
    private TextView tv_alamat;
    private Integer tarif;
    private TextView tv_periode;
    private TextView tv_calculate;
    private TextView tv_payments;
    private TextView tv_note;
    private TextView tv_status;
    EditText et_meter_bulan_lalu;
    EditText et_meter_sekarang;
    LinearLayout buttonConfirmation;
    private TextView tv_buttonConfirmation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_tagihan);
        tv_id_pelanggan = findViewById(R.id.tv_id_pelanggan);
        tv_nama = findViewById(R.id.tv_nama);
        tv_no_telepon = findViewById(R.id.tv_no_telepon);
        tv_alamat = findViewById(R.id.tv_alamat);
        tv_calculate = findViewById(R.id.tv_calculate);
        tv_payments = findViewById(R.id.tv_payments);
        tv_note = findViewById(R.id.tv_note);
        tv_status = findViewById(R.id.tv_status);
        tv_periode = findViewById(R.id.tv_periode);
        tv_periode.setText(getPeriode());
        buttonConfirmation = findViewById(R.id.buttonConfirmation);
        tv_buttonConfirmation = findViewById(R.id.tv_buttonConfirmation);
        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TagihanActivity.super.onBackPressed();
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
            @SuppressLint("ResourceAsColor")
            @Override
            public void onSuccess(PencatatanModel data) {
                if(data != null){
                    pencatatan = data;
                    int sisa = data.getPemakaian_bulan_ini() - data.getPemakaian_terakhir();
                    String calculate = data.getPemakaian_bulan_ini() + " - " + data.getPemakaian_terakhir() + " = " + sisa + " m3";
                    tv_calculate.setText(calculate);
                    String total = formatRupiah(Double.valueOf(sisa * tarif));
                    tv_payments.setText(total);
                    String note = "Note : Tarif (" + formatRupiah(Double.valueOf(tarif)) + " / m3)";
                    tv_note.setText(note);
                    int setStatus = 0;
                    if(data.getStatus() == 0){
                        tv_status.setText("Belum Lunas");
                        setStatus = 1;
                    }else{
                        tv_status.setText("Lunas");
                        tv_status.setTextColor(getResources().getColor(R.color.text_success));
                        buttonConfirmation.setBackgroundResource(R.drawable.bg_danger_rounded_30);
                        tv_buttonConfirmation.setText("BATALKAN STATUS LUNAS");
                    }
                    int finalSetStatus = setStatus;
                    buttonConfirmation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view){
                            data.setStatus(finalSetStatus);
                            query.update(data, new QueryResponse<Boolean>() {
                                @Override
                                public void onSuccess(Boolean data) {
                                    recreate();
                                }

                                @Override
                                public void onFailure(String message) {
                                }
                            });
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
                Log.d("meter_bulan_lalu", String.valueOf(meter_bulan_lalu));
                et_meter_bulan_lalu.setText(String.valueOf(meter_bulan_lalu));
            }

            @Override
            public void onFailure(String message) {
//                recyclerView.setVisibility(View.GONE);
//                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void ubahPencatatan(View view){
        Intent intent;
        intent = new Intent(TagihanActivity.this, DetailPelanggan.class);
        intent.putExtra("id_pelanggan", Integer.valueOf(tv_id_pelanggan.getText().toString()));
        intent.putExtra("nama", tv_nama.getText().toString());
        intent.putExtra("no_telepon", tv_no_telepon.getText().toString());
        intent.putExtra("alamat", tv_alamat.getText().toString());
        intent.putExtra("tarif", tarif);
        TagihanActivity.this.startActivity(intent);
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

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }


}