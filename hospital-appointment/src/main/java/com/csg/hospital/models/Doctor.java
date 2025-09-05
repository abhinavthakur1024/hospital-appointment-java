package com.csg.hospital.models;

public class Doctor {
    private int id;
    private String name;
    private String specialty;

    public Doctor(int id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    public Doctor(String name, String specialty) {
        this(-1, name, specialty);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }

    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}