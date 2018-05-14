package au.com.adepto.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import au.com.adepto.demo.exception.ResourceAlreadyExistException;
import au.com.adepto.demo.model.Shift;
import au.com.adepto.demo.service.IShiftService;

@Controller
@RequestMapping(path = "/api/shift")
public class ShiftController {

	@Autowired
	private IShiftService shiftService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Shift>> getAll() {
		Iterable<Shift> shifts = shiftService.findAll();
		return ResponseEntity.ok(shifts);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Shift shift) throws URISyntaxException {
		try {
			shiftService.create(shift);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(shift.getId()).toUri();
			return ResponseEntity.created(location).build();
			// Throws an exception when the shift already exists
		} catch (ResourceAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			// Throws an exception when the shift is null or some of the fields marked as
			// NOT NULL in the database is null
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	@RequestMapping(path = "/allocate", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Shift>> allocateStaff() {
		Iterable<Shift> shifts = shiftService.allocateStaff();
		return ResponseEntity.ok(shifts);
	}

}
