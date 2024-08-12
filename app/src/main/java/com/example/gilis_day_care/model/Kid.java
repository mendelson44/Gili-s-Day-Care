package com.example.gilis_day_care.model;

import java.util.List;
import java.util.Objects;

public class Kid {


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
    private List<Integer> days;
    private int state;
    private boolean girl;


    public Kid() {}

    public Kid(String name, String className, String birthYear, String email, String address, String parent1, String parent2, String phone1, String phone2, String sos, String allergies,List<Integer> days,boolean isGirl) {
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
        return Objects.equals(getName(), kid.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
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

    public List<Integer> getDays() {
        return days;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }



    public boolean isGirl() {
        return girl;
    }

    public void setGirl(boolean girl) {
        this.girl = girl;
    }

    @Override
    public String toString() {
        return "Kid{" +
                "name='" + name + '\'' +
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
