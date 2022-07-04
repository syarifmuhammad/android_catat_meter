package mobile.syarif.catatmeter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;

import mobile.syarif.catatmeter.adapter.PelangganAdapter;
import mobile.syarif.catatmeter.model.Pelanggan;

public class SearchPelangganActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_search_pelanggan);
        LinkedList<Pelanggan> pelanggan = this.getDataPelanggan();
        RecyclerView recyclerViewPelanggan = findViewById(R.id.recyclerViewPelanggan);
        PelangganAdapter pelangganAdapter = new PelangganAdapter(this, pelanggan);
        recyclerViewPelanggan.setAdapter(pelangganAdapter);
        recyclerViewPelanggan.setLayoutManager(new LinearLayoutManager(this));
    }

    public LinkedList<Pelanggan> getDataPelanggan(){
        LinkedList<Pelanggan> pelanggan = new LinkedList<>();
        pelanggan.add(new Pelanggan(1201200016, "syarif baru", "alamat baru"));
        pelanggan.add(new Pelanggan(1201200017, "syarif baru 2", "alamat baru 2"));
        return pelanggan;
    }
}