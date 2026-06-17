package jsp.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jsp.springboot.dao.AppointmentDao;
import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Appointment;
import jsp.springboot.entity.AppointmentStatus;
import jsp.springboot.exception.IdNotFoundException;
import jsp.springboot.exception.NoRecordAvailableException;


@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentDao appointmentDao;
	
	public ResponseEntity<ResponseStructure<Appointment>> saveAppointment(Appointment appointment) {
		
		Appointment savedAppointment = appointmentDao.saveAppointment(appointment);
		
		ResponseStructure<Appointment> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Appointment saved successfully");
		response.setData(savedAppointment);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Appointment>>> getAllAppointment(){
			
		ResponseStructure<List<Appointment>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.FOUND.value());
		response.setMessage("Appointment record retrieved");
		response.setData(appointmentDao.getAllAppointment());
		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}
	

	public ResponseEntity<ResponseStructure<Appointment>> getAppointmentById(Integer id) {

        Optional<Appointment> optional = appointmentDao.getAppointmentById(id);

        if (optional.isPresent()) {

            ResponseStructure<Appointment> response = new ResponseStructure<>();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Appointment Found Successfully");
            response.setData(optional.get());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            throw new IdNotFoundException("Appointment Id " + id + " not found");
        }
	}
	
	public ResponseEntity<ResponseStructure<List<Appointment>>> getAppointmentsByDoctorId(Integer doctorId) {

		List<Appointment> appointments = appointmentDao.getAppointmentsByDoctorId(doctorId);

		ResponseStructure<List<Appointment>> response = new ResponseStructure<>();
		
		if(!appointments.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Appointments fetched successfully");
			response.setData(appointments);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new IdNotFoundException("Doctor Id " + doctorId + " has no appointments");
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Appointment>>> getAppointmentsByPatientId(Integer patientId) {

		List<Appointment> appointments = appointmentDao.getAppointmentsByPatientId(patientId);

		ResponseStructure<List<Appointment>> response = new ResponseStructure<>();
		
		if(!appointments.isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Appointments fetched successfully");
			response.setData(appointments);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new IdNotFoundException("Patient Id " + patientId + " has no appointments");
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Appointment>>> getAppointmentsByStatus(String status) {

		AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());

		List<Appointment> appointments = appointmentDao.getAppointmentsByStatus(appointmentStatus);
		
		if(!appointments.isEmpty()) {
			ResponseStructure<List<Appointment>> response = new ResponseStructure<>();

			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Appointments fetched successfully");
			response.setData(appointments);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new NoRecordAvailableException("No Appointments");
		}
	}
	
	 public ResponseEntity<ResponseStructure<Appointment>> cancelAppointment(Integer appointmentId) {

		 Optional<Appointment> optional = appointmentDao.getAppointmentById(appointmentId);
		 ResponseStructure<Appointment> response = new ResponseStructure<>();

		 if (optional.isEmpty()) {
			 response.setStatusCode(404);
			 response.setMessage("Appointment not found");
			 response.setData(null);
			 return ResponseEntity.status(404).body(response);
		 }

		 Appointment appointment = optional.get();

		 // Optional validation
		 if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
			 response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			 response.setMessage("Appointment already cancelled");
			 response.setData(appointment);
			 return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		 }

		 if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
			 response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			 response.setMessage("Completed appointment cannot be cancelled");
			 response.setData(appointment);
			 return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		 }

		 appointment.setStatus(AppointmentStatus.CANCELLED);

		 Appointment updated = appointmentDao.saveAppointment(appointment);

		 response.setStatusCode(HttpStatus.OK.value());
		 response.setMessage("Appointment cancelled successfully");
		 response.setData(updated);

		 return new ResponseEntity<>(response, HttpStatus.OK);
	 }
}
