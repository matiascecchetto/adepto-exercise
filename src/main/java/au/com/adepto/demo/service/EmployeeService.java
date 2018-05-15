package au.com.adepto.demo.service;

import java.util.Optional;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Employee;

public interface EmployeeService {

	Employee create(Employee employee) throws ResourceAlreadyExistException;

	Iterable<Employee> findAll();

	Optional<Employee> findById(long id) throws ResourceNotFoundException;

}