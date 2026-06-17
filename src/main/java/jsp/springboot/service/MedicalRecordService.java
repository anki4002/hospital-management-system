package jsp.springboot.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jsp.springboot.dao.MedicalRecordDao;
import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.MedicalRecord;
import jsp.springboot.exception.IdNotFoundException;

@Service
public class MedicalRecordService {
	
	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	public ResponseEntity<ResponseStructure<MedicalRecord>> saveMedicalRecord(MedicalRecord medicalRecord) {
		
	MedicalRecord savedMedicalRecord= medicalRecordDao.saveMedicalRecord(medicalRecord);
		
		ResponseStructure<MedicalRecord> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("MedicalRecord record saved successfully");
		response.setData(savedMedicalRecord);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> getAllMedicalRecord(){
		
		
		ResponseStructure<List<MedicalRecord>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.FOUND.value());
		response.setMessage("MedicalRecord records Retrieved");
		response.setData(medicalRecordDao.getAllMedicalRecord());
		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}
	
	public ResponseEntity<ResponseStructure<MedicalRecord>> getMedicalRecordById(Integer id) {

        Optional<MedicalRecord> optional = medicalRecordDao.getMedicalRecordById(id);

        if (optional.isPresent()) {

            ResponseStructure<MedicalRecord> response = new ResponseStructure<>();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Medical Records Found Successfully");
            response.setData(optional.get());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            throw new IdNotFoundException("Medical Record with Id " + id + " not found");
        }
	}
	
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> getMedicalRecordsByPatientId(Integer patientId){

	    List<MedicalRecord> records = 
	            medicalRecordDao.getMedicalRecordsByPatientId(patientId);

	    if(!records.isEmpty()){

	        ResponseStructure<List<MedicalRecord>> response = new ResponseStructure<>();
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Medical Records Found Successfully");
	        response.setData(records);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    else{
	        throw new IdNotFoundException(
	                "No Medical Records found for Patient Id " + patientId);
	    }
	}
	
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> getMedicalRecordsByDoctorId(Integer doctorId){
	    List<MedicalRecord> records = 
	            medicalRecordDao.getMedicalRecordsByDoctorId(doctorId);

	    if(!records.isEmpty()){

	        ResponseStructure<List<MedicalRecord>> response = new ResponseStructure<>();
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Medical Records Found for Doctor");
	        response.setData(records);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    else{
	        throw new IdNotFoundException(
	                "No Medical Records found for Doctor Id " + doctorId);
	    }
	}
	
	public ResponseEntity<ResponseStructure<MedicalRecord>> getMedicalRecordByAppointmentId(Integer appointmentId){
	    
		Optional<MedicalRecord> optional = medicalRecordDao.getMedicalRecordByAppointmentId(appointmentId);

	            
	    if(optional.isPresent()){

	        ResponseStructure<MedicalRecord> response = new ResponseStructure<>();
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Medical Record Found for Appointment");
	        response.setData(optional.get());

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    else{
	        throw new IdNotFoundException("Medical Record not found for Appointment Id " + appointmentId);
	    }
	}
	
	public ResponseEntity<ResponseStructure<List<MedicalRecord>>> getMedicalRecordsByVisitDate(LocalDate visitDate){
	
	    List<MedicalRecord> records = medicalRecordDao.getMedicalRecordsByVisitDate(visitDate);
	            
	    if(!records.isEmpty()){

	        ResponseStructure<List<MedicalRecord>> response = new ResponseStructure<>();
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Medical Records Found for Visit Date");
	        response.setData(records);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    else{
	        throw new IdNotFoundException("No Medical Records found for Visit Date " + visitDate);
	                
	    }
	}




}
