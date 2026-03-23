package com.pecenio.appoms.transform;
import com.pecenio.appoms.entity.AppointmentData;
import com.pecenio.appoms.model.Appointment;
import org.springframework.stereotype.Service;
@Service
public class TransformAppointmentServiceImpl implements TransformAppointmentService {
	@Override
	public AppointmentData transform(Appointment appointment){
		AppointmentData appointmentData = new AppointmentData();
		appointmentData.setId(appointment.getId());
		appointmentData.setUserId(appointment.getUserId());
		appointmentData.setUsername(appointment.getUsername());
		appointmentData.setPassword(appointment.getPassword());
		appointmentData.setRole(appointment.getRole());
		appointmentData.setPatientId(appointment.getPatientId());
		appointmentData.setPatientFirstName(appointment.getPatientFirstName());
		appointmentData.setPatientLastName(appointment.getPatientLastName());
		appointmentData.setPatientPhoneNumber(appointment.getPatientPhoneNumber());
		appointmentData.setDoctorId(appointment.getDoctorId());
		appointmentData.setDoctorFirstName(appointment.getDoctorFirstName());
		appointmentData.setDoctorLastName(appointment.getDoctorLastName());
		appointmentData.setSpecialization(appointment.getSpecialization());
		appointmentData.setAvailabilityStatus(appointment.getAvailabilityStatus());
		appointmentData.setAppointmentId(appointment.getAppointmentId());
		appointmentData.setPreferredDate(appointment.getPreferredDate());
		appointmentData.setPreferredTimeRange(appointment.getPreferredTimeRange());
		appointmentData.setScheduledDate(appointment.getScheduledDate());
		appointmentData.setScheduledTime(appointment.getScheduledTime());
		appointmentData.setReason(appointment.getReason());
		appointmentData.setStatus(appointment.getStatus());
		appointmentData.setParentAppointmentId(appointment.getParentAppointmentId());
		return appointmentData;
	}
	@Override

	public Appointment transform(AppointmentData appointmentData){;
		Appointment appointment = new Appointment();
		appointment.setId(appointmentData.getId());
		appointment.setUserId(appointmentData.getUserId());
		appointment.setUsername(appointmentData.getUsername());
		appointment.setPassword(appointmentData.getPassword());
		appointment.setRole(appointmentData.getRole());
		appointment.setPatientId(appointmentData.getPatientId());
		appointment.setPatientFirstName(appointmentData.getPatientFirstName());
		appointment.setPatientLastName(appointmentData.getPatientLastName());
		appointment.setPatientPhoneNumber(appointmentData.getPatientPhoneNumber());
		appointment.setDoctorId(appointmentData.getDoctorId());
		appointment.setDoctorFirstName(appointmentData.getDoctorFirstName());
		appointment.setDoctorLastName(appointmentData.getDoctorLastName());
		appointment.setSpecialization(appointmentData.getSpecialization());
		appointment.setAvailabilityStatus(appointmentData.getAvailabilityStatus());
		appointment.setAppointmentId(appointmentData.getAppointmentId());
		appointment.setPreferredDate(appointmentData.getPreferredDate());
		appointment.setPreferredTimeRange(appointmentData.getPreferredTimeRange());
		appointment.setScheduledDate(appointmentData.getScheduledDate());
		appointment.setScheduledTime(appointmentData.getScheduledTime());
		appointment.setReason(appointmentData.getReason());
		appointment.setStatus(appointmentData.getStatus());
		appointment.setParentAppointmentId(appointmentData.getParentAppointmentId());
		appointment.setCreated(appointmentData.getCreated());
		appointment.setLastUpdated(appointmentData.getLastUpdated());
		return appointment;
	}
}
