package au.com.adepto.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.adepto.demo.model.Employee;
import au.com.adepto.demo.model.Role;
import au.com.adepto.demo.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private EmployeeService employeeService;

	@Test
	public void givenValidEmployees_whenGET_willReturnJsonArrayOfEmployees() throws Exception {

		Role role1 = new Role(1L, "Chef");
		Role role2 = new Role(2L, "Cook");
		Role role3 = new Role(4L, "Manager");

		List<Role> roles1 = new ArrayList<Role>();
		roles1.add(role1);
		roles1.add(role2);

		List<Role> roles2 = new ArrayList<Role>();
		roles2.add(role2);
		roles2.add(role3);

		List<Role> roles3 = new ArrayList<Role>();
		roles2.add(role3);

		List<String> unavailableDays1 = new ArrayList<String>();
		unavailableDays1.add("MON");

		List<String> unavailableDays2 = new ArrayList<String>();
		unavailableDays2.add("MON");
		unavailableDays2.add("THU");

		List<String> unavailableDays3 = new ArrayList<String>();
		unavailableDays1.add("MON");
		unavailableDays1.add("SAT");
		unavailableDays1.add("SUN");

		Optional<Employee> employee1 = Optional.of(new Employee(30, roles1, unavailableDays1));
		Optional<Employee> employee2 = Optional.of(new Employee(35, roles2, unavailableDays2));
		Optional<Employee> employee3 = Optional.of(new Employee(20, roles3, unavailableDays3));

		List<Employee> employees = new ArrayList<Employee>();

		employees.add(employee1.get());
		employees.add(employee2.get());
		employees.add(employee3.get());

		given(employeeService.findAll()).willReturn(employees);

		mvc.perform(get("/api/staff").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[0].id", is((int) employee1.get().getId())))
				.andExpect(jsonPath("$[0].maxWeeklyHours", is((int) employee1.get().getMaxWeeklyHours())));

		verify(employeeService, VerificationModeFactory.times(1)).findAll();
		verifyNoMoreInteractions(employeeService);

		reset(employeeService);

	}

	@Test
	public void givenAValidEmployee_whenPOST_willCreateEmployee() throws Exception {

		Role role1 = new Role(4L, "Manager");
		Role role2 = new Role(1L, "Chef");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);

		List<String> unavailableDays = new ArrayList<String>();
		unavailableDays.add("MON");
		Employee employee = new Employee(30, roles, unavailableDays);

		given(employeeService.create(any(Employee.class))).willReturn(employee);

		mvc.perform(post("/api/staff").contentType(MediaType.APPLICATION_JSON).content(asJsonString(employee)))
				.andExpect(status().isCreated());

		verify(employeeService, VerificationModeFactory.times(1)).create(any(Employee.class));

		reset(employeeService);

	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}