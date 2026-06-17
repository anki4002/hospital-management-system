package jsp.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.DayOfWeek;
import jsp.springboot.entity.Doctor;
import jsp.springboot.service.DoctorService;


@RestController
@RequestMapping("/hospital/doctor")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	//create doctor
	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<Doctor>> saveDoctor(@RequestBody Doctor doctor){
		return doctorService.saveDoctor(doctor);
	}
	
	//fetch all doctor records
	@GetMapping("/get")
	public ResponseEntity<ResponseStructure<List<Doctor>>> getAllDoctors(){
		return doctorService.getAllDoctors();
	}	
	
	//fetch doctor by id
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseStructure<Doctor>> getDoctorById(@PathVariable Integer id ){
		return doctorService.getDoctorById(id);
	}
	
	//fetch doctor by specialization
	@GetMapping("/get/specialization/{specialization}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> getDoctorBySpecialization(@PathVariable String specialization){
		return doctorService.getDoctorBySpecialization(specialization);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseStructure<Doctor>> updateDoctor(@RequestBody Doctor doctor) {
		return doctorService.updateDoctor(doctor);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteDoctor(@PathVariable Integer id) {
		return doctorService.deleteDoctor(id);   
	}
	
	@GetMapping("/get/day/{day}")
	public ResponseEntity<ResponseStructure<List<Doctor>>> getDoctorByAvailableDays(@PathVariable DayOfWeek day){
		return doctorService.getDoctorByAvailableDays(day);
	}
}
