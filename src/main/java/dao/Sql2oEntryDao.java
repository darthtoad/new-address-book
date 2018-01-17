package dao;

import models.Entry;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Guest on 1/16/18.
 */
public class Sql2oEntryDao implements EntryDao {

    private final Sql2o sql2o;

    public Sql2oEntryDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Entry entry) {
        String sql = "INSERT INTO entry (firstName, lastName, addressId, phone) VALUES (:firstName, :lastName, :addressId, :phone)";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(entry)
                    .addParameter("firstName", entry.getFirstName())
                    .addParameter("lastName", entry.getLastName())
                    .addParameter("addressId", entry.getAddressId())
                    .addParameter("phone", entry.getPhone())
                    .addColumnMapping("FIRSTNANE","firstName")
                    .addColumnMapping("LASTNAME","lastName")
                    .addColumnMapping("ADDRESSID", "addressId")
                    .addColumnMapping("PHONE", "phone")
                    .executeUpdate()
                    .getKey();
            entry.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Entry> getAll() {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM entry")
                    .executeAndFetch(Entry.class);
        }    }

    @Override
    public Entry findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM entry WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Entry.class);
        }
    }

    @Override
    public void editEntry(String firstName, String lastName, int addressId, String phone, int id) {
        String sql = "UPDATE entry SET firstName = :firstName, lastName = :lastName, addressId = :addressId, phone = :phone WHERE id=:id";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("firstName", firstName)
                    .addParameter("lastName", lastName)
                    .addParameter("addressId", addressId)
                    .addParameter("phone", phone)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from entry WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from entry";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
