package com.pecenio.doctms.serviceimpl;

import com.pecenio.doctms.entity.DoctorData;
import com.pecenio.doctms.model.Doctor;
import com.pecenio.doctms.model.DoctorEvent;
import com.pecenio.doctms.model.User;
import com.pecenio.doctms.repository.DoctorDataRepository;
import com.pecenio.doctms.service.DoctorService;
import com.pecenio.doctms.service.UserService;
import com.pecenio.doctms.transform.TransformDoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {
	private static final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);

	@Autowired
	private DoctorDataRepository doctorDataRepository;

	@Autowired
	private TransformDoctorService transformDoctorService;

	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Autowired
	private UserService userService;

	@Override
	public Doctor[] getAll() {
		List<DoctorData> doctorsData = new ArrayList<>();
		List<Doctor> doctors = new ArrayList<>();
		doctorDataRepository.findAll().forEach(doctorsData::add);
		for (DoctorData data : doctorsData) {
			doctors.add(transformDoctorService.transform(data));
		}
		return doctors.toArray(new Doctor[0]);
	}

	@Override
	public Doctor create(Doctor doctor) {
		logger.info("Creating doctor account: {} {}", doctor.getFirstName(), doctor.getLastName());

		// Ensure role is DOCTOR
		doctor.setRole("DOCTOR");
		if (doctor.getAvailabilityStatus() == null) {
			doctor.setAvailabilityStatus("AVAILABLE");
		}

		try {
			// 1. Create a corresponding User account for login
			User user = new User();
			user.setUsername(doctor.getUsername());
			user.setPassword(doctor.getPassword());
			user.setRole("DOCTOR");
			User createdUser = userService.create(user);

			// 2. Link the login account ID to the doctor profile
			doctor.setUserId((long) createdUser.getId());
		} catch (Exception e) {
			logger.error("Failed to create associated User account for doctor: {}", e.getMessage());
			// Fallback: we still save the doctor data, but without userId (login won't work
			// yet)
		}

		DoctorData doctorData = transformDoctorService.transform(doctor);
		doctorData = doctorDataRepository.save(doctorData);
		return transformDoctorService.transform(doctorData);
	}

	@Override
	public Doctor update(Doctor doctor) {
		logger.info("Updating doctor with id: {}", doctor.getId());
		Optional<DoctorData> optional = doctorDataRepository.findById(doctor.getId());

		if (optional.isPresent()) {
			DoctorData existing = optional.get();
			String oldStatus = existing.getAvailabilityStatus();
			String newStatus = doctor.getAvailabilityStatus();

			DoctorData toUpdate = transformDoctorService.transform(doctor);
			toUpdate.setCreated(existing.getCreated());
			toUpdate = doctorDataRepository.save(toUpdate);

			Doctor updated = transformDoctorService.transform(toUpdate);

			// Fire Kafka event if availability changed
			if (newStatus != null && !newStatus.equals(oldStatus)) {
				publishAvailabilityEvent(updated, "Availability changed to " + newStatus);
			}

			return updated;
		} else {
			logger.error("Doctor record with id: {} does not exist", doctor.getId());
			return null;
		}
	}

	private void publishAvailabilityEvent(Doctor doctor, String message) {
		DoctorEvent event = DoctorEvent.builder()
				.eventType("DoctorAvailabilityChangedEvent")
				.doctorId(doctor.getId())
				.availabilityStatus(doctor.getAvailabilityStatus())
				.message(message)
				.timestamp(LocalDateTime.now())
				.build();
		kafkaProducerService.sendEvent(event);
	}

	@Override
	public Doctor get(Integer id) {
		return doctorDataRepository.findById(id)
				.map(transformDoctorService::transform)
				.orElse(null);
	}

	@Override
	public void delete(Integer id) {
		doctorDataRepository.deleteById(id);
	}
}
