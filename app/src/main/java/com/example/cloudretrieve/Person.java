package com.example.cloudretrieve;

/**
 * Created by James Ooi on 9/8/2017.
 */

public class Person implements java.io.Serializable {
    private String name;
    private String city;

    public Person(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public Person() {
        this("","");
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
