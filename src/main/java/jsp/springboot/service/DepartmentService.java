package jsp.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jsp.springboot.dao.DepartmentDao;
import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Department;
import jsp.springboot.exception.IdNotFoundException;
import jsp.springboot.exception.NoRecordAvailableException;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentDao departmentDao;
	
	public ResponseEntity<ResponseStructure<Department>> saveDepartment(Department department) {
		
		Department savedDepartment = departmentDao.saveDepartment(department);
		
		ResponseStructure<Department> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Department record saved successfully");
		response.setData(savedDepartment);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Department>>> getAllDepartments(){
		
		
		ResponseStructure<List<Department>> response = new ResponseStructure<>();
		response.setStatusCode(HttpStatus.FOUND.value());
		response.setMessage("Department records Retrieved");
		response.setData(departmentDao.getAllDepartments());
		return new ResponseEntity<ResponseStructure<List<Department>>>(response, HttpStatus.FOUND);
	}
	
	public ResponseEntity<ResponseStructure<Department>> getDepartmentById(Integer id) {

        Optional<Department> optional = departmentDao.getDepartmentById(id);

        if (optional.isPresent()) {

            ResponseStructure<Department> response = new ResponseStructure<>();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Department record Found Successfully");
            response.setData(optional.get());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            throw new IdNotFoundException("Department Id " + id + " not found");
        }
	}
	
	public ResponseEntity<ResponseStructure<Department>> updateDepartment(Department department) {

        ResponseStructure<Department> response = new ResponseStructure<>();

        // Case 3 — ID not provided
        if (department.getDepartmentId() == null) {
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Id must be passed to update a record");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Department> optional = departmentDao.getDepartmentById(department.getDepartmentId());

        // Case 1 — Record exists
        if (optional.isPresent()) {

        	Department updatedBook = departmentDao.saveDepartment(department);

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Department record updated successfully");
            response.setData(updatedBook);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // Case 2 — ID not found
        else {
            throw new IdNotFoundException("Department with ID " + department.getDepartmentId() + " does not exist");
        }
    }
	
	public ResponseEntity<ResponseStructure<String>> deleteDepartment(Integer id) {

	    ResponseStructure<String> response = new ResponseStructure<>();

	    Optional<Department> optional = departmentDao.getDepartmentById(id);

	    // Case 1: Book exists -> delete
	    if (optional.isPresent()) {

	    	departmentDao.deleteDepartment(optional.get());

	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Department deleted successfully");
	        response.setData("Deleted Department ID: " + id);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    // Case 2: Book not found
	    else {
	        throw new IdNotFoundException("Department with ID " + id + " does not exist");
	    }
	}
	
	public ResponseEntity<ResponseStructure<Department>> getDepartmentByName(String name) {

	    ResponseStructure<Department> response = new ResponseStructure<>();

	    Department department = departmentDao.getDepartmentByName(name);

	    if (department.getDepartmentName() == null) {
	    	throw new NoRecordAvailableException("Book record with name: " + name + " not found in DB");
	    } else {
	    	response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Department Record with Name: " + name + " retrieved successfully");
	        response.setData(department);

	        return new ResponseEntity<>(response, HttpStatus.OK);
	       
	    }
	}
}
