package au.com.adepto.demo.service;

import java.util.Optional;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Role;

public interface IRoleService {

	Optional<Role> findById(long id) throws ResourceNotFoundException;

	Optional<Role> findByDescription(String description) throws ResourceNotFoundException;;

	Iterable<Role> findAll();

	void create(Role role) throws ResourceAlreadyExistException;

	void update(Role role) throws ResourceNotFoundException;

	void delete(long id) throws ResourceNotFoundException;

}