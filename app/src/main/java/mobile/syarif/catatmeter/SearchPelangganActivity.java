package mobile.syarif.catatmeter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import mobile.syarif.catatmeter.adapter.PelangganAdapter;
import mobile.syarif.catatmeter.database.PelangganQueryImplementation;
import mobile.syarif.catatmeter.database.QueryContract;
import mobile.syarif.catatmeter.database.QueryResponse;
import mobile.syarif.catatmeter.model.PelangganModel;

public class SearchPelangganActivity extends AppCompatActivity {
    private List<PelangganModel> dataPelanggan = new ArrayList<>();
    private PelangganAdapter pelangganAdapter;
    private RecyclerView recyclerViewPelanggan;
    private EditText et_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_search_pelanggan);
        et_search = findViewById(R.id.et_search);
        recyclerViewPelanggan = findViewById(R.id.recyclerViewPelanggan);
        pelangganAdapter = new PelangganAdapter(this, dataPelanggan);
        recyclerViewPelanggan.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPelanggan.setAdapter(pelangganAdapter);
        searchAction();
        getDataPelanggan();
        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchPelangganActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        QueryContract.PelangganQuery query = new PelangganQueryImplementation();
        query.searchByIdOrName(et_search.getText().toString(), new QueryResponse<List<PelangganModel>>() {
            @Override
            public void onSuccess(List<PelangganModel> data) {
                dataPelanggan.clear();
                dataPelanggan.addAll(data);
                pelangganAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(SearchPelangganActivity.this, message, Toast.LENGTH_LONG);
            }
        });
    }

    private void getDataPelanggan(){
        QueryContract.PelangganQuery query = new PelangganQueryImplementation();
        query.all(new QueryResponse<List<PelangganModel>>() {
            @Override
            public void onSuccess(List<PelangganModel> data) {
                dataPelanggan.clear();
                dataPelanggan.addAll(data);
                pelangganAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(SearchPelangganActivity.this, message, Toast.LENGTH_LONG);
            }
        });
    }

    private void searchAction(){
        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                QueryContract.PelangganQuery query = new PelangganQueryImplementation();
                query.searchByIdOrName(et_search.getText().toString(), new QueryResponse<List<PelangganModel>>() {
                    @Override
                    public void onSuccess(List<PelangganModel> data) {
                        dataPelanggan.clear();
                        dataPelanggan.addAll(data);
                        pelangganAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(SearchPelangganActivity.this, message, Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }

    public void activityTambahPelanggan(View view) {

        Intent intent = new Intent(this, TambahPelanggan.class);
        startActivity(intent);
    }
}