package com.pecenio.patims.transform;

import com.pecenio.patims.entity.PatientData;
import com.pecenio.patims.model.Patient;
import org.springframework.stereotype.Service;

@Service
public class TransformPatientServiceImpl implements TransformPatientService {
	@Override
	public PatientData transform(Patient patient) {
		PatientData patientData = new PatientData();
		patientData.setId(patient.getId());
		patientData.setUserId(patient.getUserId());
		patientData.setUsername(patient.getUsername());
		patientData.setPassword(patient.getPassword());
		patientData.setRole(patient.getRole());
		patientData.setFirstName(patient.getFirstName());
		patientData.setLastName(patient.getLastName());
		patientData.setPhoneNumber(patient.getPhoneNumber());
		patientData.setDateOfBirth(patient.getDateOfBirth());
		patientData.setAge(patient.getAge());
		patientData.setAddress(patient.getAddress());
		patientData.setEmergencyContact(patient.getEmergencyContact());
		return patientData;
	}

	@Override

	public Patient transform(PatientData patientData) {
		;
		Patient patient = new Patient();
		patient.setId(patientData.getId());
		patient.setUserId(patientData.getUserId());
		patient.setUsername(patientData.getUsername());
		patient.setPassword(patientData.getPassword());
		patient.setRole(patientData.getRole());
		patient.setFirstName(patientData.getFirstName());
		patient.setLastName(patientData.getLastName());
		patient.setPhoneNumber(patientData.getPhoneNumber());
		patient.setDateOfBirth(patientData.getDateOfBirth());
		patient.setAge(patientData.getAge());
		patient.setAddress(patientData.getAddress());
		patient.setEmergencyContact(patientData.getEmergencyContact());
		patient.setCreated(patientData.getCreated());
		patient.setLastUpdated(patientData.getLastUpdated());
		return patient;
	}
}
