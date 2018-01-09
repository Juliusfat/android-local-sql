package cp.fr.localsqlapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Formation on 09/01/2018.
 */

public class ContactArrayAdapter extends ArrayAdapter {
    // initialisation des variables
    private Activity context;
    private List<Map<String,String>> data;
    private int resource;
    private LayoutInflater inflater;


    public ContactArrayAdapter(@NonNull Context context, @NonNull List<Map<String, String>> data) {
        super(context, 0, data);
        // création du constructeur
        this.data = data;
        this.resource =  resource;
        this.context = (Activity) context;
        this.inflater = this.context.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // instenciation de la vue
        View view = this.inflater.inflate(R.layout.contact_list_view,parent,false);

        Map<String,String> contactdata = this.data.get(position);
        // liaison entre les données et la ligne
        TextView nametextview = view .findViewById((R.id.ListtextViewName));
        nametextview.setText(contactdata.get("name"));
        TextView firsttextview = view .findViewById((R.id.ListtextViewFirstname));
        firsttextview.setText(contactdata.get("firstName"));
        TextView emailtextview = view .findViewById((R.id.ListtextViewEmail));
        emailtextview.setText(contactdata.get("email"));


        return view;
    }
}
