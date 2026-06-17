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
import jsp.springboot.entity.Patient;
import jsp.springboot.service.PatientService;

@RestController
@RequestMapping("/hospital/patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Patient>> savePatient(@RequestBody Patient patient){
		return patientService.savePatient(patient);
	}
	
	@GetMapping("/get")
	public ResponseEntity<ResponseStructure<List<Patient>>> getAllPatient() {
		return patientService.getAllPatients();
	}
	
	@GetMapping("/get/id/{id}")
	public ResponseEntity<ResponseStructure<Patient>> getPatientById(@PathVariable Integer id) {
		 return patientService.getPatientById(id);
	}
	
	@GetMapping("/get/phone/{phone}")
	public ResponseEntity<ResponseStructure<Patient>> getPatientByPhone(@PathVariable String phone) {
		 return patientService.getPatientByPhone(phone);
	}

	@GetMapping("/get/age/{age}")
	public ResponseEntity<ResponseStructure<List<Patient>>> getPatientsWithAgeGreaterThan(@PathVariable Integer age){
		return patientService.getPatientsWithAgeGreaterThan(age);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ResponseStructure<Patient>> updatePatient(@RequestBody Patient patient) {
		return patientService.updatePatient(patient);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteDoctor(@PathVariable Integer id) {
		return patientService.deletePatient(id);   
	}
}
