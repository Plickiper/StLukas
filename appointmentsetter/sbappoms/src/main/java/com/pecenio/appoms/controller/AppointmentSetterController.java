package com.pecenio.appoms.controller;
import com.pecenio.appoms.model.AppointmentSetter;
import com.pecenio.appoms.service.AppointmentSetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class AppointmentSetterController {
	Logger logger = LoggerFactory.getLogger( AppointmentSetterController.class);
	@Autowired
	private AppointmentSetterService appointmentSetterService;
	@GetMapping("/api/appointmentSetter")
	public ResponseEntity<?> listAppointmentSetter()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			AppointmentSetter[] appointmentSetter = appointmentSetterService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(appointmentSetter);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/appointmentSetter")
	public ResponseEntity<?> add(@RequestBody AppointmentSetter appointmentSetter){
		logger.info("Input >> " + appointmentSetter.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			AppointmentSetter newAppointmentSetter = appointmentSetterService.create(appointmentSetter);
			logger.info("created appointmentSetter >> " + newAppointmentSetter.toString() );
			response = ResponseEntity.ok(newAppointmentSetter);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve appointmentSetter with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/appointmentSetter")
	public ResponseEntity<?> update(@RequestBody AppointmentSetter appointmentSetter){
		logger.info("Update Input >> appointmentSetter.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			AppointmentSetter newAppointmentSetter = appointmentSetterService.update(appointmentSetter);
			response = ResponseEntity.ok(appointmentSetter);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve appointmentSetter with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/appointmentSetter/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input appointmentSetter id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			AppointmentSetter appointmentSetter = appointmentSetterService.get(id);
			response = ResponseEntity.ok(appointmentSetter);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/appointmentSetter/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			appointmentSetterService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
