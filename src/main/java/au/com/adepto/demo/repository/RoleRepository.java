package au.com.adepto.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import au.com.adepto.demo.model.Role;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RoleRepository extends CrudRepository<Role, Long> {

	Optional<Role> findByDescription(String description);

	void deleteById(Long id);

}