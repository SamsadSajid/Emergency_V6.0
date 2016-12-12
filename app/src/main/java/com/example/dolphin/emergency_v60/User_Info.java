package com.example.dolphin.emergency_v60;

/**
 * Created by DOLPHIN on 12/12/2016.
 */
public class User_Info {
    int id;
    private String name;
    private String phone;
    private String address;
    private String institution;
    private String emergency1;
    private String emergency2;
    private String bloodgroup;

    public User_Info(){}

    public User_Info(String name,String phone,String address,String institution,String emergency1,String emergency2,String bloodgroup){
        this.name=name;
        this.phone=phone;
        this.address=address;
        this.institution=institution;
        this.emergency1=emergency1;
        this.emergency2=emergency2;
        this.bloodgroup=bloodgroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getEmergency1() {
        return emergency1;
    }

    public void setEmergency1(String emergency1) {
        this.emergency1 = emergency1;
    }

    public String getEmergency2() {
        return emergency2;
    }

    public void setEmergency2(String emergency2) {
        this.emergency2 = emergency2;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }
}




