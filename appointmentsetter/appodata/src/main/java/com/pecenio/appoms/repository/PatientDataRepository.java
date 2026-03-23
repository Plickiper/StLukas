package com.pecenio.appoms.repository;
import com.pecenio.appoms.entity.PatientData;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;
public interface PatientDataRepository extends CrudRepository<PatientData,Integer> {
    Optional<PatientData> findByUserId(Long userId);
}