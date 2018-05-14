package au.com.adepto.demo.service;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Employee;

public interface IEmployeeService {

	void create(Employee employee) throws ResourceAlreadyExistException;

	Iterable<Employee> findAll();

}