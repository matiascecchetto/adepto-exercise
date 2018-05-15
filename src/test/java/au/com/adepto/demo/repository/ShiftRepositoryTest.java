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
import au.com.adepto.demo.model.Shift;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ShiftRepositoryTest {

	@Autowired
	// Alternative to the standard JPA EntityManager that provides methods commonly
	// used when writing tests
	private TestEntityManager entityManager;

	@Autowired
	private ShiftRepository shiftRepository;

	@Test
	public void whenFindByAssigneeId_thenReturnShift() {
		// given
		Role role1 = new Role(4L, "Manager");
		Role role2 = new Role(1L, "Chef");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);

		List<String> unavailableDays = new ArrayList<String>();
		unavailableDays.add("MON");
		Employee assignee = new Employee(30, roles, unavailableDays);

		Shift shift = new Shift("TUE", 7, role1, assignee);
		entityManager.persist(assignee);
		entityManager.persist(shift);
		entityManager.flush();

		// when
		List<Shift> found = shiftRepository.findByAssignee_Id(assignee.getId());

		// then
		assertThat(!found.isEmpty());
		assertThat(found.get(0).getId()).isEqualTo(shift.getId());
		assertThat(found.get(0).getDay()).isEqualTo(shift.getDay());
		assertThat(found.get(0).getHours()).isEqualTo(shift.getHours());
		assertThat(found.get(0).getRole()).isEqualTo(shift.getRole());
		assertThat(found.get(0).getAssignee()).isEqualTo(shift.getAssignee());

	}
	
	@Test
	public void whenFindByDayAndAssignee_Id_thenReturnShift() {
		// given
		Role role1 = new Role(4L, "Manager");
		Role role2 = new Role(1L, "Chef");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);

		List<String> unavailableDays = new ArrayList<String>();
		unavailableDays.add("MON");
		Employee assignee = new Employee(30, roles, unavailableDays);

		Shift shift = new Shift("TUE", 7, role1, assignee);
		entityManager.persist(assignee);
		entityManager.persist(shift);
		entityManager.flush();

		// when
		Optional<Shift> found = shiftRepository.findByDayAndAssignee_Id("TUE", assignee.getId());

		// then
		assertThat(found.isPresent());
		assertThat(found.get().getId()).isEqualTo(shift.getId());
		assertThat(found.get().getDay()).isEqualTo(shift.getDay());
		assertThat(found.get().getHours()).isEqualTo(shift.getHours());
		assertThat(found.get().getRole()).isEqualTo(shift.getRole());
		assertThat(found.get().getAssignee()).isEqualTo(shift.getAssignee());

	}

}