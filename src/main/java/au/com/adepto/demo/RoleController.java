package au.com.adepto.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/role")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;

  //TODO: change mapping method to POST
	@GetMapping(path="/add")
	public @ResponseBody Role addNewRole (@RequestParam String description) {
		Role role = new Role();
		try {
			if (roleRepository.findByDescription(description) != null) {
				throw new Exception("Role " + description + " already exists.");
	    }
			role.setDescription(description);
			roleRepository.save(role);
		} catch (Exception e) {
			System.out.println(e);
		}
		return role;
	}

	//TODO: change mapping method to PUT
	@GetMapping(path="/update")
	public @ResponseBody Role updateRole (@RequestParam(required=true) Long id, @RequestParam(required=true) String description) {
		Role role = roleRepository.findById(id);
		try {
			if (role == null) {
				throw new Exception("Role " + id + " does not exist.");
	    }
			if(description != null) {
				role.setDescription(description);
			}
			roleRepository.save(role);
		} catch (Exception e) {
			System.out.println(e);
		}
		return role;
	}

	//TODO: change mapping method to DELETE
	@GetMapping(path="/delete")
	public @ResponseBody Iterable<Role> deleteRole (@RequestParam String description) {
		Role role = new Role();
		try {
			if (roleRepository.findByDescription(description) == null) {
				throw new Exception("Role " + description + " does not exist.");
	    }
			role = roleRepository.findByDescription(description);
			roleRepository.delete(role);
			//TODO: check if it's better to return and empty json or HTTP status code
		} catch (Exception e) {
			System.out.println(e);
	  }
		return roleRepository.findAll();
	}

}
