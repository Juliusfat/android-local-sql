package cp.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

import fr.cp.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Map<String,String> selectedPerson;
    // Intgeger peut etre nul
    private Integer selectedIndex;
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
        contactListView.setOnItemClickListener(this);
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
            contactCols.put ("name",cursor.getString(2));
            contactCols.put ("firstName",cursor.getString(1));
            contactCols.put ("email", cursor.getString(3));

            //ajout du map de la liste

            contactList.add(contactCols);
        }

        //retour de curseur

        return contactList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.selectedIndex = position;
        this.selectedPerson=this.contactList.get(position);
        Toast.makeText(this, "ligne trouvée :"+ selectedPerson.get("name"),Toast.LENGTH_LONG).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajout des entrees du fichier main_options_menu
        //au menu contextuel de l'activité
        getMenuInflater().inflate(R.menu.main_options_menu,menu);

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainMenuOptionDelete:
                break;
            case R.id.mainMenuOptionEdit:
                break;
        }
        return true;
    }
}
