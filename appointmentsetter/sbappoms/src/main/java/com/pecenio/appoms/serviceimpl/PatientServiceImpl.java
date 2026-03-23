package com.pecenio.appoms.serviceimpl;

import com.pecenio.appoms.entity.PatientData;
import com.pecenio.appoms.model.Patient;
import com.pecenio.appoms.repository.PatientDataRepository;
import com.pecenio.appoms.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class PatientServiceImpl implements PatientService {
    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Autowired
    private PatientDataRepository patientDataRepository;

    @Override
    public Patient[] getAll() {
        return StreamSupport.stream(patientDataRepository.findAll().spliterator(), false)
                .map(this::toModel)
                .toArray(Patient[]::new);
    }

    @Override
    public Patient create(Patient patient) {
        logger.info("Creating patient profile for user: {}", patient.getUsername());
        PatientData patientData = toEntity(patient);
        patientData = patientDataRepository.save(patientData);
        return toModel(patientData);
    }

    @Override
    public Patient update(Patient patient) {
        logger.info("Updating patient profile with id: {}", patient.getId());
        Optional<PatientData> optional = patientDataRepository.findById(patient.getId());
        if (optional.isPresent()) {
            PatientData patientData = toEntity(patient);
            patientData.setCreated(optional.get().getCreated());
            patientData = patientDataRepository.save(patientData);
            return toModel(patientData);
        } else {
            logger.error("Patient record with id: {} does not exist", patient.getId());
            return null;
        }
    }

    @Override
    public Patient get(Integer id) {
        logger.info("Getting patient profile with id: {}", id);
        return patientDataRepository.findById(id)
                .map(this::toModel)
                .orElseGet(() -> {
                    logger.warn("Patient with id: {} not found", id);
                    return null;
                });
    }

    @Override
    public void delete(Integer id) {
        logger.info("Deleting patient profile with id: {}", id);
        if (patientDataRepository.existsById(id)) {
            patientDataRepository.deleteById(id);
            logger.info("Successfully deleted patient profile with id: {}", id);
        } else {
            logger.error("Unable to locate patient with id: {}", id);
        }
    }

    private Patient toModel(PatientData entity) {
        if (entity == null) return null;
        Patient model = new Patient();
        model.setId(entity.getId());
        model.setUserId(entity.getUserId());
        model.setUsername(entity.getUsername());
        model.setPassword(entity.getPassword());
        model.setRole(entity.getRole());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setPhoneNumber(entity.getPhoneNumber());
        model.setCreated(entity.getCreated());
        model.setLastUpdated(entity.getLastUpdated());
        return model;
    }

    private PatientData toEntity(Patient model) {
        if (model == null) return null;
        PatientData entity = new PatientData();
        entity.setId(model.getId());
        entity.setUserId(model.getUserId());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());
        entity.setRole(model.getRole());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setPhoneNumber(model.getPhoneNumber());
        return entity;
    }
}
