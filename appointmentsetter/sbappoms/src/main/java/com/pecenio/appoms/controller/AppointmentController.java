package com.pecenio.appoms.controller;
import com.pecenio.appoms.model.Appointment;
import com.pecenio.appoms.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class AppointmentController {
	Logger logger = LoggerFactory.getLogger( AppointmentController.class);
	@Autowired
	private AppointmentService appointmentService;
	@GetMapping("/api/appointment")
	public ResponseEntity<?> listAppointment()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Appointment[] appointment = appointmentService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(appointment);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/appointment")
	public ResponseEntity<?> add(@RequestBody Appointment appointment){
		logger.info("Input >> " + appointment.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Appointment newAppointment = appointmentService.create(appointment);
			logger.info("created appointment >> " + newAppointment.toString() );
			response = ResponseEntity.ok(newAppointment);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve appointment with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/appointment")
	public ResponseEntity<?> update(@RequestBody Appointment appointment){
		logger.info("Update Input >> appointment.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Appointment newAppointment = appointmentService.update(appointment);
			response = ResponseEntity.ok(appointment);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve appointment with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/appointment/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input appointment id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Appointment appointment = appointmentService.get(id);
			response = ResponseEntity.ok(appointment);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/appointment/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			appointmentService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
