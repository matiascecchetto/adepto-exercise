package au.com.adepto.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Role;
import au.com.adepto.demo.repository.RoleRepository;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Optional<Role> findById(long id) throws ResourceNotFoundException {
		if (!roleRepository.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Role with id #" + id + " doesn't exist.");
		}
		return roleRepository.findById(id);
	}

	@Override
	public Optional<Role> findByDescription(String description) throws ResourceNotFoundException {
		if (!roleRepository.findByDescription(description).isPresent()) {
			throw new ResourceNotFoundException("Role " + description + " doesn't exist.");
		}
		return roleRepository.findByDescription(description);
	}

	@Override
	public Iterable<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public void create(Role role) throws ResourceAlreadyExistException {
		if (roleRepository.findByDescription(role.getDescription()).isPresent()) {
			throw new ResourceAlreadyExistException("Role " + role.getDescription() + " already exists.");
		}
		roleRepository.save(role);
	}

	@Override
	public void update(Role role) throws ResourceNotFoundException {
		if (!roleRepository.findById(role.getId()).isPresent()) {
			throw new ResourceNotFoundException("Role " + role.getDescription() + " doesn't exist.");
		}
		roleRepository.save(role);
	}

	@Override
	public void delete(long id) throws ResourceNotFoundException {
		if (!roleRepository.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Role with id #" + id + " doesn't exist.");
		}
		roleRepository.deleteById(id);
	}

}