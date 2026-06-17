package jsp.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.springboot.dto.ResponseStructure;
import jsp.springboot.entity.Department;
import jsp.springboot.service.DepartmentService;

@RestController
@RequestMapping("/hospital/department")

public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	//create department
	@PostMapping("/add")
	public ResponseEntity<ResponseStructure<Department>> saveDepartment(@RequestBody Department department){
		return departmentService.saveDepartment(department);
	}
	
	//fetch all department records
	@GetMapping("/get")
	public ResponseEntity<ResponseStructure<List<Department>>> getAllDepartments(){
		return departmentService.getAllDepartments();
	}	
	
	//fetch department by id	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseStructure<Department>> getDepartmentById(@PathVariable Integer id) {
		 return departmentService.getDepartmentById(id);
	}
	
	//update department (2 records with same name cannot exist)
	@PutMapping("/update")
	public ResponseEntity<ResponseStructure<Department>> updateDepartment(@RequestBody Department department) {
		return departmentService.updateDepartment(department);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteBook(@PathVariable Integer id) {
		return departmentService.deleteDepartment(id);   
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<ResponseStructure<Department>> getBookByDeptName(@PathVariable String name){
		 return departmentService.getDepartmentByName(name);
	}
}
