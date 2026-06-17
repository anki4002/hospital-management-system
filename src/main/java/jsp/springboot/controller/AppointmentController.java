package jsp.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Appointment;
import jsp.springboot.service.AppointmentService;

@RestController	
@RequestMapping("/hospital/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<Appointment>> saveAppointment(@RequestBody Appointment appointment){
		return appointmentService.saveAppointment(appointment);
	}
	
	@GetMapping("/get")
	public ResponseEntity<ResponseStructure<List<Appointment>>> getAllAppointment(){
		return appointmentService.getAllAppointment();
	}	
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseStructure<Appointment>> getAppointmentById(@PathVariable Integer id ){
		return appointmentService.getAppointmentById(id);
	}
	
	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<ResponseStructure<List<Appointment>>> getAppointmentsByDoctorId(@PathVariable Integer doctorId) {
	        return appointmentService.getAppointmentsByDoctorId(doctorId);
	    }
	
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<ResponseStructure<List<Appointment>>> getAppointmentsByPatientId(@PathVariable Integer patientId) {
	        return appointmentService.getAppointmentsByPatientId(patientId);
	    }
	
	@GetMapping("/status/{status}")
    public ResponseEntity<ResponseStructure<List<Appointment>>> getAppointmentsByStatus(@PathVariable String status) {
        return appointmentService.getAppointmentsByStatus(status);
    }
	
	@PutMapping("/{appointmentId}/cancel")
	public ResponseEntity<ResponseStructure<Appointment>> cancelAppointment(@PathVariable Integer appointmentId) {
		return appointmentService.cancelAppointment(appointmentId);
	}
}
