package com.pecenio.appoms.model;
import lombok.Data;
import java.util.Date;

@Data
public class Doctor{
	private int id;
	private String name;
	private Date lastUpdated;
	private Date created;
	@Override
	public String toString(){
		return name;
	}
}
