package fr.cp.database;

import android.database.sqlite.SQLiteException;

import java.util.List;

import cp.fr.localsqlapp.model.Contact;

/**
 * Created by Formation on 11/01/2018.
 */

interface DAOInterface <DTO> {
    DTO findOneByID(long id) throws SQLiteException;

    List<DTO> findALL() throws SQLiteException;

    void deleteOneById(Long id) throws SQLiteException;

    void persist(Contact entity);
}
