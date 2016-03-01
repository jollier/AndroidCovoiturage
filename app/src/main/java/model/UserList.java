package model;

/**
 * Created by AREDON on 29/02/2016.
 */
public class UserList {

    public String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String address;
    public UserList(){
        super();
    }

    public UserList(String name, String address) {
        super();
        this.name = name;
        this.address = address;
    }
}
