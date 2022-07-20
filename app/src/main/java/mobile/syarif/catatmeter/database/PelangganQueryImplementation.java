package mobile.syarif.catatmeter.database;

import static mobile.syarif.catatmeter.util.Constants.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mobile.syarif.catatmeter.R;
import mobile.syarif.catatmeter.model.PelangganModel;
import mobile.syarif.catatmeter.model.PencatatanModel;

public class PelangganQueryImplementation implements QueryContract.PelangganQuery{
    private DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public void count(QueryResponse<Integer> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        try {
            long pelanggan = DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_PELANGGAN);
            response.onSuccess((int) pelanggan);

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void countTercatat(QueryResponse<Integer> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT PELANGGAN_ID_FK FROM PENCATATAN WHERE PERIODE = ? GROUP BY PELANGGAN_ID_FK", new String[] {getPeriode()} );
            if(cursor!=null){
                response.onSuccess(cursor.getCount());
            } else
                response.onSuccess(0);

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void searchByIdOrName(String param, QueryResponse<List<PelangganModel>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<PelangganModel> pelanggan = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT *, (SELECT COUNT(*) AS TERCATAT FROM PENCATATAN WHERE PELANGGAN_ID_FK = ID_PELANGGAN AND PERIODE = ?) AS TERCATAT FROM PELANGGAN WHERE ID_PELANGGAN LIKE ? OR NAMA LIKE ?", new String[] {getPeriode(), "%"+ param+ "%", "%"+ param+ "%"});
            if(cursor!=null && cursor.moveToFirst()){
                do {
                    PelangganModel p = getPelangganFromCursor(cursor);
                    pelanggan.add(p);
                } while (cursor.moveToNext());

                response.onSuccess(pelanggan);
            } else
                response.onFailure("Tidak ada pelanggan di database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void all(QueryResponse<List<PelangganModel>> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<PelangganModel> pelanggan = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT *, (SELECT COUNT(*) AS TERCATAT FROM PENCATATAN WHERE PELANGGAN_ID_FK = ID_PELANGGAN AND PERIODE = ?) AS TERCATAT FROM PELANGGAN", new String[] {getPeriode()});

            if(cursor!=null && cursor.moveToFirst()){
                do {
                    PelangganModel p = getPelangganFromCursor(cursor);
                    pelanggan.add(p);
                } while (cursor.moveToNext());

                response.onSuccess(pelanggan);
            } else
                response.onFailure("Tidak ada pelanggan di database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void get(int id_pelanggan, QueryResponse<PelangganModel> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT *, (SELECT COUNT(*) AS TERCATAT FROM PENCATATAN WHERE PELANGGAN_ID_FK = ID_PELANGGAN AND PERIODE = ?) AS TERCATAT FROM PELANGGAN WHERE ID_PELANGGAN = ?", new String[] {getPeriode(), String.valueOf(id_pelanggan)});

            if(cursor!=null && cursor.moveToFirst()){
                PelangganModel p = getPelangganFromCursor(cursor);
                response.onSuccess(p);
            } else
                response.onFailure("Tidak ada pelanggan di database");

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void create(PelangganModel pelanggan, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = getContentValuesForPelanggan(pelanggan);

        try {
            long id = sqLiteDatabase.insertOrThrow(TABLE_PELANGGAN, null, contentValues);
            if(id>0) {
                response.onSuccess(true);
                pelanggan.setId_pelanggan((int) id);
            }
            else
                response.onFailure("Failed to create pelanggan. Unknown Reason!");
        } catch (SQLiteException e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    @Override
    public void update(PelangganModel pelanggan, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = getContentValuesForPelanggan(pelanggan);

        try {
            long id = sqLiteDatabase.update(TABLE_PELANGGAN, contentValues, ID_PELANGGAN + " = " + pelanggan.getId_pelanggan(), null);
            if(id>0) {
                response.onSuccess(true);
            }
            else
                response.onFailure("Failed to update pelanggan. Unknown Reason!");
        } catch (SQLiteException e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    private ContentValues getContentValuesForPelanggan(PelangganModel pelanggan){
        ContentValues contentValues = new ContentValues();

//        contentValues.put(ID_PELANGGAN, pelanggan.getId_pelanggan());
        contentValues.put(NAMA, pelanggan.getNama());
        contentValues.put(NO_TELEPON, pelanggan.getNo_telepon());
        contentValues.put(ALAMAT, pelanggan.getAlamat());
        contentValues.put(TARIF, pelanggan.getTarif());

        return contentValues;
    }

    private PelangganModel getPelangganFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_PELANGGAN));
        String nama = cursor.getString(cursor.getColumnIndexOrThrow(NAMA));
        String no_telepon = cursor.getString(cursor.getColumnIndexOrThrow(NO_TELEPON));
        int tarif = cursor.getInt(cursor.getColumnIndexOrThrow(TARIF));
        String alamat = cursor.getString(cursor.getColumnIndexOrThrow(ALAMAT));
        boolean tercatat = cursor.getInt(cursor.getColumnIndexOrThrow("TERCATAT")) >= 1;
        return new PelangganModel(id, nama, no_telepon, tarif, alamat, tercatat);
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
