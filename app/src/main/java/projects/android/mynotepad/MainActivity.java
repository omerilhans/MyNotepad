package projects.android.mynotepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    ListView lv;
    LayoutInflater li;
    BaseAdapter ba;
    ArrayList<Note> dS = new ArrayList<>();
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        li = LayoutInflater.from(this);

        db = new DB(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Note secilenNote = dS.get(i);
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("note", secilenNote);
        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {

        final  Note secilenNote = dS.get(index);

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Uyarı")
                .setMessage("Not Silinsin Mi ?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                        db.delNote(secilenNote.id);
                        MainActivity.this.onResume();
                    }
                })
                .setNegativeButton("Hayır", null)
                .show();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Ekle").setIcon(android.R.drawable.ic_menu_add)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add("Tümünü Sil").setIcon(android.R.drawable.ic_menu_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        String ne = ""+item.getTitle();
        if (ne.equals("Ekle"))
        {
            startActivity(new Intent(this, NoteActivity.class));
        }
        if (ne.equals("Tümünü Sil"))
        {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Uyarı")
                    .setMessage("Tüm Veri Silinecek. Emin Misiniz ?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.tumunuSil();
                            MainActivity.this.onResume();
                        }
                    })
                    .setNegativeButton("Hayır", null)

                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onResume()
    {
        super.onResume();

        dS = db.getNotes();

        ba = new BaseAdapter() {
            @Override
            public int getCount() {

                return dS.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup)
            {
                if (view == null)
                    view = li.inflate(android.R.layout.two_line_list_item, null);

                TextView t1 = (TextView) view.findViewById(android.R.id.text1);
                final TextView t2 = (TextView) view.findViewById(android.R.id.text2);

                Note n = dS.get(i);
                t1.setTextSize(20.0f);
                t1.setText(n.baslik);
                t2.setTextSize(18.0f);
                t2.setText(n.tarih);
                view.setPadding(10,10,10,10);
                return view;
            }
        };

        lv.setAdapter(ba);
    }
}
