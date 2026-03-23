package com.pecenio.appoms.model;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
public class AppointmentSetter {
	private int id;
	private Long patientId;
	private String patientFirstName;
	private String patientLastName;
	private Long doctorId;
	private String doctorFirstName;
	private String doctorLastName;
	private String specialization;
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
