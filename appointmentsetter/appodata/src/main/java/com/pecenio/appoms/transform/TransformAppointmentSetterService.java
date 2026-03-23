package com.pecenio.appoms.transform;
import com.pecenio.appoms.entity.AppointmentSetterData;
import com.pecenio.appoms.model.AppointmentSetter;
public interface TransformAppointmentSetterService {
	AppointmentSetterData transform(AppointmentSetter appointmentSetter);
	AppointmentSetter transform(AppointmentSetterData appointmentSetterData);
}
