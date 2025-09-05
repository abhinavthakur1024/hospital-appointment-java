package com.csg.hospital.services;

import com.csg.hospital.Database;
import com.csg.hospital.models.Appointment;

import java.sql.*;
import java.util.*;

public class AppointmentService {
    // Booking queue to demonstrate DS: Queue<Request> (FIFO processing)
    public static class BookingRequest {
        public final int doctorId;
        public final int patientId;
        public final String time;
        public BookingRequest(int doctorId, int patientId, String time) {
            this.doctorId = doctorId;
            this.patientId = patientId;
            this.time = time;
        }
    }

    private final Queue<BookingRequest> bookingQueue = new ArrayDeque<>();

    public void enqueueBooking(int doctorId, int patientId, String time) {
        bookingQueue.add(new BookingRequest(doctorId, patientId, time));
    }

    public Optional<Appointment> processNext() throws SQLException {
        BookingRequest req = bookingQueue.poll();
        if (req == null) return Optional.empty();
        return Optional.ofNullable(book(req.patientId, req.doctorId, req.time));
    }

    public int queueSize() {
        return bookingQueue.size();
    }

    public Appointment book(int patientId, int doctorId, String time) throws SQLException {
        String sql = "INSERT INTO appointments(doctor_id, patient_id, time, status) VALUES(?, ?, ?, 'BOOKED')";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, patientId);
            ps.setString(3, time);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new Appointment(id, doctorId, patientId, time, "BOOKED");
                }
            }
        } catch (SQLException e) {
            // Unique(doctor_id, time) -> slot already taken
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("unique")) {
                throw new SQLException("Time slot already booked for this doctor.", e);
            }
            throw e;
        }
        throw new SQLException("Failed to create appointment");
    }

    public boolean cancel(int appointmentId) throws SQLException {
        String sql = "UPDATE appointments SET status='CANCELLED' WHERE id=? AND status='BOOKED'";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Appointment> listForDoctor(int doctorId) throws SQLException {
        String sql = "SELECT id, doctor_id, patient_id, time, status FROM appointments WHERE doctor_id=? ORDER BY time";
        List<Appointment> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Appointment(
                        rs.getInt("id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getString("time"),
                        rs.getString("status")
                    ));
                }
            }
        }
        return list;
    }

    public Optional<String> findNextAvailableSlot(int doctorId, String fromTimeIso) throws SQLException {
        // Simple approach: find the next 30-min increment after fromTime that isn't booked
        // We'll check the next 16 slots (8 hours) to keep it simple.
        try (Connection conn = Database.getConnection()) {
            String checkSql = "SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND time=? AND status='BOOKED'";
            for (int i = 0; i < 16; i++) {
                java.time.LocalDateTime t = java.time.LocalDateTime.parse(fromTimeIso, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                        .plusMinutes(30L * i);
                String candidate = t.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                    ps.setInt(1, doctorId);
                    ps.setString(2, candidate);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && rs.getInt(1) == 0) {
                            return Optional.of(candidate);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}