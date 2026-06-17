package jsp.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jsp.springboot.entity.DayOfWeek;
import jsp.springboot.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
	
	List<Doctor> findBySpecialization(String specialization);
	
	List<Doctor> findByDepartment(String department);

	List<Doctor> findByAvailableDays(DayOfWeek day);
}
