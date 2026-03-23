package com.pecenio.appoms.serviceimpl;

import com.pecenio.appoms.entity.DoctorData;
import com.pecenio.appoms.model.Doctor;
import com.pecenio.appoms.repository.DoctorDataRepository;
import com.pecenio.appoms.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class DoctorServiceImpl implements DoctorService {
    private static final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);

    @Autowired
    private DoctorDataRepository doctorDataRepository;

    @Override
    public Doctor[] getAll() {
        return StreamSupport.stream(doctorDataRepository.findAll().spliterator(), false)
                .map(this::toModel)
                .toArray(Doctor[]::new);
    }

    @Override
    public Doctor create(Doctor doctor) {
        logger.info("Creating doctor profile for user: {}", doctor.getUsername());
        DoctorData doctorData = toEntity(doctor);
        doctorData = doctorDataRepository.save(doctorData);
        return toModel(doctorData);
    }

    @Override
    public Doctor update(Doctor doctor) {
        logger.info("Updating doctor profile with id: {}", doctor.getId());
        Optional<DoctorData> optional = doctorDataRepository.findById(doctor.getId());
        if (optional.isPresent()) {
            DoctorData doctorData = toEntity(doctor);
            doctorData.setCreated(optional.get().getCreated());
            doctorData = doctorDataRepository.save(doctorData);
            return toModel(doctorData);
        } else {
            logger.error("Doctor record with id: {} does not exist", doctor.getId());
            return null;
        }
    }

    @Override
    public Doctor get(Integer id) {
        logger.info("Getting doctor profile with id: {}", id);
        return doctorDataRepository.findById(id)
                .map(this::toModel)
                .orElseGet(() -> {
                    logger.warn("Doctor with id: {} not found", id);
                    return null;
                });
    }

    @Override
    public void delete(Integer id) {
        logger.info("Deleting doctor profile with id: {}", id);
        if (doctorDataRepository.existsById(id)) {
            doctorDataRepository.deleteById(id);
            logger.info("Successfully deleted doctor profile with id: {}", id);
        } else {
            logger.error("Unable to locate doctor with id: {}", id);
        }
    }

    private Doctor toModel(DoctorData entity) {
        if (entity == null) return null;
        Doctor model = new Doctor();
        model.setId(entity.getId());
        model.setUserId(entity.getUserId());
        model.setUsername(entity.getUsername());
        model.setPassword(entity.getPassword());
        model.setRole(entity.getRole());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setSpecialization(entity.getSpecialization());
        model.setAvailabilityStatus(entity.getAvailabilityStatus());
        model.setCreated(entity.getCreated());
        model.setLastUpdated(entity.getLastUpdated());
        return model;
    }

    private DoctorData toEntity(Doctor model) {
        if (model == null) return null;
        DoctorData entity = new DoctorData();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());
        entity.setRole(model.getRole());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setSpecialization(model.getSpecialization());
        entity.setAvailabilityStatus(model.getAvailabilityStatus());
        return entity;
    }
}
