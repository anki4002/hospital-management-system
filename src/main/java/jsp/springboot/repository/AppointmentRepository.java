package jsp.springboot.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jsp.springboot.entity.Appointment;
import jsp.springboot.entity.AppointmentStatus;
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{
	
	List<Appointment> findByAppointmentDateTimeBetween(
            LocalDateTime start,
            LocalDateTime end);
	
	List<Appointment> findByDoctorDoctorId(Integer doctorId);
	
	List<Appointment> findByPatientPatientId(Integer patientId);
	
	 List<Appointment> findByStatus(AppointmentStatus status);
}
