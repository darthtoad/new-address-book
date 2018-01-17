package dao;

import models.Address;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

/**
 * Created by Guest on 1/16/18.
 */
public class Sql2oAddressDaoTest {
    private Sql2oEntryDao entryDao;
    private Sql2oAddressDao addressDao;
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        addressDao = new Sql2oAddressDao(sql2o);
        entryDao = new Sql2oEntryDao(sql2o);
        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingAddressSetsId() throws Exception {
        Address address = new Address("123 Sesame St", "New York", "New York", "USA", "10110");
        int originalAddressId = address.getId();
        addressDao.add(address);
        assertNotEquals(originalAddressId, address.getId());
    }

    @Test
    public void existingAddressesCanBeFoundById() throws Exception {
        Address address = new Address("123 Sesame St", "New York", "New York", "USA", "10110");
        addressDao.add(address);
        Address foundAddress = addressDao.findById(address.getId());
        assertEquals(address, foundAddress);
    }

    @Test
    public void getAll_getsAllAddresses() throws Exception {
        Address address = new Address("123 Sesame St", "New York", "New York", "USA", "10110");
        addressDao.add(address);
        assertEquals(1, addressDao.getAll().size());
    }

    @Test
    public void getAll_noAddressesReturnsEmptyList() throws Exception {
        assertEquals(0, addressDao.getAll().size());
    }

    @Test
    public void editAddress_changesAddress() throws Exception {
        String zip = "10110";
        Address address = new Address("123 Sesame St", "New York", "New York", "USA", zip);
        addressDao.add(address);

        addressDao.editAddress("4911 Hatfield St", "Pittsburgh", "Pennsylvania", "US", "15217", address.getId());
        Address edittedAddress = addressDao.findById(address.getId());
        assertNotEquals(zip, edittedAddress.getZip());
    }

    @Test
    public void deleteByIdDeletesCorrectAddress() throws Exception {
        Address address = new Address("123 Sesame St", "New York", "New York", "USA", "10110");
        Address address1 = new Address("4911 Hatfield St", "Pittsburgh", "Pennsylvania", "US", "15217");
        addressDao.add(address);
        addressDao.add(address1);
        addressDao.deleteById(1);
        assertEquals(1, addressDao.getAll().size());
    }

    @Test
    public void clearAllAddressesClearsAll() throws Exception {
        Address address = new Address("123 Sesame St", "New York", "New York", "USA", "10110");
        Address address1 = new Address("4911 Hatfield St", "Pittsburgh", "Pennsylvania", "US", "15217");
        addressDao.add(address);
        addressDao.add(address1);
        addressDao.clearAllAddresses();
        assertEquals(0, addressDao.getAll().size());
    }
}