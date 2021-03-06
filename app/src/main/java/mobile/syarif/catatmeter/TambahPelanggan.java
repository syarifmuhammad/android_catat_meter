package mobile.syarif.catatmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import mobile.syarif.catatmeter.database.PelangganQueryImplementation;
import mobile.syarif.catatmeter.database.QueryContract;
import mobile.syarif.catatmeter.database.QueryResponse;
import mobile.syarif.catatmeter.model.PelangganModel;

public class TambahPelanggan extends AppCompatActivity {
    private QueryContract.PelangganQuery query;
    private EditText et_nama;
    private EditText et_no_telepon;
    private EditText et_tarif;
    private EditText et_alamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_tambah_pelanggan);
        query = new PelangganQueryImplementation();
        et_nama = findViewById(R.id.et_nama);
        et_no_telepon = findViewById(R.id.et_no_telepon);
        et_tarif = findViewById(R.id.et_tarif);
        et_alamat = findViewById(R.id.et_alamat);
        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahPelanggan.super.onBackPressed();
            }
        });
    }


    public void tambahPelangganAction(View view) {
        PelangganModel create = new PelangganModel(-1, et_nama.getText().toString(), et_no_telepon.getText().toString(), Integer.parseInt(et_tarif.getText().toString()), et_alamat.getText().toString());
        query.create(create, new QueryResponse<Boolean>(){

            @Override
            public void onSuccess(Boolean data) {
                Toast.makeText(TambahPelanggan.this, "Berhasil menambah pelanggan baru", Toast.LENGTH_LONG).show();
                et_nama.setText("");
                et_no_telepon.setText("");
                et_tarif.setText("");
                et_alamat.setText("");
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(TambahPelanggan.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}