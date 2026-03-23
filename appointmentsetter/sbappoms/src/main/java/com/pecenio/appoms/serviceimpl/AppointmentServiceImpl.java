package com.pecenio.appoms.serviceimpl;

import com.pecenio.appoms.entity.AppointmentData;
import com.pecenio.appoms.model.Appointment;
import com.pecenio.appoms.model.AppointmentEvent;
import com.pecenio.appoms.repository.AppointmentDataRepository;
import com.pecenio.appoms.service.AppointmentService;
import com.pecenio.appoms.transform.TransformAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	@Autowired
	private AppointmentDataRepository appointmentDataRepository;

	@Autowired
	private TransformAppointmentService transformAppointmentService;

	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Override
	public Appointment[] getAll() {
		List<AppointmentData> appointmentsData = new ArrayList<>();
		List<Appointment> appointments = new ArrayList<>();
		appointmentDataRepository.findAll().forEach(appointmentsData::add);
		for (AppointmentData data : appointmentsData) {
			appointments.add(transformAppointmentService.transform(data));
		}
		return appointments.toArray(new Appointment[0]);
	}

	@Override
	public Appointment create(Appointment appointment) {
		logger.info("Creating appointment request for patient: {}", appointment.getPatientId());

		appointment.setStatus("REQUESTED");
		String eventType = "AppointmentRequestedEvent";

		if (appointment.getParentAppointmentId() != null) {
			appointment.setStatus("FOLLOW_UP_PENDING");
			eventType = "FollowUpCreatedEvent";
		}

		AppointmentData appointmentData = transformAppointmentService.transform(appointment);
		appointmentData = appointmentDataRepository.save(appointmentData);

		Appointment saved = transformAppointmentService.transform(appointmentData);

		// Fire Kafka Event
		publishEvent(saved, eventType, "New appointment initiated");

		return saved;
	}

	@Override
	public Appointment update(Appointment appointment) {
		logger.info("Updating appointment with id: {}", appointment.getId());
		Optional<AppointmentData> optional = appointmentDataRepository.findById(appointment.getId());

		if (optional.isPresent()) {
			AppointmentData existing = optional.get();
			String oldStatus = existing.getStatus();
			String newStatus = appointment.getStatus();

			if (!oldStatus.equals(newStatus)) {
				validateTransition(oldStatus, newStatus, appointment.getRole());
			}

			AppointmentData toUpdate = transformAppointmentService.transform(appointment);
			toUpdate.setCreated(existing.getCreated());
			toUpdate = appointmentDataRepository.save(toUpdate);

			Appointment updated = transformAppointmentService.transform(toUpdate);

			// Fire Kafka events based on status change
			if (!oldStatus.equals(newStatus)) {
				String eventType = mapStatusToEvent(newStatus);
				publishEvent(updated, eventType, "Status changed from " + oldStatus + " to " + newStatus);
			}

			return updated;
		} else {
			logger.error("Appointment record with id: {} does not exist", appointment.getId());
			return null;
		}
	}

	private void validateTransition(String oldStatus, String newStatus, String role) {
		if ("REQUESTED".equals(oldStatus) && "PENDING_DOCTOR_CONFIRMATION".equals(newStatus)) {
			if (!"ADMIN".equalsIgnoreCase(role)) {
				throw new RuntimeException("Only Admin can assign doctors");
			}
		}
		if ("PENDING_DOCTOR_CONFIRMATION".equals(oldStatus)) {
			if (("CONFIRMED".equals(newStatus) || "REJECTED".equals(newStatus)) && !"DOCTOR".equalsIgnoreCase(role)) {
				throw new RuntimeException("Only Doctor can confirm/reject");
			}
		}
		if ("CONFIRMED".equals(oldStatus) && "COMPLETED".equals(newStatus) && !"DOCTOR".equalsIgnoreCase(role)) {
			throw new RuntimeException("Only Doctor can complete");
		}
	}

	private String mapStatusToEvent(String status) {
		switch (status) {
			case "PENDING_DOCTOR_CONFIRMATION":
				return "AppointmentAssignedEvent";
			case "CONFIRMED":
				return "AppointmentConfirmedEvent";
			case "REJECTED":
				return "AppointmentRejectedEvent";
			case "COMPLETED":
				return "AppointmentCompletedEvent";
			default:
				return "AppointmentUpdatedEvent";
		}
	}

	private void publishEvent(Appointment app, String eventType, String message) {
		AppointmentEvent event = AppointmentEvent.builder()
				.eventType(eventType)
				.appointmentId(app.getId())
				.patientId(app.getPatientId())
				.doctorId(app.getDoctorId())
				.status(app.getStatus())
				.message(message)
				.timestamp(LocalDateTime.now())
				.build();
		// kafkaProducerService.sendEvent(event);
		kafkaProducerService.sendEvent(event);
	}

	@Override
	public Appointment get(Integer id) {
		return appointmentDataRepository.findById(id)
				.map(transformAppointmentService::transform)
				.orElse(null);
	}

	@Override
	public void delete(Integer id) {
		appointmentDataRepository.deleteById(id);
	}
}
