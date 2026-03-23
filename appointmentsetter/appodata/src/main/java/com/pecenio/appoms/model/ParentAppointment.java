package com.pecenio.appoms.model;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
public class ParentAppointment{
	private int id;
    private Long patientId;
    private Long doctorId;
    private LocalDate preferredDate;
    private String preferredTimeRange;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    private String reason;
    private String status;
	private Date lastUpdated;
	private Date created;
}
