package cp.fr.localsqlapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import fr.cp.database.DatabaseHandler;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
    }

    public void onValid(View V){
        Button clickButton = (Button) V;

        DatabaseHandler  db = new DatabaseHandler (this);

    }
}
