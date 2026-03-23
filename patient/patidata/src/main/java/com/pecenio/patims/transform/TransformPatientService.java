package com.pecenio.patims.transform;
import com.pecenio.patims.entity.PatientData;
import com.pecenio.patims.model.Patient;
public interface TransformPatientService {
	PatientData transform(Patient patient);
	Patient transform(PatientData patientData);
}
