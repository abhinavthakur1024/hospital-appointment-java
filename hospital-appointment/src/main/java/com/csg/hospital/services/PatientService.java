package com.csg.hospital.services;

import com.csg.hospital.Database;
import com.csg.hospital.models.Patient;

import java.sql.*;
import java.util.Optional;

public class PatientService {

    public Patient addPatient(String name, String phone) throws SQLException {
        String sql = "INSERT INTO patients(name, phone) VALUES(?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new Patient(id, name, phone);
                }
            }
        }
        throw new SQLException("Failed to create patient");
    }

    public Optional<Patient> getById(int id) throws SQLException {
        String sql = "SELECT id, name, phone FROM patients WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Patient(rs.getInt("id"), rs.getString("name"), rs.getString("phone")));
                }
            }
        }
        return Optional.empty();
    }
}