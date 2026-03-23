package com.pecenio.doctms.model;
import lombok.Data;
import java.util.Date;

@Data
public class Doctor{
	private int id;
	private Long userId;
	private String username;
	private String password;
	private String role;
	private String firstName;
	private String lastName;
	private String specialization;
	private String availabilityStatus;
	private Date lastUpdated;
	private Date created;
}
