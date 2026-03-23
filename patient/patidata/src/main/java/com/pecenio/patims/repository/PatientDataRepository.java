package com.pecenio.patims.repository;

import com.pecenio.patims.entity.PatientData;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PatientDataRepository extends CrudRepository<PatientData, Integer> {
    Optional<PatientData> findByUserId(Long userId);
}