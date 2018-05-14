package au.com.adepto.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import au.com.adepto.demo.model.Employee;

@RepositoryRestResource(collectionResourceRel = "staff", path = "staff")
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
	Optional<Employee> findById(Long id);
	
}
