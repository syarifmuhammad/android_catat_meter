package mobile.syarif.catatmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.Calendar;

import mobile.syarif.catatmeter.database.PelangganQueryImplementation;
import mobile.syarif.catatmeter.database.QueryContract;
import mobile.syarif.catatmeter.database.QueryResponse;

public class DashboardActivity extends AppCompatActivity {
    private String periode;
    private int total_pelanggan;
    private TextView tv_total_pelanggan;
    private TextView tv_total_pelanggan_tercatat;
    private TextView tv_total_pelanggan_belum_tercatat;
    private TextView tv_periode;
    private QueryContract.PelangganQuery query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_dashboard);

        query = new PelangganQueryImplementation();

        tv_total_pelanggan = findViewById(R.id.tv_tagihan);
        tv_total_pelanggan_tercatat = findViewById(R.id.tv_total_pelanggan_tercatat);
        tv_total_pelanggan_belum_tercatat = findViewById(R.id.tv_total_pelanggan_belum_tercatat);
        tv_periode = findViewById(R.id.tv_periode);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setDataDashboard();
    }

    public void activityCariPelanggan(View view) {
        Intent intent = new Intent(this, SearchPelangganActivity.class);
        startActivity(intent);
    }

    public void activityTambahPelanggan(View view) {

        Intent intent = new Intent(this, TambahPelanggan.class);
        startActivity(intent);
    }

    public void setDataDashboard(){
        setTotalPelanggan();
        setPeriode();
    }

    private void setTotalPelanggan(){
        query.count(new QueryResponse<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                total_pelanggan = data;
                tv_total_pelanggan.setText(data.toString());
                setTotalPelangganTercatat(data);
            }

            @Override
            public void onFailure(String message) {
//                recyclerView.setVisibility(View.GONE);
//                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });
    }
    private void setTotalPelangganTercatat(int total){
        query.countTercatat(new QueryResponse<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                tv_total_pelanggan_tercatat.setText(String.valueOf(data));
                setTotalPelangganBelumTercatat(total - data);
            }

            @Override
            public void onFailure(String message) {
//                recyclerView.setVisibility(View.GONE);
//                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setTotalPelangganBelumTercatat(int total){
        tv_total_pelanggan_belum_tercatat.setText(String.valueOf(total));
    }

    private void setPeriode(){
        Calendar calendar = Calendar.getInstance();
        String month = getMonth(calendar.get(Calendar.MONTH));
        Integer year = calendar.get(Calendar.YEAR);
        periode = month + " - " + year.toString();
        tv_periode.setText(periode);
    }


    private String getMonth(int monthValue){
        String[] month = {"JANUARI", "FEBRUARI", "MARET", "APRIL", "MEI", "JUNI", "JULI", "AGUSTUS", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DESEMBER"};
        return month[monthValue];
    }
}