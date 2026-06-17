package jsp.springboot.repository;

import jsp.springboot.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
		// fetch Department based on title
		Department findBydepartmentName(String name);
}