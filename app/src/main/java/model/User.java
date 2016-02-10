package model;

/**
 * Created by jollier on 10/02/2016.
 */
public class User {

    private  String lastName="";
    private  String firstName="";
    private  String email="";
    private  String addressNumber="";
    private  String addressWay="";
    private  String addressCP="";
    private  String addressCity="";
    private  String longitude="";
    private  String latitude="";
    private  String phoneNumber;
    private  String sexe;
    private  String isConducteur;
    private  String isSmoker;
    private  String area;
    private  String password;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressNumber() {
        return addressNumber;
    }
    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressWay() {
        return addressWay;
    }
    public void setAddressWay(String addressWay) {
        this.addressWay = addressWay;
    }

    public String getAddressCP() {
        return addressCP;
    }
    public void setAddressCP(String addressCP) {
        this.addressCP = addressCP;
    }

    public String getAddressCity() {
        return addressCity;
    }
    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSexe() {
        return sexe;
    }
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getIsConducteur() {
        return isConducteur;
    }
    public void setIsConducteur(String isConducteur) {
        this.isConducteur = isConducteur;
    }

    public String getIsSmoker() {
        return isSmoker;
    }
    public void setIsSmoker(String isSmoker) {
        this.isSmoker = isSmoker;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }
}
