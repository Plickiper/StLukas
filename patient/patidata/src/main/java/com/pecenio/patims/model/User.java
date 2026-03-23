package com.pecenio.patims.model;

import lombok.Data;
import java.util.Date;

@Data
public class User {
	private int id;
	private String username;
	private String password;
	private String role;
	private Date lastUpdated;
	private Date created;
}
