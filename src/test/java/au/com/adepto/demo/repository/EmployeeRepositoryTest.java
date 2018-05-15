package au.com.adepto.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import au.com.adepto.demo.model.Employee;
import au.com.adepto.demo.model.Role;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

	@Autowired
	// Alternative to the standard JPA EntityManager that provides methods commonly
	// used when writing tests
	private TestEntityManager entityManager;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void whenFindById_thenReturnEmployee() {
		// given
		Role role1 = new Role(4L, "Manager");
		Role role2 = new Role(1L, "Chef");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);

		List<String> unavailableDays = new ArrayList<String>();
		unavailableDays.add("MON");
		Employee employee = new Employee(30, roles, unavailableDays);
		entityManager.persist(employee);
		entityManager.flush();

		// when
		Optional<Employee> found = employeeRepository.findById(employee.getId());

		// then
		assertThat(found.isPresent());
		assertThat(found.get().getId()).isEqualTo(employee.getId());
		assertThat(found.get().getMaxWeeklyHours()).isEqualTo(employee.getMaxWeeklyHours());
		assertThat(found.get().getRoles()).isEqualTo(employee.getRoles());
		assertThat(found.get().getUnavailableDays()).isEqualTo(employee.getUnavailableDays());

	}

}