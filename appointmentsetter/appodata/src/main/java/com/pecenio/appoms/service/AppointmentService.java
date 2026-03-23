package com.pecenio.appoms.service;
import com.pecenio.appoms.model.Appointment;
public interface AppointmentService {
	Appointment[] getAll() throws Exception;
	Appointment get(Integer id) throws Exception;
	Appointment create(Appointment appointment) throws Exception;
	Appointment update(Appointment appointment) throws Exception;
	void delete(Integer id) throws Exception;
}
