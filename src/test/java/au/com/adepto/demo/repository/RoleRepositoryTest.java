package au.com.adepto.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import au.com.adepto.demo.model.Role;


@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

	@Autowired
	// Alternative to the standard JPA EntityManager that provides methods commonly
	// used when writing tests
	private TestEntityManager entityManager;

	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void whenFindRoleByDescription_thenShouldBeFound() {
		// given
		Role role = new Role("Bar attendant");
		entityManager.persist(role);
		entityManager.flush();

		// when
		Optional<Role> found = roleRepository.findByDescription(role.getDescription());

		// then
		assertThat(found.isPresent());
		assertThat(found.get().getId()).isEqualTo(role.getId());
		assertThat(found.get().getDescription()).isEqualTo(role.getDescription());

	}

}