package mobile.syarif.catatmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import mobile.syarif.catatmeter.database.PelangganQueryImplementation;
import mobile.syarif.catatmeter.database.QueryContract;
import mobile.syarif.catatmeter.database.QueryResponse;
import mobile.syarif.catatmeter.model.PelangganModel;

public class UpdatePelanggan extends AppCompatActivity {
    private QueryContract.PelangganQuery query;
    private int id;
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
        setContentView(R.layout.activity_update_pelanggan);
        query = new PelangganQueryImplementation();
        et_nama = findViewById(R.id.et_nama);
        et_no_telepon = findViewById(R.id.et_no_telepon);
        et_tarif = findViewById(R.id.et_tarif);
        et_alamat = findViewById(R.id.et_alamat);
        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdatePelanggan.super.onBackPressed();
            }
        });

        Intent intent = getIntent();
        id = intent.getIntExtra("id_pelanggan", -1);
        setDataPelangganView(intent);
    }

    private void setDataPelangganView(Intent intent){
        et_nama.setText(intent.getStringExtra("nama"));
        et_no_telepon.setText(intent.getStringExtra("no_telepon"));
        et_alamat.setText(intent.getStringExtra("alamat"));
        int tarif = intent.getIntExtra("tarif", 0);
        et_tarif.setText(String.valueOf(tarif));
    }



    public void updatePelangganAction(View view) {
        PelangganModel pelanggan = new PelangganModel(id, et_nama.getText().toString(), et_no_telepon.getText().toString(), Integer.parseInt(et_tarif.getText().toString()), et_alamat.getText().toString());
        query.update(pelanggan, new QueryResponse<Boolean>(){

            @Override
            public void onSuccess(Boolean data) {
                Toast.makeText(UpdatePelanggan.this, "Berhasil mengedit pelanggan", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(UpdatePelanggan.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}