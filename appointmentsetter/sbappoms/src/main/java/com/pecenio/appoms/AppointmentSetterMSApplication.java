package com.pecenio.appoms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.pecenio.appoms.model.User;
import com.pecenio.appoms.service.UserService;

@SpringBootApplication
public class AppointmentSetterMSApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppointmentSetterMSApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserService userService) {
		return args -> {
			User adminUser = userService.login("admin", "12345");
			if (adminUser == null) {
				// Check if admin exists but has wrong password
				User[] allUsers = userService.getAll();
				User existingAdmin = null;
				for (User u : allUsers) {
					if ("admin".equals(u.getUsername())) {
						existingAdmin = u;
						break;
					}
				}

				if (existingAdmin != null) {
					existingAdmin.setPassword("12345");
					existingAdmin.setRole("ADMIN");
					userService.update(existingAdmin);
					System.out.println("System-defined Default Admin account updated: admin/12345");
				} else {
					User newAdmin = new User();
					newAdmin.setUsername("admin");
					newAdmin.setPassword("12345");
					newAdmin.setRole("ADMIN");
					userService.create(newAdmin);
					System.out.println("System-defined Default Admin account created: admin/12345");
				}
			} else {
				System.out.println("Default Admin account already exists and is verified.");
			}
		};
	}
}
