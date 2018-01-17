package dao;

import models.Address;
import models.Entry;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

/**
 * Created by Guest on 1/16/18.
 */
public class Sql2oAddressDao implements AddressDao {
    private final Sql2o sql2o;

    public Sql2oAddressDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Address address) {
        String sql = "INSERT INTO address (street, city, state, country, zip) VALUES (:street, :city, :state, :country, :zip)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(address)
                    .executeUpdate()
                    .getKey();
            address.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Address> getAll() {
        String sql = "SELECT * FROM address";
        try (Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .executeAndFetch(Address.class);
        }
    }

    @Override
    public Address findById(int id) {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM address WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Address.class);
        }
    }

    @Override
    public void editAddress(String street, String city, String state, String country, String zip, int id) {
        String sql = "UPDATE address SET street = :street, city = :city, state = :state, country = :country, zip = :zip WHERE id = :id";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("street", street)
                    .addParameter("city", city)
                    .addParameter("state", state)
                    .addParameter("country", country)
                    .addParameter("zip", zip)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from address WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllAddresses() {
        String sql = "DELETE from address";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Entry> getAllEntriesByAddress(int addressId) {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM entry WHERE addressId = :addressId")
                    .addParameter("addressId", addressId)
                    .executeAndFetch(Entry.class);
        }
    }


}
