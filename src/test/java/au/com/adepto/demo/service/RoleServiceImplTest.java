package au.com.adepto.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

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
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Role;
import au.com.adepto.demo.repository.RoleRepository;

@RunWith(SpringRunner.class)
public class RoleServiceImplTest {

	@TestConfiguration
	static class RoleServiceImplTestContextConfiguration {
		@Bean
		public RoleService roleService() {
			return new RoleServiceImpl();
		}

	}

	@Autowired
	private RoleService roleService;

	@MockBean
	private RoleRepository roleRepository;

	@Before
	public void setUp() throws ResourceAlreadyExistException {

		Optional<Role> role1 = Optional.of(new Role(1L, "Chef"));
		Optional<Role> role2 = Optional.of(new Role(2L, "Cook"));
		Optional<Role> role3 = Optional.of(new Role(3L, "Baristo"));

		List<Role> roles = new ArrayList<Role>();

		roles.add(role1.get());
		roles.add(role2.get());
		roles.add(role3.get());

		Mockito.when(roleRepository.findById(1L)).thenReturn(role1);

		Mockito.when(roleRepository.findByDescription("Chef")).thenReturn(role1);

		Mockito.when(roleRepository.findAll()).thenReturn((Iterable<Role>) roles);

		Mockito.doNothing().when(roleRepository).deleteById(1L);

	}

	@Test
	public void whenFindRoleById_withValidId_thenShouldBeFound() {
		// given
		Long id = 1L;

		// when
		Optional<Role> foundRole = roleService.findById(id);

		// then
		assertThat(foundRole.get()).isNotNull();
		assertThat(foundRole.get().getId()).isEqualTo(id);
		assertThat(foundRole.get().getDescription()).isEqualTo("Chef");
	}

	@Test
	public void whenFindRoleByDescription_withValidDescription_thenShouldBeFound() {
		// given
		String description = "Chef";

		// when
		Optional<Role> foundRole = roleService.findByDescription(description);

		// then
		assertThat(foundRole.get().getDescription()).isEqualTo(description);
		assertThat(foundRole.get().getId()).isEqualTo(1L);
	}

	@Test
	public void whenFindAllRoles_thenAllRolesShouldBeFound() {
		// given

		// when
		List<Role> found = (List<Role>) roleService.findAll();

		// then
		assertThat(found).isNotEmpty();
		assertThat(found.size()).isEqualTo(3);
	}

	@Test
	public void whenCreatingNewRole_thenRoleShouldBeCreated() throws ResourceAlreadyExistException {
		// given
		Mockito.when(roleRepository.findByDescription("Baristo")).thenReturn(Optional.empty());
		Mockito.when(roleRepository.save(any(Role.class))).thenReturn(new Role(3L, "Baristo"));
		Role toBeRole = new Role(3L, "Baristo");

		// when
		Role createdRole = roleService.create(toBeRole);

		// then
		assertThat(createdRole).isNotNull();
		assertThat(createdRole.getId()).isEqualTo(toBeRole.getId());
		assertThat(createdRole.getDescription()).isEqualTo(toBeRole.getDescription());
	}

	@Test(expected = ResourceAlreadyExistException.class)
	public void whenCreatingNewRole_withExistingDescription_thenShouldThrowException()
			throws ResourceAlreadyExistException {
		// given
		Role role = new Role(1L, "Chef");

		// when
		roleService.create(role);

		// then
		// exception is thrown

	}

	@Test
	public void whenUpdatingARole_thenRoleShouldBeUpdated() throws ResourceNotFoundException {
		// given
		Mockito.when(roleRepository.save(any(Role.class))).thenReturn(new Role(1L, "Bar attendant"));
		Role toBeUpRole = new Role(1L, "Bar attendant");

		// when
		Role updatedRole = roleService.update(toBeUpRole);

		// then
		assertThat(updatedRole).isNotNull();
		assertThat(updatedRole.getId()).isEqualTo(toBeUpRole.getId());
		assertThat(updatedRole.getDescription()).isEqualTo(toBeUpRole.getDescription());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void whenUpdatingANonExistingRole_thenShouldThrowException() {
		// given
		Role role = new Role(9999L, "Bar attendant");

		// when
		roleService.update(role);

		// then
		// exception is thrown
	}

	@Test(expected = ResourceNotFoundException.class)
	public void whenDeletingARole_thenRoleShouldBeDeleted() {
		// given
		Role role = new Role(2L, "Cook");
		Mockito.when(roleRepository.findById(2L)).thenReturn(Optional.of(role));
		Mockito.doNothing().when(roleRepository).deleteById(2L);
		Mockito.when(roleRepository.findByDescription("Cook")).thenReturn(Optional.empty());

		// when
		roleService.delete(role.getId());

		// then
		roleService.findByDescription(role.getDescription());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void whenDeletingANonExistingRole_thenShouldThrowException() {
		// given
		Role role = new Role(9999L, "Bar attendant");

		// when
		roleService.delete(role.getId());

		// then
		// exception is thrown

	}

	// @Test(expected = DataIntegrityViolationException.class)
	// public void
	// whenDeletingAnExistingRole_withFKReferences_thenShouldThrowException() {
	// // given
	// Role role = new Role("Chef");
	//
	// // when
	// roleService.delete(roleService.findByDescription(role.getDescription()).get().getId());
	//
	// // then
	// }

}