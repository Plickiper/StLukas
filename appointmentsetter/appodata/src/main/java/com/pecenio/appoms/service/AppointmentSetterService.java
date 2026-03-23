package com.pecenio.appoms.service;
import com.pecenio.appoms.model.AppointmentSetter;
public interface AppointmentSetterService {
	AppointmentSetter[] getAll() throws Exception;
	AppointmentSetter get(Integer id) throws Exception;
	AppointmentSetter create(AppointmentSetter appointmentSetter) throws Exception;
	AppointmentSetter update(AppointmentSetter appointmentSetter) throws Exception;
	void delete(Integer id) throws Exception;
}
