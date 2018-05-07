package au.com.adepto.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


@Controller
@RequestMapping(path="/shift")
public class ShiftController {

	@Autowired
	private ShiftRepository shiftRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	private static final List<String> DAYS = Arrays.asList("MON","TUE","WED","THU","FRI","SAT","SUN");

	//TODO: change mapping method to POST
	@GetMapping(path="/add")
	public @ResponseBody Shift addNewShift (@RequestParam(required=true) String day, @RequestParam(required=true) int hours, @RequestParam(required=true) String role) {
		Shift newShift = new Shift();
		Role tmpRole = roleRepository.findByDescription(role);
		if (DAYS.contains(day.toUpperCase())) {
			newShift.setDay(day.toUpperCase());
		}
		//TODO: add check for maximum & minumim amount of hours
		newShift.setHours(hours);
		if (tmpRole != null) {
			newShift.setRole(tmpRole);
		}
		shiftRepository.save(newShift);
		return newShift;
	}

	@GetMapping(path="/allocate")
	public @ResponseBody Iterable<Shift> allocateStaff () {
		Iterable<Shift> shifts = shiftRepository.findAll();
		Iterable<Employee> employees = employeeRepository.findAll();
		for (Shift shift : shifts) {
			if (shift.getAssignee() == null) {
				for (Employee employee : employees) {
					if ( shiftRepository.findByDayAndAssignee_Id(shift.getDay(), employee.getId()) == null) {
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
