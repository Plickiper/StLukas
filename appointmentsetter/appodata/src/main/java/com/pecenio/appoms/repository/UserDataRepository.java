package com.pecenio.appoms.repository;

import com.pecenio.appoms.entity.UserData;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserDataRepository extends CrudRepository<UserData, Integer> {
    Optional<UserData> findByUsernameAndPassword(String username, String password);
}