package au.com.adepto.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Employee;
import au.com.adepto.demo.service.EmployeeService;

@Controller
@RequestMapping(path = "/api/staff")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Employee>> getAll() {
		Iterable<Employee> employees = employeeService.findAll();
		return ResponseEntity.ok(employees);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Employee employee) throws URISyntaxException {
		try {
			employeeService.create(employee);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(employee.getId()).toUri();
			return ResponseEntity.created(location).build();
			// Throws an exception when the employee already exists
		} catch (ResourceAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			// Throws an exception when the employee is null or some of the fields marked as
			// NOT NULL in the database is null
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
