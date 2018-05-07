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
@RequestMapping(path="/staff")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
  @Autowired
	private RoleRepository roleRepository;

	private static final List<String> DAYS = Arrays.asList("MON","TUE","WED","THU","FRI","SAT","SUN");

  //TODO: change mapping method to POST
	@GetMapping(path="/add")
	public @ResponseBody Employee addNewEmployee (@RequestParam(value="hours", required=true) int maxWeeklyHours, @RequestParam(value="role", required=true) List<String> roles, @RequestParam(value="unavailabledays", required=false) List<String> unavailableDays) {
		Employee employee = new Employee();
		List<Role> tmpRolesList = new ArrayList<Role>();
		List<String> tmpUnavailableDays = new ArrayList<String>();
		employee.setMaxWeeklyHours(maxWeeklyHours);
		for (String role : roles) {
			tmpRolesList.add(roleRepository.findByDescription(role));
		}
		if (!tmpRolesList.isEmpty()) {
			employee.setRoles(tmpRolesList);
		}
		if (unavailableDays != null) {
			for (String day : unavailableDays) {
				if (DAYS.contains(day.toUpperCase())) {
					if (!tmpUnavailableDays.contains(day.toUpperCase())) {
						tmpUnavailableDays.add(day.toUpperCase());
					}
				}
			}
			if (!tmpUnavailableDays.isEmpty()) {
				employee.setUnavailableDays(tmpUnavailableDays);
			}
		}
		employeeRepository.save(employee);
		return employee;
	}

}
