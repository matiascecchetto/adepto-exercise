package au.com.adepto.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Employee;
import au.com.adepto.demo.model.Role;
import au.com.adepto.demo.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {
		@Bean
		public EmployeeService employeeService() {
			return new EmployeeServiceImpl();
		}

	}

	@Autowired
	private EmployeeService employeeService;

	@MockBean
	private EmployeeRepository employeeRepository;

	@Before
	public void setUp() throws ResourceAlreadyExistException {
		
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

		Mockito.when(employeeRepository.findById(0L)).thenReturn(employee1);

		Mockito.when(employeeRepository.findAll()).thenReturn( (Iterable<Employee>) employees);


	}

	@Test
	public void whenFindEmployeeById_withValidId_thenShouldBeFound() {
		// given
		Long id = 0L;

		// when
		Optional<Employee> foundEmployee = employeeService.findById(id);

		// then
		assertThat(foundEmployee.get()).isNotNull();
		assertThat(foundEmployee.get().getId()).isEqualTo(id);

	}

	@Test
	public void whenFindAllEmployees_thenAllEmployeesShouldBeFound() {
		// given

		// when
		List<Employee>  found = (List<Employee>) employeeService.findAll();

		// then
		assertThat(found).isNotEmpty();
		assertThat(found.size()).isEqualTo(3);
	}

	@Test
	public void whenCreatingNewEmployee_thenEmployeeShouldBeCreated() throws ResourceAlreadyExistException {
		// given
		Role role1 = new Role(4L, "Manager");
		Role role2 = new Role(1L, "Chef");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);

		List<String> unavailableDays = new ArrayList<String>();
		unavailableDays.add("MON");
		Employee employee = new Employee(30, roles, unavailableDays);
		
		Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
		Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee(30, roles, unavailableDays));

		// when
		
		Employee createdEmployee = employeeService.create(employee);

		// then
		assertThat(createdEmployee).isNotNull();
		assertThat(createdEmployee.getId()).isEqualTo(employee.getId());
		assertThat(createdEmployee.getMaxWeeklyHours()).isEqualTo(employee.getMaxWeeklyHours());
		assertThat(createdEmployee.getRoles()).isEqualTo(employee.getRoles());
		assertThat(createdEmployee.getUnavailableDays()).isEqualTo(employee.getUnavailableDays());
		
	}

}
