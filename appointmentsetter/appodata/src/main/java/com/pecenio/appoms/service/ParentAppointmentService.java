package com.pecenio.appoms.service;
import com.pecenio.appoms.model.ParentAppointment;
public interface ParentAppointmentService {
	ParentAppointment[] getAll() throws Exception;
	ParentAppointment get(Integer id) throws Exception;
	ParentAppointment create(ParentAppointment parentAppointment) throws Exception;
	ParentAppointment update(ParentAppointment parentAppointment) throws Exception;
	void delete(Integer id) throws Exception;
}
