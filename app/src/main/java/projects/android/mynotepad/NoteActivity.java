package projects.android.mynotepad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by omer
 */
public class NoteActivity extends AppCompatActivity {

    EditText etBaslik, etIcerik;
    DB db;
    boolean isEditMode = false;
    Note secilenNote = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        db = new DB(this);
        etBaslik = (EditText) findViewById(R.id.etBaslik);
        etIcerik = (EditText) findViewById(R.id.etIcerik);

        try
        {
            secilenNote = (Note) getIntent().getExtras().getSerializable("note");
            if (secilenNote != null)
            {
                isEditMode = true;
                etBaslik.setText(secilenNote.baslik);
                etIcerik.setText(secilenNote.icerik);
            }
        } catch (Exception e) { }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isEditMode) {
            menu.add("Kaydet")
                    .setIcon(android.R.drawable.ic_menu_save)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        else
        {
            menu.add("Güncelle")
                    .setIcon(android.R.drawable.ic_menu_edit)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String ne= ""+item.getTitle();
        if (ne.equals("Kaydet"))
        {
            db.addNote(etBaslik.getText().toString(), etIcerik.getText().toString());
            Toast.makeText(NoteActivity.this,"Yeni Not Eklendi", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (ne.equals("Güncelle"))
        {
            String yeniBaslik = etBaslik.getText().toString();
            String yeniIcerik = etIcerik.getText().toString();

            db.updNote(secilenNote.id, yeniBaslik, yeniIcerik);
            Toast.makeText(NoteActivity.this,"Not Güncellendi", Toast.LENGTH_SHORT).show();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
