package mobile.syarif.catatmeter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import mobile.syarif.catatmeter.R;
import mobile.syarif.catatmeter.model.Pelanggan;

public class PelangganAdapter extends RecyclerView.Adapter<PelangganAdapter.PelangganViewHolder>{
    private LayoutInflater mInflater;
    private final LinkedList<Pelanggan> pelanggan;

    public PelangganAdapter(Context context,
                            LinkedList<Pelanggan> pelanggan) {
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
        Pelanggan pelanggan = this.pelanggan.get(position);
        Integer id_pelanggan = pelanggan.getId_pelanggan();
        String nama = pelanggan.getNama();
        String alamat = pelanggan.getAlamat();
        holder.id_pelanggan.setText(id_pelanggan.toString());
        holder.nama.setText(nama);
        holder.alamat.setText(alamat);
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
