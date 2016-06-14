package projects.android.mynotepad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by omer
 */
public class DB extends SQLiteOpenHelper
{
    SQLiteDatabase db;

    public DB(Context c)
    {
        // /data/data/com.mynp/databases/Notlar.db
        super(c, c.getDatabasePath("Notlar.db").getAbsolutePath(), null, 3);
        // Android Bug
        db = getWritableDatabase();
        db.close();
    }

    // Veritabanı Tabloları İlk Defa Oluşturulurken 1 KEz Çalışır
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String q = "create table notlar "+
                "("+
                "id integer primary key autoincrement, "+
                "tarih text, "+
                "baslik text, "+
                "icerik text "+
                ");";

        sqLiteDatabase.execSQL(q);
        Log.e("x","DB OnCreate Methodu Calisti");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void addNote(String baslik, String icerik)
    {
        String q = String.format(
                "insert into notlar (tarih, baslik, icerik) values "+
                        "(datetime('now'), '%s', '%s')",
                baslik, icerik);

        Log.e("x","add Note : "+q);
        db = getWritableDatabase();
        db.execSQL(q);
        db.close();
    }

    public void delNote(String id)
    {
        db = getWritableDatabase();
        db.execSQL("delete from notlar where id = "+id);
        db.close();
    }

    public void updNote(String id, String baslik, String icerik)
    {
        db = getWritableDatabase();
        String q = String.format(
                "update notlar set baslik = '%s', icerik = '%s' where id = %s",
                baslik, icerik, id);

        db.execSQL(q);
        db.close();
    }

    public void tumunuSil()
    {
        db =getWritableDatabase();
        db.execSQL("delete from notlar");
        db.close();
    }

    public ArrayList<Note> getNotes()
    {
        db =getWritableDatabase();
        Cursor c = db.rawQuery("select * from notlar", null);
        ArrayList<Note> nL = new ArrayList<>();

        while (c.moveToNext())
            nL.add(new Note(c));

        c.close();
        db.close();
        return nL;
    }
}

