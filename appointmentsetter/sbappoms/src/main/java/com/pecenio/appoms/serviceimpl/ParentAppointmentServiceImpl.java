package com.pecenio.appoms.serviceimpl;

import com.pecenio.appoms.entity.ParentAppointmentData;
import com.pecenio.appoms.model.ParentAppointment;
import com.pecenio.appoms.repository.ParentAppointmentDataRepository;
import com.pecenio.appoms.service.ParentAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ParentAppointmentServiceImpl implements ParentAppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(ParentAppointmentServiceImpl.class);

    @Autowired
    private ParentAppointmentDataRepository repository;

    @Override
    public ParentAppointment[] getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::toModel)
                .toArray(ParentAppointment[]::new);
    }

    @Override
    public ParentAppointment create(ParentAppointment model) {
        logger.info("Creating parent appointment");
        ParentAppointmentData entity = toEntity(model);
        entity = repository.save(entity);
        return toModel(entity);
    }

    @Override
    public ParentAppointment update(ParentAppointment model) {
        logger.info("Updating parent appointment with id: {}", model.getId());
        Optional<ParentAppointmentData> optional = repository.findById(model.getId());
        if (optional.isPresent()) {
            ParentAppointmentData entity = toEntity(model);
            entity.setCreated(optional.get().getCreated());
            entity = repository.save(entity);
            return toModel(entity);
        } else {
            logger.error("Parent appointment record with id: {} does not exist", model.getId());
            return null;
        }
    }

    @Override
    public ParentAppointment get(Integer id) {
        logger.info("Getting parent appointment with id: {}", id);
        return repository.findById(id)
                .map(this::toModel)
                .orElseGet(() -> {
                    logger.warn("Parent appointment with id: {} not found", id);
                    return null;
                });
    }

    @Override
    public void delete(Integer id) {
        logger.info("Deleting parent appointment with id: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            logger.info("Successfully deleted parent appointment with id: {}", id);
        } else {
            logger.error("Unable to locate parent appointment with id: {}", id);
        }
    }

    private ParentAppointment toModel(ParentAppointmentData entity) {
        if (entity == null) return null;
        ParentAppointment model = new ParentAppointment();
        model.setId(entity.getId());
        model.setPatientId(entity.getPatientId());
        model.setDoctorId(entity.getDoctorId());
        model.setPreferredDate(entity.getPreferredDate());
        model.setPreferredTimeRange(entity.getPreferredTimeRange());
        model.setScheduledDate(entity.getScheduledDate());
        model.setScheduledTime(entity.getScheduledTime());
        model.setReason(entity.getReason());
        model.setStatus(entity.getStatus());
        model.setCreated(entity.getCreated());
        model.setLastUpdated(entity.getLastUpdated());
        return model;
    }

    private ParentAppointmentData toEntity(ParentAppointment model) {
        if (model == null) return null;
        ParentAppointmentData entity = new ParentAppointmentData();
        entity.setId(model.getId());
        entity.setPatientId(model.getPatientId());
        entity.setDoctorId(model.getDoctorId());
        entity.setPreferredDate(model.getPreferredDate());
        entity.setPreferredTimeRange(model.getPreferredTimeRange());
        entity.setScheduledDate(model.getScheduledDate());
        entity.setScheduledTime(model.getScheduledTime());
        entity.setReason(model.getReason());
        entity.setStatus(model.getStatus());
        return entity;
    }
}
