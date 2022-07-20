package mobile.syarif.catatmeter.database;

import static mobile.syarif.catatmeter.util.Constants.ID_PENCATATAN;
import static mobile.syarif.catatmeter.util.Constants.PELANGGAN_ID_FK;
import static mobile.syarif.catatmeter.util.Constants.PEMAKAIAN_BULAN_INI;
import static mobile.syarif.catatmeter.util.Constants.PEMAKAIAN_TERAKHIR;
import static mobile.syarif.catatmeter.util.Constants.PERIODE;
import static mobile.syarif.catatmeter.util.Constants.STATUS;
import static mobile.syarif.catatmeter.util.Constants.TABLE_PENCATATAN;
import static mobile.syarif.catatmeter.util.Constants.TANGGAL;
import static mobile.syarif.catatmeter.util.Constants.TARIF1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mobile.syarif.catatmeter.model.PencatatanModel;

public class PencatatanQueryImplementation implements QueryContract.PencatatanQuery{
    private DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    @Override
    public void getLastWhereIdPelanggan(int id_pelanggan, QueryResponse<PencatatanModel> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        PencatatanModel catatan;

        Cursor cursor = null;
        try {
            boolean distinct = false;
            String table = TABLE_PENCATATAN;
            String where = PELANGGAN_ID_FK + " = ? AND " + PERIODE + " != ?";
            String[] whereArgs = {String.valueOf(id_pelanggan), getPeriode()};
            String order = TANGGAL + " DESC";
            String limit = "1";
            cursor = sqLiteDatabase.query(distinct, table, null, where, whereArgs, null, null, order, limit);

            if(cursor!=null && cursor.moveToFirst()){
                catatan = getPencatatanFromCursor(cursor);
                response.onSuccess(catatan);
            } else{
                response.onSuccess(null);
            }

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }
    @Override
    public void getCurrentPeriodeWhereIdPelanggan(int id_pelanggan, QueryResponse<PencatatanModel> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        PencatatanModel catatan;

        Cursor cursor = null;
        try {
            String table = TABLE_PENCATATAN;
            String where = PELANGGAN_ID_FK + " = ? AND " + PERIODE + " = ?";
            String[] whereArgs = {String.valueOf(id_pelanggan), getPeriode()};
            String order = TANGGAL + " DESC";
            String limit = "1";
            cursor = sqLiteDatabase.query(false, table, null, where, whereArgs, null, null, order, limit);

            if(cursor!=null && cursor.moveToFirst()){
                catatan = getPencatatanFromCursor(cursor);
                response.onSuccess(catatan);
            } else{
                response.onSuccess(null);
            }

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void create(PencatatanModel pencatatan, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = getContentValuesForPencatatan(pencatatan);

        try {
            long id = sqLiteDatabase.insertOrThrow(TABLE_PENCATATAN, null, contentValues);
            if(id>0) {
                pencatatan.setId_pencatatan((int) id);
                response.onSuccess(true);
            }
            else
                response.onFailure("Failed to create pencatatan. Unknown Reason!");
        } catch (SQLiteException e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }
    @Override
    public void update(PencatatanModel pencatatan, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = getContentValuesForPencatatan(pencatatan);

        try {
            long id = sqLiteDatabase.update(TABLE_PENCATATAN, contentValues, ID_PENCATATAN + " = " + pencatatan.getId_pencatatan(), null);
            if(id>0) {
                response.onSuccess(true);
            }
            else
                response.onFailure("Failed to update pencatatan. Unknown Reason!");
        } catch (SQLiteException e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }
    }

    private ContentValues getContentValuesForPencatatan(PencatatanModel pencatatan){
        ContentValues contentValues = new ContentValues();

        contentValues.put(PEMAKAIAN_TERAKHIR, pencatatan.getPemakaian_terakhir());
        contentValues.put(PEMAKAIAN_BULAN_INI, pencatatan.getPemakaian_bulan_ini());
        contentValues.put(TARIF1, pencatatan.getTarif());
        contentValues.put(PERIODE, pencatatan.getPeriode());
        contentValues.put(TANGGAL, pencatatan.getTanggal().toString());
        contentValues.put(STATUS, pencatatan.getStatus());
        contentValues.put(PELANGGAN_ID_FK, pencatatan.getPelanggan_id());
        return contentValues;
    }

    private PencatatanModel getPencatatanFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_PENCATATAN));
        int pemakaian_terakhir = cursor.getInt(cursor.getColumnIndexOrThrow(PEMAKAIAN_TERAKHIR));
        int pemakaian_bulan_ini = cursor.getInt(cursor.getColumnIndexOrThrow(PEMAKAIAN_BULAN_INI));
        int tarif = cursor.getInt(cursor.getColumnIndexOrThrow(TARIF1));
        String periode = cursor.getString(cursor.getColumnIndexOrThrow(PERIODE));
        String tanggal_str = cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL));
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        Date tanggal = null;
        try {
            tanggal = formatter.parse(tanggal_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int status = cursor.getInt(cursor.getColumnIndexOrThrow(STATUS));
        int pelanggan_id = cursor.getInt(cursor.getColumnIndexOrThrow(PELANGGAN_ID_FK));

        return new PencatatanModel(id, pemakaian_terakhir, pemakaian_bulan_ini, tarif, periode, tanggal, pelanggan_id, status);
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
