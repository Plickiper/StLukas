package com.pecenio.patims.service;

import com.pecenio.patims.model.Patient;

public interface PatientService {
	Patient[] getAll() throws Exception;

	Patient get(Integer id) throws Exception;

	Patient create(Patient patient) throws Exception;

	Patient update(Patient patient) throws Exception;

	void delete(Integer id) throws Exception;

	Patient getByUserId(Long userId) throws Exception;
}
