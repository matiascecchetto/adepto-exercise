package au.com.adepto.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import au.com.adepto.demo.model.Shift;

@RepositoryRestResource(collectionResourceRel = "shift", path = "shift")
public interface ShiftRepository extends CrudRepository<Shift, Long> {

	Shift findByDayAndAssignee_Id(String day, Long id);

	List<Shift> findByAssignee_Id(Long id);

}
