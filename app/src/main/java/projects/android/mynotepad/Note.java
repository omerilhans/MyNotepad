package projects.android.mynotepad;

import android.database.Cursor;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by omer on 11.06.2016.
 */
public class Note implements Serializable {

    public String id;
    public String baslik;
    public String tarih;
    public String icerik;

    public Note(Cursor c) {
        id = c.getString(0);
        tarih = c.getString(1);
        baslik = c.getString(2);
        icerik = c.getString(3);

        Log.e("x", "ID : " + id + " Tarih : " + tarih + " Baslik : " + baslik);
    }
}
