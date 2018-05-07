package au.com.adepto.demo;

// import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "staff", path = "staff")
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	Employee findById(@Param("id") Long id);

}
