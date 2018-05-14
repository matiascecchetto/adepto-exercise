package au.com.adepto.demo.service;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Shift;

public interface IShiftService {

	Iterable<Shift> findAll();

	void create(Shift shift) throws ResourceAlreadyExistException;

	Iterable<Shift> allocateStaff();

}