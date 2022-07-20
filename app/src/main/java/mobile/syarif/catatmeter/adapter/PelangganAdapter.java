package mobile.syarif.catatmeter.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import mobile.syarif.catatmeter.DetailPelanggan;
import mobile.syarif.catatmeter.R;
import mobile.syarif.catatmeter.SearchPelangganActivity;
import mobile.syarif.catatmeter.TagihanActivity;
import mobile.syarif.catatmeter.model.PelangganModel;

public class PelangganAdapter extends RecyclerView.Adapter<PelangganAdapter.PelangganViewHolder>{
    private LayoutInflater mInflater;
    private final List<PelangganModel> pelanggan;
    private Context context;

    public PelangganAdapter(Context context,
                            List<PelangganModel> pelanggan) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.pelanggan = pelanggan;
    }

    class PelangganViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout layout;
        public final TextView nama;
        public final TextView id_pelanggan;
        public final TextView alamat;
        final PelangganAdapter mAdapter;

        public PelangganViewHolder(View itemView, PelangganAdapter adapter) {
            super(itemView);
            this.layout = itemView.findViewById(R.id.item_pelanggan);
            this.nama = itemView.findViewById(R.id.nama);
            this.id_pelanggan = itemView.findViewById(R.id.id_pelanggan);
            this.alamat = itemView.findViewById(R.id.alamat);
            this.mAdapter = adapter;
        }
    }
    @NonNull
    @Override
    public PelangganAdapter.PelangganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.list_pelanggan,
                parent, false);
        return new PelangganViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(PelangganViewHolder holder, int position) {
        PelangganModel pelanggan = this.pelanggan.get(position);
        int id_pelanggan = pelanggan.getId_pelanggan();
        String nama = pelanggan.getNama();
        String no_telepon = pelanggan.getNo_telepon();
        String alamat = pelanggan.getAlamat();
        int tarif = pelanggan.getTarif();
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(pelanggan.isTercatat()){
                    intent = new Intent(context, TagihanActivity.class);
                }else{
                    intent = new Intent(context, DetailPelanggan.class);
                }
                intent.putExtra("id_pelanggan", id_pelanggan);
                context.startActivity(intent);
            }
        });
        holder.id_pelanggan.setText("ID : " + id_pelanggan);
        holder.nama.setText(nama);
        holder.alamat.setText(alamat);
        if(pelanggan.isTercatat()){
            Log.d("TES", "TES");
            holder.layout.setBackgroundResource(R.drawable.bg_info_rounded);
        }
        if(position != this.getItemCount()-1){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 20);
            holder.layout.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return pelanggan.size();
    }
}
