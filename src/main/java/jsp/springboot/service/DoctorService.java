package jsp.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jsp.springboot.dao.DoctorDao;
import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.DayOfWeek;
import jsp.springboot.entity.Doctor;
import jsp.springboot.exception.IdNotFoundException;
import jsp.springboot.exception.NoRecordAvailableException;

@Service
public class DoctorService {
	@Autowired
	private DoctorDao doctorDao;
	
	public ResponseEntity<ResponseStructure<Doctor>> saveDoctor(Doctor doctor) {
		
		Doctor savedDoctor = doctorDao.saveDoctor(doctor);
		
		ResponseStructure<Doctor> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Doctor record saved successfully");
		response.setData(savedDoctor);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Doctor>>> getAllDoctors(){
			
		ResponseStructure<List<Doctor>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.FOUND.value());
		response.setMessage("Doctors record retrieved");
		response.setData(doctorDao.getAllDoctors());
		return new ResponseEntity<ResponseStructure<List<Doctor>>>(response, HttpStatus.FOUND);
	}
	

	public ResponseEntity<ResponseStructure<Doctor>> getDoctorById(Integer id) {

        Optional<Doctor> optional = doctorDao.getDoctorById(id);

        if (optional.isPresent()) {

            ResponseStructure<Doctor> response = new ResponseStructure<>();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Doctor Found Successfully");
            response.setData(optional.get());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            throw new IdNotFoundException("Doctor Id " + id + " not found");
        }
	}
	
	public ResponseEntity<ResponseStructure<List<Doctor>>> getDoctorBySpecialization(String specialization){
		List<Doctor> doctors = doctorDao.getDoctorBySpecialization(specialization);
		
		if(!doctors.isEmpty()) {
			
			ResponseStructure<List<Doctor>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Doctor Found Successfully");
			response.setData(doctors);
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new NoRecordAvailableException("No Doctor Record under specialization : " +specialization);
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Doctor>>> getDoctorByDepartment(String department){
		List<Doctor> doctors = doctorDao.getDoctorByDepartment(department);
		
		if(!doctors.isEmpty()) {
			
			ResponseStructure<List<Doctor>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Doctor Found Successfully");
			response.setData(doctors);
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new NoRecordAvailableException("No Doctor Record under specialization : " +department);
		}
	}
	
	public ResponseEntity<ResponseStructure<Doctor>> updateDoctor(Doctor doctor) {

        ResponseStructure<Doctor> response = new ResponseStructure<>();

        // Case 3 — ID not provided
        if (doctor.getDoctorId() == null) {
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Id must be passed to update a record");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Doctor> optional =doctorDao.getDoctorById(doctor.getDoctorId());

        // Case 1 — Record exists
        if (optional.isPresent()) {

        	Doctor updatedDoctor = doctorDao.saveDoctor(doctor);

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Doctor record updated successfully");
            response.setData(updatedDoctor);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // Case 2 — ID not found
        else {
            throw new IdNotFoundException("Doctor with ID " + doctor.getDoctorId() + " does not exist");
        }
    }
	
	public ResponseEntity<ResponseStructure<String>> deleteDoctor(Integer id) {

	    ResponseStructure<String> response = new ResponseStructure<>();

	    Optional<Doctor> optional = doctorDao.getDoctorById(id);

	    // Case 1: Book exists -> delete
	    if (optional.isPresent()) {

	    	doctorDao.deleteDoctor(optional.get());

	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Doctor deleted successfully");
	        response.setData("Deleted Doctor ID: " + id);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    // Case 2: Book not found
	    else {
	        throw new IdNotFoundException("Doctor with ID " + id + " does not exist");
	    }
	}
	
	public ResponseEntity<ResponseStructure<List<Doctor>>> getDoctorByAvailableDays(DayOfWeek day){
		
		List<Doctor> doctors = doctorDao.getDoctorByAvailableDays(day);
		
		if(!doctors.isEmpty()) {
			ResponseStructure<List<Doctor>> response = new ResponseStructure<>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Doctors Found Successfully");
			response.setData(doctors);
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new NoRecordAvailableException("No Doctors available for this day");
		}
	}
}
