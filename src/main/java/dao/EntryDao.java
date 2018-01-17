package dao;

import models.Entry;

import java.util.List;

/**
 * Created by Guest on 1/16/18.
 */
public interface EntryDao {

    void add (Entry entry);

    List<Entry> getAll();

    Entry findById(int id);

    void editEntry(String firstName, String lastName, int addressId, String phone, int id);

    void deleteById(int id);

    void clearAll();
}
