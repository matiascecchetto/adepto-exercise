package au.com.adepto.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Employee;
import au.com.adepto.demo.model.Shift;
import au.com.adepto.demo.repository.EmployeeRepository;
import au.com.adepto.demo.repository.ShiftRepository;

@Service
public class ShiftServiceImpl implements ShiftService {

	@Autowired
	private ShiftRepository shiftRepository;

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Optional<Shift> findById(long id) throws ResourceNotFoundException {
		Optional<Shift> shift = shiftRepository.findById(id);
		if (!shift.isPresent()) {
			throw new ResourceNotFoundException("Shift with id #" + id + " doesn't exist.");
		}
		return shift;
	}

	@Override
	public Iterable<Shift> findAll() {
		return shiftRepository.findAll();
	}

	@Override
	public void create(Shift shift) throws ResourceAlreadyExistException {
		if (shiftRepository.findById(shift.getId()).isPresent()) {
			throw new ResourceAlreadyExistException("Shift with id #" + shift.getId() + " already exists.");
		}
		shiftRepository.save(shift);
	}

	@Override
	public Iterable<Shift> allocateStaff() {
		Iterable<Shift> shifts = shiftRepository.findAll();
		Iterable<Employee> employees = employeeRepository.findAll();
		for (Shift shift : shifts) {
			if (shift.getAssignee() == null) {
				for (Employee employee : employees) {
					if (!shiftRepository.findByDayAndAssignee_Id(shift.getDay(), employee.getId()).isPresent()) {
						List<Shift> shiftsAssignedToEmployee = shiftRepository.findByAssignee_Id(employee.getId());
						int totalWorkedHours = 0;
						if (shiftsAssignedToEmployee != null) {
							for (Shift shiftAssignedToEmployee : shiftsAssignedToEmployee) {
								totalWorkedHours += shiftAssignedToEmployee.getHours();
							}
						}
						if (employee.getRoles().contains(shift.getRole())
								&& shift.getHours() <= (employee.getMaxWeeklyHours() - totalWorkedHours)
									&& !employee.getUnavailableDays().contains(shift.getDay())) {
							shift.setAssignee(employee);
							break;
						}
					}
				}
				shiftRepository.save(shift);
			}
		}
		return shiftRepository.findAll();
	}

}
