package com.pecenio.appoms.serviceimpl;

import com.pecenio.appoms.entity.UserData;
import com.pecenio.appoms.model.User;
import com.pecenio.appoms.repository.UserDataRepository;
import com.pecenio.appoms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public User[] getAll() {
        return StreamSupport.stream(userDataRepository.findAll().spliterator(), false)
                .map(this::toModel)
                .toArray(User[]::new);
    }

    @Override
    public User create(User user) {
        logger.info("Creating user: {}", user.getUsername());
        UserData userData = toEntity(user);
        userData = userDataRepository.save(userData);
        return toModel(userData);
    }

    @Override
    public User update(User user) {
        logger.info("Updating user with id: {}", user.getId());
        Optional<UserData> optional = userDataRepository.findById(user.getId());
        if (optional.isPresent()) {
            UserData userData = toEntity(user);
            userData.setCreated(optional.get().getCreated());
            userData = userDataRepository.save(userData);
            return toModel(userData);
        } else {
            logger.error("User record with id: {} does not exist", user.getId());
            return null;
        }
    }

    @Override
    public User get(Integer id) {
        logger.info("Getting user with id: {}", id);
        return userDataRepository.findById(id)
                .map(this::toModel)
                .orElseGet(() -> {
                    logger.warn("User with id: {} not found", id);
                    return null;
                });
    }

    @Override
    public void delete(Integer id) {
        logger.info("Deleting user with id: {}", id);
        if (userDataRepository.existsById(id)) {
            userDataRepository.deleteById(id);
            logger.info("Successfully deleted user with id: {}", id);
        } else {
            logger.error("Unable to locate user with id: {}", id);
        }
    }

    @Override
    public User login(String username, String password) {
        logger.info("Attempting login for user: {}", username);
        return userDataRepository.findByUsernameAndPassword(username, password)
                .map(this::toModel)
                .orElse(null);
    }

    private User toModel(UserData entity) {
        if (entity == null)
            return null;
        User model = new User();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setPassword(entity.getPassword());
        model.setRole(entity.getRole());
        model.setCreated(entity.getCreated());
        model.setLastUpdated(entity.getLastUpdated());
        return model;
    }

    private UserData toEntity(User model) {
        if (model == null)
            return null;
        UserData entity = new UserData();
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());
        entity.setRole(model.getRole());
        return entity;
    }
}
