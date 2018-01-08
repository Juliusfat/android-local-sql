package cp.fr.localsqlapp;

import android.content.ContentValues;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onValid(View V){
        Button clickButton = (Button) V;

        // récupération de la saisie de l'utilisateur

        String name = ((EditText) findViewById(R.id.editTextNom)).getText().toString();
        String firstname = ((EditText) findViewById(R.id.ediTextPrenom)).getText().toString();
        String email = ((EditText) findViewById(R.id.ediTextEmail)).getText().toString();

        //Instanciation de connexion à la base de données
        DatabaseHandler  db = new DatabaseHandler (this);

        //
        ContentValues insertValues = new ContentValues();
        db.getWritableDatabase().insert("contacts", null, insertValues);

        // insertion des données

        try {
            db.getWritableDatabase().insert("contacts", null, insertValues);
            Toast.makeText(this,"Insertion OK", Toast.LENGTH_SHORT).show();
        }
            catch(SQLiteException ex){
                Log.e("SQL EXCEPTION", ex.getMessage());
            }

        }
    }

