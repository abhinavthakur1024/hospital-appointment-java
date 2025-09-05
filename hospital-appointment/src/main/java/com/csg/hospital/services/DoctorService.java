package com.csg.hospital.services;

import com.csg.hospital.Database;
import com.csg.hospital.models.Doctor;

import java.sql.*;
import java.util.*;

public class DoctorService {
    // In-memory specialty index: specialty -> list of doctors (DS: HashMap + ArrayList)
    private final Map<String, List<Doctor>> specialtyIndex = new HashMap<>();

    public Doctor addDoctor(String name, String specialty) throws SQLException {
        String sql = "INSERT INTO doctors(name, specialty) VALUES(?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, specialty);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    Doctor d = new Doctor(id, name, specialty);
                    specialtyIndex.computeIfAbsent(specialty.toLowerCase(), k -> new ArrayList<>()).add(d);
                    return d;
                }
            }
        }
        throw new SQLException("Failed to create doctor");
    }

    public List<Doctor> findBySpecialty(String specialty) throws SQLException {
        // First try in-memory index
        List<Doctor> cached = specialtyIndex.get(specialty.toLowerCase());
        if (cached != null && !cached.isEmpty()) return cached;

        String sql = "SELECT id, name, specialty FROM doctors WHERE lower(specialty)=lower(?) ORDER BY name";
        List<Doctor> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, specialty);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Doctor(rs.getInt("id"), rs.getString("name"), rs.getString("specialty")));
                }
            }
        }
        if (!list.isEmpty()) specialtyIndex.put(specialty.toLowerCase(), list);
        return list;
    }

    public Optional<Doctor> getById(int id) throws SQLException {
        String sql = "SELECT id, name, specialty FROM doctors WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Doctor(rs.getInt("id"), rs.getString("name"), rs.getString("specialty")));
                }
            }
        }
        return Optional.empty();
    }
}