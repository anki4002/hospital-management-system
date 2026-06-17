package jsp.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jsp.springboot.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer>{

}
