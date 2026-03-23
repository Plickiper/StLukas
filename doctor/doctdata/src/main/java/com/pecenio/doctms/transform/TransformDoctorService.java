package com.pecenio.doctms.transform;
import com.pecenio.doctms.entity.DoctorData;
import com.pecenio.doctms.model.Doctor;
public interface TransformDoctorService {
	DoctorData transform(Doctor doctor);
	Doctor transform(DoctorData doctorData);
}
