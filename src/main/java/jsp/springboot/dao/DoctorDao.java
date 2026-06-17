package jsp.springboot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jsp.springboot.entity.DayOfWeek;
import jsp.springboot.entity.Doctor;
import jsp.springboot.repository.DoctorRepository;


@Repository
public class DoctorDao {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	public Doctor saveDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}
	
	public List<Doctor> getAllDoctors(){
		return doctorRepository.findAll();
	}
	
	public Optional<Doctor> getDoctorById(Integer id) {
        return doctorRepository.findById(id);
    }
	
	public List<Doctor> getDoctorBySpecialization(String specialization){
		return doctorRepository.findBySpecialization(specialization);
	}
	
	public List<Doctor> getDoctorByDepartment(String department){
		return doctorRepository.findByDepartment(department);
	}
	
	public void deleteDoctor(Doctor doctor) {
		doctorRepository.delete(doctor);
	 }
	
	public List<Doctor> getDoctorByAvailableDays(DayOfWeek day) {
		return doctorRepository.findByAvailableDays(day);
	}
}

