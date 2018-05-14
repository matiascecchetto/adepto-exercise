package au.com.adepto.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Employee;
import au.com.adepto.demo.repository.EmployeeRepository;

@Service
public class EmployeeService implements IEmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Iterable<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public void create(Employee employee) throws ResourceAlreadyExistException {
		if (employeeRepository.findById(employee.getId()).isPresent()) {
			throw new ResourceAlreadyExistException("Employee with id #" + employee.getId() + " already exists.");
		}
		employeeRepository.save(employee);
	}

}