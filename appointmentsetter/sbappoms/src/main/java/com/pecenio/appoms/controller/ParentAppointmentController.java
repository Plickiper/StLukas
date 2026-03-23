package com.pecenio.appoms.controller;
import com.pecenio.appoms.model.ParentAppointment;
import com.pecenio.appoms.service.ParentAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class ParentAppointmentController {
	Logger logger = LoggerFactory.getLogger( ParentAppointmentController.class);
	@Autowired
	private ParentAppointmentService parentAppointmentService;
	@GetMapping("/api/parentAppointment")
	public ResponseEntity<?> listParentAppointment()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			ParentAppointment[] parentAppointment = parentAppointmentService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(parentAppointment);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/parentAppointment")
	public ResponseEntity<?> add(@RequestBody ParentAppointment parentAppointment){
		logger.info("Input >> " + parentAppointment.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			ParentAppointment newParentAppointment = parentAppointmentService.create(parentAppointment);
			logger.info("created parentAppointment >> " + newParentAppointment.toString() );
			response = ResponseEntity.ok(newParentAppointment);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve parentAppointment with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/parentAppointment")
	public ResponseEntity<?> update(@RequestBody ParentAppointment parentAppointment){
		logger.info("Update Input >> parentAppointment.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			ParentAppointment newParentAppointment = parentAppointmentService.update(parentAppointment);
			response = ResponseEntity.ok(parentAppointment);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve parentAppointment with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/parentAppointment/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input parentAppointment id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			ParentAppointment parentAppointment = parentAppointmentService.get(id);
			response = ResponseEntity.ok(parentAppointment);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/parentAppointment/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			parentAppointmentService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
