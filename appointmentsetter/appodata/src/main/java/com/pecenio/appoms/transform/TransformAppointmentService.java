package com.pecenio.appoms.transform;
import com.pecenio.appoms.entity.AppointmentData;
import com.pecenio.appoms.model.Appointment;
public interface TransformAppointmentService {
	AppointmentData transform(Appointment appointment);
	Appointment transform(AppointmentData appointmentData);
}
