package com.pecenio.appoms.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
@Table(name = "Appointment_data")
public class AppointmentData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date lastUpdated;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date created;
}
