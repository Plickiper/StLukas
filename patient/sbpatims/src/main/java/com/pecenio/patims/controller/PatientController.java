package com.pecenio.patims.controller;

import com.pecenio.patims.model.Patient;
import com.pecenio.patims.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatientController {
	Logger logger = LoggerFactory.getLogger(PatientController.class);
	@Autowired
	private PatientService patientService;

	@GetMapping("/api/patient")
	public ResponseEntity<?> listPatient() {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Patient[] patient = patientService.getAll();
			response = ResponseEntity.ok().headers(headers).body(patient);
		} catch (Exception ex) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@PutMapping("api/patient")
	public ResponseEntity<?> add(@RequestBody Patient patient) {
		logger.info("Input >> " + patient.toString());
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Patient newPatient = patientService.create(patient);
			logger.info("created patient >> " + newPatient.toString());
			response = ResponseEntity.ok(newPatient);
		} catch (Exception ex) {
			logger.error("Failed to retrieve patient with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@PostMapping("api/patient")
	public ResponseEntity<?> update(@RequestBody Patient patient) {
		logger.info("Update Input >> patient.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Patient newPatient = patientService.update(patient);
			response = ResponseEntity.ok(patient);
		} catch (Exception ex) {
			logger.error("Failed to retrieve patient with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/patient/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id) {
		logger.info("Input patient id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			Patient patient = patientService.get(id);
			response = ResponseEntity.ok(patient);
		} catch (Exception ex) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@DeleteMapping("api/patient/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id) {
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			patientService.delete(id);
			response = ResponseEntity.ok(null);
		} catch (Exception ex) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@GetMapping("api/patient/user/{userId}")
	public ResponseEntity<?> getByUserId(@PathVariable final Long userId) {
		logger.info("Input userId >> " + Long.toString(userId));
		try {
			Patient patient = patientService.getByUserId(userId);
			if (patient == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found for userId: " + userId);
			}
			return ResponseEntity.ok(patient);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}
}
