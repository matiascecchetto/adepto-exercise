package au.com.adepto.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "shift", path = "shift")
public interface ShiftRepository extends CrudRepository<Shift, Long> {

  Shift findById(@Param("id") Long id);
  Shift findByDayAndAssignee_Id(@Param("day") String day, @Param("assignee") Long id);
  List<Shift> findByAssignee_Id(@Param("assignee") Long id);

}
