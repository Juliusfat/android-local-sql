package fr.cp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

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
            contact = hydrateContact(cursor);

        }

        cursor.close();

        return contact;
    }

    private Contact hydrateContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setId(cursor.getLong(0));
        contact.setName(cursor.getString(1));
        contact.setFirstName(cursor.getString(2));
        contact.setEmail(cursor.getString(3));

        return contact;
    }

    /**
     *
     * @return
     */

    public List<Contact> findALL() throws SQLiteException{

        //instancier la liste des contacts
        List<Contact> contactList = new ArrayList<>();

        //executer la requete sql

        String sql = "SELECT id, name, first_name, email FROM contacts";
        Cursor cursor = this.db.getReadableDatabase().rawQuery(sql,null);

        // boucle sur le curseur

        while (cursor.moveToNext()) {

            contactList.add(this.hydrateContact(cursor));
        }

        //fermer cursor

        cursor.close();

        return contactList;
    }

    //methode de suppression simple

    public void deleteOneById (Long id) throws SQLiteException{
        String[] params = {id.toString()};
        String Sql = "DELETE FROM contacts WHERE id=?";
        this.db.getWritableDatabase().execSQL(Sql, params);


    }

    public void persist(Contact entity) {
        if (entity.getId() == null) {
            this.insert(entity);
        } else {
            this.update(entity);
        }
    }

    private ContentValues getContentValuesFromEntity(Contact entity){
        ContentValues values = new ContentValues();
        values.put("name", entity.getName());
        values.put("first_name", entity.getFirstName());
        values.put("email", entity.getEmail());
        return values;
    }

    private void insert (Contact entity) {
        Long id = this.db.getWritableDatabase().insert("contacts",null,this.getContentValuesFromEntity(entity));
        entity.setId(id);
    }

    private void update(Contact entity){
        String[] params = {entity.getId().toString()};
        this.db.getWritableDatabase().update(
                "contacts",
                this.getContentValuesFromEntity(entity),
                "id=?",
                params
        );
    }
}
