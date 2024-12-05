package com.bway.springproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bway.springproject.model.Employee;
import com.bway.springproject.model.Product;
import com.bway.springproject.repository.ProductRepository;
import com.bway.springproject.service.EmployeeService;

@RestController 
public class EmployeeRestApi {
	
	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@GetMapping("/api/emp/list")
	public List<Employee> getAll() {
		
		return empService.getAllEmps();
	}
	
	@PostMapping("/api/emp/add")
	public String add(@RequestBody Employee  emp) {
		
		empService.addEmp(emp);
		return "success";
	}
	
	@DeleteMapping("/api/emp/delete/{id}")
	public String delete(@PathVariable Long id) {
		
		empService.deleteEmp(id);
		
		return "delete success";
	}
	
	@PutMapping("/api/emp/update")
	public  String update(@RequestBody Employee emp) {
		
		empService.updateEmp(emp);
		return "update success";
	}
	
	@GetMapping("/api/emp/{id}")
	public  Employee  getOne(@PathVariable Long id) {
		
		return empService.getEmpById(id);
	}
	
	@GetMapping("/api/emp/j2o")
	public  String jsonToObject() {
		
		  RestTemplate   temp = new RestTemplate();
		  
		 Employee emp = temp.getForObject("http://localhost:8080/api/emp/3", Employee.class);
		
		return "FirstName = "+emp.getFname();
	}
	
	
	@GetMapping("/api/emp/ja2oa")
	public  String jsonArrayToObjectArray() {
		
		 RestTemplate   temp = new RestTemplate();
		  
		 Employee[] emps = temp.getForObject("http://localhost:8080/api/emp/list", Employee[].class);
		
		return "FirstName = "+emps[1].getFname();
	}
	
	@GetMapping("/api/loadProduct")
	public String loadProducts() {
		
		RestTemplate  tmp = new RestTemplate();
		Product[]  products = tmp.getForObject("https://fakestoreapi.com/products", Product[].class);
		
		prodRepo.saveAll(List.of(products));
		
		return "success";
	}
	
}
