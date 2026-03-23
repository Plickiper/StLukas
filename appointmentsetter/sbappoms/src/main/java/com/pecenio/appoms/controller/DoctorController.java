package com.pecenio.appoms.controller;
import com.pecenio.appoms.model.Doctor;
import com.pecenio.appoms.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class DoctorController {
	Logger logger = LoggerFactory.getLogger( DoctorController.class);
	@Autowired
	private DoctorService doctorService;
	@GetMapping("/api/doctor")
	public ResponseEntity<?> listDoctor()
{
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Doctor[] doctor = doctorService.getAll();
			response =  ResponseEntity.ok().headers(headers).body(doctor);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PutMapping("api/doctor")
	public ResponseEntity<?> add(@RequestBody Doctor doctor){
		logger.info("Input >> " + doctor.toString() );
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Doctor newDoctor = doctorService.create(doctor);
			logger.info("created doctor >> " + newDoctor.toString() );
			response = ResponseEntity.ok(newDoctor);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve doctor with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@PostMapping("api/doctor")
	public ResponseEntity<?> update(@RequestBody Doctor doctor){
		logger.info("Update Input >> doctor.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Doctor newDoctor = doctorService.update(doctor);
			response = ResponseEntity.ok(doctor);
		}
		catch( Exception ex)
		{
			logger.error("Failed to retrieve doctor with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/doctor/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id){
		logger.info("Input doctor id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Doctor doctor = doctorService.get(id);
			response = ResponseEntity.ok(doctor);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
	@DeleteMapping("api/doctor/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id){
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			doctorService.delete(id);
			response = ResponseEntity.ok(null);
		}
		catch( Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
