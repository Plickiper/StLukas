package com.pecenio.patims.serviceimpl;

import com.pecenio.patims.entity.PatientData;
import com.pecenio.patims.model.Patient;
import com.pecenio.patims.model.PatientEvent;
import com.pecenio.patims.repository.PatientDataRepository;
import com.pecenio.patims.service.PatientService;
import com.pecenio.patims.transform.TransformPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
	private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Autowired
	private PatientDataRepository patientDataRepository;

	@Autowired
	private TransformPatientService transformPatientService;

	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Autowired
	private com.pecenio.patims.service.UserService userService;

	@Override
	public Patient[] getAll() {
		List<PatientData> patientsData = new ArrayList<>();
		List<Patient> patients = new ArrayList<>();
		patientDataRepository.findAll().forEach(patientsData::add);
		for (PatientData data : patientsData) {
			patients.add(transformPatientService.transform(data));
		}
		return patients.toArray(new Patient[0]);
	}

	@Override
	public Patient create(Patient patient) {
		logger.info("Creating patient account: {} {}", patient.getFirstName(), patient.getLastName());

		patient.setRole("PATIENT");

		// 1. Create User account for login
		try {
			com.pecenio.patims.model.User user = new com.pecenio.patims.model.User();
			user.setUsername(patient.getUsername());
			user.setPassword(patient.getPassword());
			user.setRole("PATIENT");
			com.pecenio.patims.model.User createdUser = userService.create(user);
			patient.setUserId((long) createdUser.getId());
		} catch (Exception e) {
			logger.error("Failed to create associated User account for patient: {}", e.getMessage());
		}

		PatientData patientData = transformPatientService.transform(patient);
		patientData = patientDataRepository.save(patientData);

		Patient saved = transformPatientService.transform(patientData);

		// Fire Registration Event
		publishRegistrationEvent(saved);

		return saved;
	}

	@Override
	public Patient update(Patient patient) {
		logger.info("Updating patient with id: {}", patient.getId());
		Optional<PatientData> optional = patientDataRepository.findById(patient.getId());

		if (optional.isPresent()) {
			PatientData existing = optional.get();
			PatientData toUpdate = transformPatientService.transform(patient);
			toUpdate.setCreated(existing.getCreated());
			toUpdate = patientDataRepository.save(toUpdate);
			return transformPatientService.transform(toUpdate);
		} else {
			logger.error("Patient record with id: {} does not exist", patient.getId());
			return null;
		}
	}

	private void publishRegistrationEvent(Patient patient) {
		PatientEvent event = PatientEvent.builder()
				.eventType("PatientRegisteredEvent")
				.patientId(patient.getId())
				.message("Welcome " + patient.getFirstName() + "!")
				.timestamp(LocalDateTime.now())
				.build();
		kafkaProducerService.sendEvent(event);
	}

	@Override
	public Patient get(Integer id) {
		return patientDataRepository.findById(id)
				.map(transformPatientService::transform)
				.orElse(null);
	}

	@Override
	public void delete(Integer id) {
		patientDataRepository.deleteById(id);
	}

	@Override
	public Patient getByUserId(Long userId) {
		return patientDataRepository.findByUserId(userId)
				.map(transformPatientService::transform)
				.orElse(null);
	}
}
