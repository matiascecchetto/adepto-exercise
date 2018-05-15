package au.com.adepto.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Role;
import au.com.adepto.demo.service.RoleService;

@Controller
@RequestMapping(path = "/api/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<Role>> get(@PathVariable Long id) {
		try {
			Optional<Role> role = roleService.findById(id);
			return ResponseEntity.ok(role);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Role>> getAll() {
		Iterable<Role> roles = roleService.findAll();
		return ResponseEntity.ok(roles);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Role role) throws URISyntaxException {
		try {
			roleService.create(role);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(role.getId())
					.toUri();
			return ResponseEntity.created(location).build();
			// Throws an exception when the role already exists
		} catch (ResourceAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			// Throws an exception when the role is null or some of the fields marked as
			// NOT NULL in the database is null
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Role role) {
		try {
			roleService.update(role);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			// Throws an exception when some of the fields marked as NOT NULL in the
			// database is null
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			roleService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
