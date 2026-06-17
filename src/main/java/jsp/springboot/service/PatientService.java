package jsp.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jsp.springboot.dao.PatientDao;
import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Patient;
import jsp.springboot.exception.IdNotFoundException;
import jsp.springboot.exception.NoRecordAvailableException;

@Service
public class PatientService {
	
	@Autowired
	private PatientDao patientDao;
	
	public ResponseEntity<ResponseStructure<Patient>> savePatient(Patient patient) {
		
		Patient savedPatient = patientDao.savePatient(patient);
		
		ResponseStructure<Patient> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Patient record saved successfully");
		response.setData(savedPatient);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<List<Patient>>> getAllPatients(){
		
		
		ResponseStructure<List<Patient>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.FOUND.value());
		response.setMessage("Records Retrieved");
		response.setData(patientDao.getAllPatient());
		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}
	
	public ResponseEntity<ResponseStructure<Patient>> getPatientById(Integer id) {

        Optional<Patient> optional = patientDao.getPatientById(id);

        if (optional.isPresent()) {

            ResponseStructure<Patient> response = new ResponseStructure<>();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Patient Found Successfully");
            response.setData(optional.get());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            throw new IdNotFoundException("Patient Id " + id + " not found");
        }
	}
	
	public ResponseEntity<ResponseStructure<Patient>> getPatientByPhone(String phone) {

        Optional<Patient> optional = patientDao.getPatientByPhone(phone);

        if (optional.isPresent()) {

            ResponseStructure<Patient> response = new ResponseStructure<>();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Patient Found Successfully");
            response.setData(optional.get());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            throw new IdNotFoundException("Patient Phone Number " + phone + " not found");
        }
	}
	public ResponseEntity<ResponseStructure<List<Patient>>> getPatientsWithAgeGreaterThan(Integer age) {

	    ResponseStructure<List<Patient>> response = new ResponseStructure<>();

	    List<Patient> patients = patientDao.getPatientsWithAgeGreaterThan(age);

	    if (!patients.isEmpty()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Patients with age greater than " + age + " retrieved successfully");
	        response.setData(patients);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        throw new NoRecordAvailableException("No patients found with age greater than " + age);
	    }
	}
	
	public ResponseEntity<ResponseStructure<Patient>> updatePatient(Patient patient) {

        ResponseStructure<Patient> response = new ResponseStructure<>();

        // Case 3 — ID not provided
        if (patient.getPatientId() == null) {
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Id must be passed to update a record");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Patient> optional =patientDao.getPatientById(patient.getPatientId());

        // Case 1 — Record exists
        if (optional.isPresent()) {

        	Patient updatedPatient = patientDao.savePatient(patient);

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Patient record updated successfully");
            response.setData(updatedPatient);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // Case 2 — ID not found
        else {
            throw new IdNotFoundException("Patient with ID " + patient.getPatientId() + " does not exist");
        }
    }
	
	public ResponseEntity<ResponseStructure<String>> deletePatient(Integer id) {

	    ResponseStructure<String> response = new ResponseStructure<>();

	    Optional<Patient> optional = patientDao.getPatientById(id);

	    // Case 1: Book exists -> delete
	    if (optional.isPresent()) {

	    	patientDao.deletePatient(optional.get());

	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Patient deleted successfully");
	        response.setData("Deleted Patient ID: " + id);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    // Case 2: Book not found
	    else {
	        throw new IdNotFoundException("Patient with ID " + id + " does not exist");
	    }
	}
}
