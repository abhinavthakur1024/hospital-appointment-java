package com.csg.hospital.models;

public class Patient {
    private int id;
    private String name;
    private String phone;

    public Patient(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Patient(String name, String phone) {
        this(-1, name, phone);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }

    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}