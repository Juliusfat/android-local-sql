package cp.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import cp.fr.localsqlapp.model.Contact;
import fr.cp.database.ContactDAO;
import fr.cp.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Contact selectedPerson;
    // Intgeger peut etre nul
    private Integer selectedIndex;
    private ListView contactListView;
    private List<Contact> contactList;
    private final String LIFE_CYCLE = "Cycle de vie";
    private DatabaseHandler db;
    private ContactDAO dao;
    private ContactArrayAdapter contactAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LIFE_CYCLE, "onCreate");
        setContentView(R.layout.activity_main);

        //instancier gestionnaire de base de données

        this.db = new DatabaseHandler(this);

        //instancier la liste des contacts
        this.dao = new ContactDAO(this.db);

        contactListView = findViewById(R.id.contactlistView);
        contactListInit();


        //Récupération des données persistées dans le Bundle
        if(savedInstanceState != null){
            //Récupération de l'index de sélection de sauvegarde
            this.selectedIndex = savedInstanceState.getInt("selectedIndex");
            if(this.selectedIndex != null){
                this.selectedPerson = this.contactList.get(this.selectedIndex);

                contactListView.setSelection(this.selectedIndex);

                Log.i(LIFE_CYCLE, "Selection:"+ contactListView.getSelectedItemId());
            }
        }
    }



    private void contactListInit() {
        contactList = this.dao.findALL();
        ContactArrayAdapter contactAdapter = new ContactArrayAdapter(this, contactList);
        contactListView.setAdapter(contactAdapter);
        contactListView.setOnItemClickListener(this);
    }

    public void onAddContact(View view) {

        Intent FormIntent = new Intent(this, FormActivity.class);
        startActivity(FormIntent);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.selectedIndex = position;
        this.selectedPerson = this.contactList.get(position);
        Toast.makeText(this, "ligne trouvée :" + selectedPerson.getName(), Toast.LENGTH_LONG).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajout des entrees du fichier main_options_menu
        //au menu contextuel de l'activité
        getMenuInflater().inflate(R.menu.main_options_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainMenuOptionDelete:
                this.deleteSelectedContact();
                break;
            case R.id.mainMenuOptionEdit:
                this.modifySelectedContact();
                break;
        }
        return true;
    }

    private void modifySelectedContact() {
        if (selectedIndex != null) {

            Intent FormIntent = new Intent(this, FormActivity.class);
            FormIntent.putExtra("first_name", this.selectedPerson.getFirstName());
            FormIntent.putExtra("id", String.valueOf(this.selectedPerson.getId()));
            FormIntent.putExtra("name", this.selectedPerson.getName());
            FormIntent.putExtra("email", this.selectedPerson.getEmail());
            startActivityForResult(FormIntent, 1);

        } else
            Toast.makeText(this, "vous devez selectionnez un contact", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(this,"Mise a jour OK",Toast.LENGTH_SHORT);
            this.contactListInit();
        }
    }

    //Suppression du contact
    private void deleteSelectedContact() throws SQLiteException{
        if (selectedIndex != null) {

            // definition de la requete SQL et des parametres
            Long id= this.selectedPerson.getId();
            this.dao.deleteOneById(Long.valueOf(id));
            Toast.makeText(this, "suppression ok", Toast.LENGTH_SHORT).show();

            //regenerer la liste des contacts
            //
            this.contactList = this.dao.findALL();
            this.contactListInit();


        } else {
            Toast.makeText(this, "vous devez selectionnez un contact", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LIFE_CYCLE, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LIFE_CYCLE, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LIFE_CYCLE, "onResume");
    }


      //Persitance des données avant destruction d'actvité

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (this.selectedIndex != null) {
            outState.putInt("selectedIndex", this.selectedIndex);
            super.onSaveInstanceState(outState);
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        this.selectedIndex= savedInstanceState.getInt("selectedIndex");
    }
}


