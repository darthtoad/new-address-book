package models;

/**
 * Created by Guest on 1/16/18.
 */
public class Entry {
    private String firstName;
    private String lastName;
    private int addressId;
    private int id;
    private String phone;
    public Entry(String firstName, String lastName, int addressId, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
        this.phone = phone;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAddressId() {
        return this.addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void editEntry(String firstName, String lastName, int addressId, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (addressId != entry.addressId) return false;
        if (id != entry.id) return false;
        if (!firstName.equals(entry.firstName)) return false;
        if (!lastName.equals(entry.lastName)) return false;
        return phone.equals(entry.phone);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + addressId;
        result = 31 * result + id;
        result = 31 * result + phone.hashCode();
        return result;
    }
}
