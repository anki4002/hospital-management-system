package jsp.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jsp.springboot.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    
	Optional<Patient> findByPhone(String phone);
    
	List<Patient> findByAgeGreaterThan(Integer age);
}
