package jsp.springboot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jsp.springboot.entity.Patient;
import jsp.springboot.repository.PatientRepository;

@Repository
public class PatientDao {
	
	@Autowired
	private PatientRepository patientRepository;
	
	public Patient savePatient(Patient patient) {
		return patientRepository.save(patient);
	}
	
	public List<Patient> getAllPatient() {
		return patientRepository.findAll();
	}
	
	public Optional<Patient> getPatientById(Integer id) {
		return patientRepository.findById(id);
	}
	
	public Optional<Patient> getPatientByPhone(String phone){
		return patientRepository.findByPhone(phone);
	}
	
	public List<Patient> getPatientsWithAgeGreaterThan(Integer age) {
	    return patientRepository.findByAgeGreaterThan(age);
	}
	
	public void deletePatient(Patient patient) {
		patientRepository.delete(patient);
	 }
	
	
}