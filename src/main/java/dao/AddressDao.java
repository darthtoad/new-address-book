package dao;

import models.Address;
import models.Entry;

import java.util.List;

/**
 * Created by Guest on 1/16/18.
 */
public interface AddressDao {
    void add (Address address);

    List<Address> getAll();
    List<Entry> getAllEntriesByAddress(int addressId);

    Address findById(int id);

    void editAddress(String street, String city, String state, String country, String zip, int id);

    void deleteById(int id);

    void clearAllAddresses();
}
