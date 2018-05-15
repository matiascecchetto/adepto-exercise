package au.com.adepto.demo.service;

import java.util.Optional;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Shift;

public interface ShiftService {

	Iterable<Shift> findAll();

	void create(Shift shift) throws ResourceAlreadyExistException;

	Iterable<Shift> allocateStaff();

	Optional<Shift> findById(long id) throws ResourceNotFoundException;

}