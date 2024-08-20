package com.example.gilis_day_care.Model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Kid {


    private String id;  // Unique identifier
    private String name;
    private String className;
    private String birthYear;
    private String email;
    private String address;
    private String parent1;
    private String parent2;
    private String phone1;
    private String phone2;
    private String sos;
    private String allergies;
    private ArrayList<String> days;
    private int state;
    private boolean girl;
    private boolean isLate = false;


    public Kid() {}

    public Kid(String name, String className, String birthYear, String email, String address, String parent1, String parent2, String phone1, String phone2, String sos, String allergies,ArrayList<String> days,boolean isGirl) {
        this.id = UUID.randomUUID().toString();  // Generate a unique ID when creating a new Event
        this.name = name;
        this.className = className;
        this.birthYear = birthYear;
        this.email = email;
        this.address = address;
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.sos = sos;
        this.allergies = allergies;
        this.days = days;
        this.state = 0;
        this.girl = isGirl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kid)) return false;
        Kid kid = (Kid) o;
        return Objects.equals(getId(), kid.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getParent1() {
        return parent1;
    }

    public String getParent2() {
        return parent2;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getSos() {
        return sos;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setParent1(String parent1) {
        this.parent1 = parent1;
    }

    public void setParent2(String parent2) {
        this.parent2 = parent2;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public boolean isGirl() {
        return girl;
    }

    public boolean isLate() {
        return isLate;
    }

    public void setLate(boolean late) {
        isLate = late;
    }

    public void setGirl(boolean girl) {
        this.girl = girl;
    }

    @Override
    public String toString() {
        return "Kid{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", parent1='" + parent1 + '\'' +
                ", parent2='" + parent2 + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", sos='" + sos + '\'' +
                ", allergies='" + allergies + '\'' +
                ", days=" + days +
                ", state=" + state +
                ", girl=" + girl +
                '}';
    }
}
