package com.pecenio.patims.service;

import com.pecenio.patims.model.User;

public interface UserService {
	User[] getAll() throws Exception;

	User get(Integer id) throws Exception;

	User login(String username, String password) throws Exception;

	User create(User user) throws Exception;

	User update(User user) throws Exception;

	void delete(Integer id) throws Exception;
}
