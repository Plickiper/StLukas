package com.pecenio.doctms.transform;
import com.pecenio.doctms.entity.DoctorData;
import com.pecenio.doctms.model.Doctor;
import org.springframework.stereotype.Service;
@Service
public class TransformDoctorServiceImpl implements TransformDoctorService {
	@Override
	public DoctorData transform(Doctor doctor){
		DoctorData doctorData = new DoctorData();
		doctorData.setId(doctor.getId());
		doctorData.setUserId(doctor.getUserId());
		doctorData.setUsername(doctor.getUsername());
		doctorData.setPassword(doctor.getPassword());
		doctorData.setRole(doctor.getRole());
		doctorData.setFirstName(doctor.getFirstName());
		doctorData.setLastName(doctor.getLastName());
		doctorData.setSpecialization(doctor.getSpecialization());
		doctorData.setAvailabilityStatus(doctor.getAvailabilityStatus());
		return doctorData;
	}
	@Override

	public Doctor transform(DoctorData doctorData){;
		Doctor doctor = new Doctor();
		doctor.setId(doctorData.getId());
		doctor.setUserId(doctorData.getUserId());
		doctor.setUsername(doctorData.getUsername());
		doctor.setPassword(doctorData.getPassword());
		doctor.setRole(doctorData.getRole());
		doctor.setFirstName(doctorData.getFirstName());
		doctor.setLastName(doctorData.getLastName());
		doctor.setSpecialization(doctorData.getSpecialization());
		doctor.setAvailabilityStatus(doctorData.getAvailabilityStatus());
		doctor.setCreated(doctorData.getCreated());
		doctor.setLastUpdated(doctorData.getLastUpdated());
		return doctor;
	}
}
