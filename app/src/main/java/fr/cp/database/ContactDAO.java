package fr.cp.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import cp.fr.localsqlapp.model.Contact;

/**
 * Created by Formation on 10/01/2018.
 */

public class ContactDAO {

    private DatabaseHandler db;

    public ContactDAO(DatabaseHandler db) {
        this.db = db;
    }

    /**
     * récupération d'un contact en fonction de sa clé primaire
     * @param id
     * @return
     */
    public Contact findOneByID(long id) throws SQLiteException {
        String[] param = {String.valueOf(id)};
        String sql = "SELECT id, name, first_name, email FROM contacts WHERE id=?";
        Cursor cursor = this.db.getReadableDatabase().rawQuery(sql, param);

        //Instancier un objet
        Contact contact = new Contact();

        //Hydratation du contact
        if (cursor.moveToNext()) {
            contact.setId(cursor.getLong(0));
            contact.setName(cursor.getString(1));
            contact.setFirstName(cursor.getString(2));
            contact.setEmail(cursor.getString(3));


        }

        cursor.close();

        return contact;
    }

}
