package jsp.springboot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jsp.springboot.entity.Department;
import jsp.springboot.entity.MedicalRecord;
import jsp.springboot.repository.DepartmentRepository;

@Repository
public class DepartmentDao {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	public Department saveDepartment(Department department) {
		return departmentRepository.save(department);
	}
	
	public List<Department> getAllDepartments(){
		return departmentRepository.findAll();
	}
	
	public Optional<Department> getDepartmentById(Integer id) {
        return departmentRepository.findById(id);
    }
	
	 public void deleteDepartment(Department department) {
		 departmentRepository.delete(department);
	 }
	 
	 public Department getDepartmentByName(String name) {
		    return departmentRepository.findBydepartmentName(name);
		}
}
  