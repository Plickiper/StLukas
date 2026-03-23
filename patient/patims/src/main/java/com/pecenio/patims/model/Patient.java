package com.pecenio.patims.model;
import lombok.Data;
import java.util.Date;

@Data
public class Patient{
	private int id;
	private Long userId;
	private String username;
	private String password;
	private String role;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Date lastUpdated;
	private Date created;
	@Override
	public String toString(){
		return firstName + " " + lastName;
	}
}
