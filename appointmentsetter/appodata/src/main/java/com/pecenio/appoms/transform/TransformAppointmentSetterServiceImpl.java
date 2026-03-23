package com.pecenio.appoms.transform;
import com.pecenio.appoms.entity.AppointmentSetterData;
import com.pecenio.appoms.model.AppointmentSetter;
import org.springframework.stereotype.Service;
@Service
public class TransformAppointmentSetterServiceImpl implements TransformAppointmentSetterService {
	@Override
	public AppointmentSetterData transform(AppointmentSetter appointmentSetter){
		AppointmentSetterData appointmentSetterData = new AppointmentSetterData();
		appointmentSetterData.setId(appointmentSetter.getId());
		appointmentSetterData.setPatientId(appointmentSetter.getPatientId());
		appointmentSetterData.setPatientFirstName(appointmentSetter.getPatientFirstName());
		appointmentSetterData.setPatientLastName(appointmentSetter.getPatientLastName());
		appointmentSetterData.setDoctorId(appointmentSetter.getDoctorId());
		appointmentSetterData.setDoctorFirstName(appointmentSetter.getDoctorFirstName());
		appointmentSetterData.setDoctorLastName(appointmentSetter.getDoctorLastName());
		appointmentSetterData.setSpecialization(appointmentSetter.getSpecialization());
		appointmentSetterData.setPreferredDate(appointmentSetter.getPreferredDate());
		appointmentSetterData.setPreferredTimeRange(appointmentSetter.getPreferredTimeRange());
		appointmentSetterData.setScheduledDate(appointmentSetter.getScheduledDate());
		appointmentSetterData.setScheduledTime(appointmentSetter.getScheduledTime());
		appointmentSetterData.setReason(appointmentSetter.getReason());
		appointmentSetterData.setStatus(appointmentSetter.getStatus());
		appointmentSetterData.setParentAppointmentId(appointmentSetter.getParentAppointmentId());
		return appointmentSetterData;
	}
	@Override

	public AppointmentSetter transform(AppointmentSetterData appointmentSetterData){;
		AppointmentSetter appointmentSetter = new AppointmentSetter();
		appointmentSetter.setId(appointmentSetterData.getId());
		appointmentSetter.setPatientId(appointmentSetterData.getPatientId());
		appointmentSetter.setPatientFirstName(appointmentSetterData.getPatientFirstName());
		appointmentSetter.setPatientLastName(appointmentSetterData.getPatientLastName());
		appointmentSetter.setDoctorId(appointmentSetterData.getDoctorId());
		appointmentSetter.setDoctorFirstName(appointmentSetterData.getDoctorFirstName());
		appointmentSetter.setDoctorLastName(appointmentSetterData.getDoctorLastName());
		appointmentSetter.setSpecialization(appointmentSetterData.getSpecialization());
		appointmentSetter.setPreferredDate(appointmentSetterData.getPreferredDate());
		appointmentSetter.setPreferredTimeRange(appointmentSetterData.getPreferredTimeRange());
		appointmentSetter.setScheduledDate(appointmentSetterData.getScheduledDate());
		appointmentSetter.setScheduledTime(appointmentSetterData.getScheduledTime());
		appointmentSetter.setReason(appointmentSetterData.getReason());
		appointmentSetter.setStatus(appointmentSetterData.getStatus());
		appointmentSetter.setParentAppointmentId(appointmentSetterData.getParentAppointmentId());
		appointmentSetter.setCreated(appointmentSetterData.getCreated());
		appointmentSetter.setLastUpdated(appointmentSetterData.getLastUpdated());
		return appointmentSetter;
	}
}
