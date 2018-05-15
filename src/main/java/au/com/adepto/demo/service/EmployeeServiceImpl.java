package au.com.adepto.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Employee;
import au.com.adepto.demo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Optional<Employee> findById(long id) throws ResourceNotFoundException {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (!employee.isPresent()) {
			throw new ResourceNotFoundException("Employee with id #" + id + " doesn't exist.");
		}
		return employee;
	}
	
	@Override
	public Iterable<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee create(Employee employee) throws ResourceAlreadyExistException {
		if (employeeRepository.findById(employee.getId()).isPresent()) {
			throw new ResourceAlreadyExistException("Employee with id #" + employee.getId() + " already exists.");
		}
		return employeeRepository.save(employee);
	}

}