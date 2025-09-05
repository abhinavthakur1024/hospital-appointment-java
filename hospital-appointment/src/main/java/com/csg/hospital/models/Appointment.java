package com.csg.hospital.models;

public class Appointment {
    private int id;
    private int doctorId;
    private int patientId;
    private String time;   // ISO string: "YYYY-MM-DD HH:MM"
    private String status; // BOOKED | CANCELLED

    public Appointment(int id, int doctorId, int patientId, String time, String status) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.time = time;
        this.status = status;
    }

    public Appointment(int doctorId, int patientId, String time) {
        this(-1, doctorId, patientId, time, "BOOKED");
    }

    public int getId() { return id; }
    public int getDoctorId() { return doctorId; }
    public int getPatientId() { return patientId; }
    public String getTime() { return time; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}