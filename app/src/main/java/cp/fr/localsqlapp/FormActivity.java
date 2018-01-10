package cp.fr.localsqlapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.cp.database.DatabaseHandler;

public class FormActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText firstnameEditText;
    EditText emailEditText;
    String contactId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


           Intent intention = getIntent();
           String name = intention.getStringExtra("name");
           String id = intention.getStringExtra("id");
           String firstname = intention.getStringExtra("first_name");
           String email= intention.getStringExtra("email");


           this.firstnameEditText = findViewById(R.id.ediTextPrenom);
           this.nameEditText = findViewById(R.id.editTextNom);
           this.emailEditText = findViewById(R.id.ediTextEmail);

           this.contactId = id;
           this.firstnameEditText.setText(firstname);
           this.nameEditText.setText(name);
           this.emailEditText.setText(email);



          /*  String name = ((EditText) findViewById(R.id.editTextNom)).getText().toString();
            String firstname = ((EditText) findViewById(R.id.ediTextPrenom)).getText().toString();
            String email = ((EditText) findViewById(R.id.ediTextEmail)).getText().toString();
           */


        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onValid(View V) {


        // récupération de la saisie de l'utilisateur

        String name = this.nameEditText.getText().toString();
        String firstname = this.firstnameEditText.getText().toString();
        String email = this.emailEditText.getText().toString();

        //Instanciation de connexion à la base de données
        DatabaseHandler db = new DatabaseHandler(this);

        // initialisation des valeurs
        ContentValues insertValues = new ContentValues();
        insertValues.put("name", name);
        insertValues.put("first_name", firstname);
        insertValues.put("email", email);

        //db.getWritableDatabase().insert("contacts", null, insertValues);



            try {
                if (this.contactId != null) {

                    //mise à jour de la base de données
                    String[] params = {contactId};
                    db.getWritableDatabase().update("contacts",insertValues,"id=?",params);
                    Toast.makeText(this, "Modification OK", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }else {
                    // insertion d'un nouvel enregistrement
                    db.getWritableDatabase().insert("contacts", null, insertValues);
                    Toast.makeText(this, "Insertion OK", Toast.LENGTH_SHORT).show();
                    ((EditText) findViewById(R.id.editTextNom)).setText("");
                    ((EditText) findViewById(R.id.ediTextPrenom)).setText("");
                    ((EditText) findViewById(R.id.ediTextEmail)).setText("");
                }
            } catch (SQLiteException ex) {
                Log.e("SQL EXCEPTION", ex.getMessage());
            }

        }
    }


