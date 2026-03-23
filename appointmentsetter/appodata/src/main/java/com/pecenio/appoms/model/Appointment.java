package com.pecenio.appoms.model;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
public class Appointment{
	private int id;
	private Long userId;
	private String username;
	private String password;
	private String role;
	private Long patientId;
	private String patientFirstName;
	private String patientLastName;
	private String patientPhoneNumber;
	private Long doctorId;
	private String doctorFirstName;
	private String doctorLastName;
	private String specialization;
	private String availabilityStatus;
	private Long appointmentId;
	private LocalDate preferredDate;
	private String preferredTimeRange;
	private LocalDate scheduledDate;
	private LocalTime scheduledTime;
	private String reason;
	private String status;
	private Long parentAppointmentId;
	private Date lastUpdated;
	private Date created;
}
