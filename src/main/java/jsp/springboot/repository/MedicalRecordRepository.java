package jsp.springboot.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jsp.springboot.entity.Appointment;
import jsp.springboot.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
	
	Optional<MedicalRecord> findByAppointmentAppointmentId(Integer appointmentId);
	
	 List<MedicalRecord> findByPatientPatientId(Integer patientId);
	 
	 List<MedicalRecord> findByDoctorDoctorId(Integer doctorId);
	 
	 List<MedicalRecord> findByVisitDate(LocalDate visitDate);


}
