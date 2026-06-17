package jsp.springboot.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jsp.springboot.entity.Department;
import jsp.springboot.entity.MedicalRecord;
import jsp.springboot.repository.MedicalRecordRepository;

@Repository
public class MedicalRecordDao {
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	
	public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.save(medicalRecord);
	}
	
	public List<MedicalRecord> getAllMedicalRecord(){
		return medicalRecordRepository.findAll();
	}
	
	public Optional<MedicalRecord> getMedicalRecordById(Integer id) {
        return medicalRecordRepository.findById(id);
    }
	
	public List<MedicalRecord> getMedicalRecordsByPatientId(Integer patientId){
	    return medicalRecordRepository.findByPatientPatientId(patientId);
	}
	
	public List<MedicalRecord> getMedicalRecordsByDoctorId(Integer doctorId){
	    return medicalRecordRepository.findByDoctorDoctorId(doctorId);
	}
	
	public Optional<MedicalRecord> getMedicalRecordByAppointmentId(Integer appointmentId){
	    return medicalRecordRepository.findByAppointmentAppointmentId(appointmentId);
	}
	
	public List<MedicalRecord> getMedicalRecordsByVisitDate(LocalDate visitDate){
	    return medicalRecordRepository.findByVisitDate(visitDate);
	}




}
