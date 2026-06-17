package jsp.springboot.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.MedicalRecord;
import jsp.springboot.service.MedicalRecordService;

@RestController
@RequestMapping("/hospital/medicalrecord")
public class MedicalRecordController {
	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	
	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<MedicalRecord>> saveMedicalRecord(@RequestBody MedicalRecord medicalRecord){
		return medicalRecordService.saveMedicalRecord(medicalRecord);
	}
	
	@GetMapping("/get")
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> getAllMedicalRecords(){
		return medicalRecordService.getAllMedicalRecord();
	}	
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseStructure<MedicalRecord>> getMedicalRecordById(@PathVariable Integer id) {
		 return medicalRecordService.getMedicalRecordById(id);
	}
	
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> getMedicalRecordsByPatientId(@PathVariable Integer patientId){
	    return medicalRecordService.getMedicalRecordsByPatientId(patientId);
	}
	
	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> getMedicalRecordsByDoctorId(@PathVariable Integer doctorId){
	    return medicalRecordService.getMedicalRecordsByDoctorId(doctorId);
	}
	
	@GetMapping("/appointment/{appointmentId}")
	public ResponseEntity<ResponseStructure<MedicalRecord>> getMedicalRecordByAppointmentId(@PathVariable Integer appointmentId){

	    return medicalRecordService.getMedicalRecordByAppointmentId(appointmentId);
	}
	
	@GetMapping("/visitdate/{visitDate}")
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> 
	getMedicalRecordsByVisitDate(
	        @PathVariable 
	        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
	        LocalDate visitDate){

	    return medicalRecordService.getMedicalRecordsByVisitDate(visitDate);
	}



}
