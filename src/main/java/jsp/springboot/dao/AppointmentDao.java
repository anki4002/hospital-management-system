package jsp.springboot.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jsp.springboot.entity.Appointment;
import jsp.springboot.entity.AppointmentStatus;
import jsp.springboot.repository.AppointmentRepository;

@Repository
public class AppointmentDao {
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	public Appointment saveAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}
	
	public List<Appointment> getAllAppointment(){
		return appointmentRepository.findAll();
	}
	
	public Optional<Appointment> getAppointmentById(Integer id) {
        return appointmentRepository.findById(id);
    }
	
	public List<Appointment> getAppointmentsByDate(LocalDateTime start, LocalDateTime end) {

        return appointmentRepository.findByAppointmentDateTimeBetween(start, end);
    }
	
	public List<Appointment> getAppointmentsByDoctorId(Integer doctorId) {
        return appointmentRepository.findByDoctorDoctorId(doctorId);
    }
	
	public List<Appointment> getAppointmentsByPatientId(Integer patientId) {
        return appointmentRepository.findByPatientPatientId(patientId);
    }
	
	public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }
}
