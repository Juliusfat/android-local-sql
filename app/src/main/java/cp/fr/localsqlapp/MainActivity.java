package cp.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.cp.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    private ListView contactListView;
    private List<Map<String,String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactListView = findViewById(R.id.contactlistView);
        contactList = this.getAllcontact();
        ContactArrayAdapter contactAdapter = new ContactArrayAdapter(this,contactList);
        contactListView.setAdapter(contactAdapter);
    }

    public void onAddContact(View view) {

            Intent FormIntent = new Intent(this, FormActivity.class);
            startActivity(FormIntent);

    }

    private List<Map<String,String>> getAllcontact() {

        //instencier le composant à la base de données
        DatabaseHandler db = new DatabaseHandler(this) ;

        //instancier la requete de selection
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM contacts", null);

        //instancier la liste qui recevra les données
        List<Map<String,String>> contactList = new ArrayList<>();


        while(cursor.moveToNext()){
            Map<String, String> contactCols = new HashMap<>();
            contactCols.put ("name",cursor.getString(0));
            contactCols.put ("firstname",cursor.getString(1));
            contactCols.put ("email", cursor.getString(2));

            //ajout du map de la liste

            contactList.add(contactCols);
        }

        //retour de curseur

        return contactList;
    }
}
