package mobile.syarif.catatmeter.database;

import static mobile.syarif.catatmeter.util.Constants.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import mobile.syarif.catatmeter.util.MyApp;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    private DatabaseHelper() {
        super(MyApp.context, CATATMETER_DB, null, VERSION);
    }

    public static DatabaseHelper getInstance() {

        if (databaseHelper == null) {
            synchronized (DatabaseHelper.class){ //thread safe singleton
                if (databaseHelper == null)
                    databaseHelper = new DatabaseHelper();
            }
        }

        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PELANGGAN_TABLE = "CREATE TABLE " + TABLE_PELANGGAN + " (" +
                ID_PELANGGAN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAMA + " TEXT NOT NULL," +
                NO_TELEPON + " TEXT NOT NULL," +
                ALAMAT + " TEXT NOT NULL," +
                TARIF + " INTEGER NOT NULL" +
        ")";

        String CREATE_PENCATATAN_TABLE = "CREATE TABLE " + TABLE_PENCATATAN + " (" +
                ID_PENCATATAN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PEMAKAIAN_TERAKHIR + " INTEGER NOT NULL," +
                PEMAKAIAN_BULAN_INI + " INTEGER NOT NULL," +
                TARIF1 + " INTEGER NOT NULL," +
                PERIODE + " TEXT NOT NULL," +
                TANGGAL + " TEXT NOT NULL," +
                STATUS + " INTEGER NOT NULL," +
                PELANGGAN_ID_FK + " INTEGER NOT NULL," +
                "FOREIGN KEY (" + PELANGGAN_ID_FK + ") REFERENCES " + TABLE_PELANGGAN + " (" + ID_PELANGGAN + ") ON UPDATE CASCADE ON DELETE CASCADE" +
        ")";

        sqLiteDatabase.execSQL(CREATE_PELANGGAN_TABLE);
        sqLiteDatabase.execSQL(CREATE_PENCATATAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
