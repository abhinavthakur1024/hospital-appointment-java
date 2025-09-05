# Hospital Appointment Booking System

This is a simple Hospital Appointment Booking System built using **Java**, **MySQL**, and **OOP + Data Structures** concepts.  
It allows adding doctors and patients, booking appointments, checking available slots, and cancelling appointments.

---

## Features

- Add Doctor
- Add Patient
- Find Doctors by Specialty
- Enqueue Booking Request
- Process Next Booking (Queue)
- List Appointments for a Doctor
- Cancel Appointment
- Find Next Available Slot for a Doctor

---

## Technologies Used

- **Java** (JDK 11+)
- **MySQL** (Database)
- **JDBC** (Database Connection)
- **Gradle** (Build Tool)
- **OOP + Data Structures** (Queue for appointment booking)

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/abhinavthakur1024/hospital-appointment-java.git
cd hospital-appointment-java

2. Set Up MySQL Database
Open MySQL and run:

CREATE DATABASE hospital;
USE hospital;

-- Doctors table
CREATE TABLE doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialty VARCHAR(255) NOT NULL
);

-- Patients table
CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL
);

-- Appointments table
CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id INT NOT NULL,
    patient_id INT NOT NULL,
    appointment_time DATETIME NOT NULL,
    status ENUM('BOOKED','CANCELLED') NOT NULL DEFAULT 'BOOKED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    UNIQUE KEY unique_appointment (doctor_id, appointment_time)
);


3. Add MySQL Connector

The project already has mysql-connector-j-9.3.0.jar in the lib/ folder.
Make sure it’s added to your classpath when running the project.

4. Compile and Run
javac -cp "lib/*;." com/csg/hospital/*.java com/csg/hospital/models/*.java com/csg/hospital/services/*.java com/csg/hospital/util/*.java
java -cp "lib/*;." com.csg.hospital.Main



