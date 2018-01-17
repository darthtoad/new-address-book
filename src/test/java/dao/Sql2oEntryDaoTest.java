package dao;

import models.Entry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

/**
 * Created by Guest on 1/16/18.
 */
public class Sql2oEntryDaoTest {
    private Sql2oEntryDao entryDao;
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        entryDao = new Sql2oEntryDao(sql2o);

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingEntrySetsId() throws Exception {
        Entry entry = new Entry("fdas", "aadsf", 0, "555-555-5555");
        int originalEntryId = entry.getId();
        entryDao.add(entry);
        assertNotEquals(originalEntryId, entry.getId());
    }

    @Test
    public void existingEntriesCanBeFoundById() throws Exception {
        Entry entry = new Entry("fdas", "aadsf", 0, "555-555-5555");
        entryDao.add(entry);
        Entry foundEntry = entryDao.findById(entry.getId());
        assertEquals(entry.getId(), foundEntry.getId());
        assertEquals(entry.getPhone(), foundEntry.getPhone());
    }

    @Test
    public void getAll_getsAllEntries_List() throws Exception {
        Entry entry = new Entry("fdas", "aadsf", 0, "555-555-5555");
        entryDao.add(entry);
        assertEquals(1, entryDao.getAll().size());
    }

    @Test
    public void getAll_noTasksReturnsEmptyList() throws Exception {
        assertEquals(0, entryDao.getAll().size());
    }

    @Test
    public void editEntry_changesEntry() throws Exception {
        String phone = "555-555-5555";
        Entry entry = new Entry("fdas", "aadsf", 0, phone);
        entryDao.add(entry);
        entryDao.editEntry("Someone", "Something", 1, "234-567-8910", entry.getId());
        Entry editEntry = entryDao.findById(entry.getId());
        assertNotEquals(phone, editEntry.getPhone());
    }

    @Test
    public void deleteByIdDeletesCorrectEntry() throws Exception {
        Entry entry = new Entry("fdas", "aadsf", 0, "555-555-5555");
        Entry entry1 = new Entry("Someone", "Something", 1, "234-567-8910");
        entryDao.add(entry);
        entryDao.add(entry1);
        entryDao.deleteById(1);
        assertEquals(1, entryDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Entry entry = new Entry("fdas", "aadsf", 0, "555-555-5555");
        Entry entry1 = new Entry("Someone", "Something", 1, "234-567-8910");
        entryDao.add(entry);
        entryDao.add(entry1);
        entryDao.clearAll();
        assertEquals(0, entryDao.getAll().size());
    }

    @Test
    public void addressIdIsReturnedCorrectly() throws Exception {
        Entry entry = new Entry("fdas", "aadsf", 0, "555-555-5555");
        int originalAddressId = entry.getAddressId();
        entryDao.add(entry);
        assertEquals(originalAddressId, entryDao.findById(entry.getId()).getAddressId());
    }

}