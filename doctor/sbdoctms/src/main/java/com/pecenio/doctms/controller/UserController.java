package com.pecenio.doctms.controller;

import com.pecenio.doctms.model.User;
import com.pecenio.doctms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@GetMapping("/api/user")
	public ResponseEntity<?> listUser() {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			User[] user = userService.getAll();
			response = ResponseEntity.ok().headers(headers).body(user);
		} catch (Exception ex) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@PostMapping("/api/user")
	public ResponseEntity<?> add(@RequestBody User user) {
		logger.info("Input >> " + user.toString());
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			User newUser = userService.create(user);
			logger.info("created user >> " + newUser.toString());
			response = ResponseEntity.ok(newUser);
		} catch (Exception ex) {
			logger.error("Failed to retrieve user with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@PutMapping("/api/user")
	public ResponseEntity<?> update(@RequestBody User user) {
		logger.info("Update Input >> user.toString() ");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			User newUser = userService.update(user);
			if (newUser == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User record does not exist");
			}
			response = ResponseEntity.ok(newUser);
		} catch (Exception ex) {
			logger.error("Failed to retrieve user with id : {}", ex.getMessage(), ex);
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@PostMapping("api/user/login")
	public ResponseEntity<?> login(@RequestBody User credentials) {
		logger.info("Login attempt for user: {}", credentials.getUsername());
		try {
			User user = userService.login(credentials.getUsername(), credentials.getPassword());
			if (user != null) {
				logger.info("Login successful for user: {}", user.getUsername());
				return ResponseEntity.ok(user);
			} else {
				logger.warn("Login failed for user: {}", credentials.getUsername());
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
			}
		} catch (Exception ex) {
			logger.error("Error during login for user: {}", credentials.getUsername(), ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}

	@GetMapping("api/user/{id}")
	public ResponseEntity<?> get(@PathVariable final Integer id) {
		logger.info("Input user id >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			User user = userService.get(id);
			response = ResponseEntity.ok(user);
		} catch (Exception ex) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}

	@DeleteMapping("api/user/{id}")
	public ResponseEntity<?> delete(@PathVariable final Integer id) {
		logger.info("Input >> " + Integer.toString(id));
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<?> response;
		try {
			userService.delete(id);
			response = ResponseEntity.ok(null);
		} catch (Exception ex) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return response;
	}
}
