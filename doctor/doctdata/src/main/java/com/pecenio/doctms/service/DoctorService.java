package com.pecenio.doctms.service;
import com.pecenio.doctms.model.Doctor;
public interface DoctorService {
	Doctor[] getAll() throws Exception;
	Doctor get(Integer id) throws Exception;
	Doctor create(Doctor doctor) throws Exception;
	Doctor update(Doctor doctor) throws Exception;
	void delete(Integer id) throws Exception;
}
