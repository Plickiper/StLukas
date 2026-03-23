package com.pecenio.patims.model;

import lombok.Data;
import java.util.Date;

@Data
public class Patient {
	private int id;
	private Long userId;
	private String username;
	private String password;
	private String role;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String dateOfBirth;
	private int age;
	private String address;
	private String emergencyContact;
	private Date lastUpdated;
	private Date created;
}
