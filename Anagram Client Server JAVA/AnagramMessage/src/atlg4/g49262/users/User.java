package atlg4.g49262.users;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Nicolas Rossitto, <49282@etu.he2b.be>
 */
public class User implements Serializable {

    public static final User ADMIN = new User(0, "ADMIN");

    public static final User EVERYBODY = new User(0, "EVERYBODY");

    private final int id;
    private String name;
    private InetAddress address;

    public User(int id, String name, InetAddress address) {
        this.name = name;
        this.id = id;
        this.address = address;
    }

    public User(int id, String name) {
        this(id, name, null);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address.getHostAddress();
    }

    void setName(String name) {
        this.name = name;
    }

    boolean is(int id) {
        return this.id == id;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return this.id == other.id;
    }

}
