# Hospital Appointment Booking System (Java + SQLite)

**Why this project?**
- Demonstrates **OOP** (Doctor/Patient/Appointment classes), **Data Structures** (HashMap for specialty index, Queue for booking requests, Lists), and **SDLC**.
- Uses **SQL** with **SQLite (embedded)** via JDBC — no server setup required.
- Clean CLI so interviewers can test quickly.

## Tech
- Java 17
- Gradle
- SQLite (via `org.xerial:sqlite-jdbc`)

## Run (VS Code or terminal)
1. Install Java 17 and Gradle (or use VS Code Extension Pack for Java).
2. Open folder in VS Code.
3. Terminal:
   ```bash
   ./gradlew run         # macOS/Linux
   gradlew.bat run       # Windows
   ```

> First run auto-creates `hospital.db` and the tables.

## Menu Demo
- Add doctors/patients
- Enqueue booking requests (DS: Queue)
- Process queue to book appointments (checks unique slot per doctor)
- List appointments for a doctor (ordered by time)
- Cancel appointment
- Find next available 30-min slot

## Where DS + OOP show up
- **OOP**: `Doctor`, `Patient`, `Appointment` classes; services encapsulate DB logic.
- **DS**:
  - `HashMap<String, List<Doctor>>` as in-memory **specialty index** (fast lookup).
  - `ArrayDeque<BookingRequest>` as **Queue** for booking (FIFO).
  - `ArrayList` for collections.
- **Algorithms**:
  - Unique index `(doctor_id, time)` prevents double bookings (integrity).
  - Simple slot search scans next 16 half-hours.

## Project Structure
```
hospital-appointment/
  ├── build.gradle
  ├── settings.gradle
  └── src/main/java/com/csg/hospital/...
```

## Notes
- DB file: `hospital.db` in project root.
- For a GUI/Web version later, keep these services as the core and add a REST layer.