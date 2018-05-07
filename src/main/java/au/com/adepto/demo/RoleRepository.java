package au.com.adepto.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "role", path = "role")
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByDescription(@Param("description") String description);
  Role findById(@Param("id") Long id);

}
