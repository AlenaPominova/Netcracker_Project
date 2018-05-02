package com.parking.server.objects;


//import javax.persistence.Column;
import java.io.Serializable;
import java.util.Map;

public class User implements Serializable {

    private long id;
    private String fio;
    private String phone;
    private String email;
    private String password;
    private String address;
    private String birthDate;
    private Map<Long, String> attr_name;


    public User(long id) {
        this.id = id;
    }

   // @Column(name = "USER_ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   // @Column(name = "FIO")
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

   // @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

  //  @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //@Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //@Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

   // @Column(name = "BIRTH_DATE")
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Map<Long, String> getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(Map<Long, String> attr_name) {
        this.attr_name = attr_name;
    }
}
