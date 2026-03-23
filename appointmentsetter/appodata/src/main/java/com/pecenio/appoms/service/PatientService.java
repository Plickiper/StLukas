package com.pecenio.appoms.service;
import com.pecenio.appoms.model.Patient;
public interface PatientService {
	Patient[] getAll() throws Exception;
	Patient get(Integer id) throws Exception;
	Patient create(Patient patient) throws Exception;
	Patient update(Patient patient) throws Exception;
	void delete(Integer id) throws Exception;
}
