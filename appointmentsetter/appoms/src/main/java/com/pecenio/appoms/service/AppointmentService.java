package com.pecenio.appoms.service;
import com.pecenio.appoms.model.Appointment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AppointmentService {
	Logger logger = LoggerFactory.getLogger(AppointmentService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8080/api/appointment";

	protected static AppointmentService service= null;
	public static AppointmentService getService(){
		if(service == null){
			service = new AppointmentService();
		}
		return service;
	}

	RestTemplate restTemplate = null;
	public RestTemplate getRestTemplate() {
		if(restTemplate == null) {
		restTemplate = new RestTemplate();
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
			converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
			messageConverters.add(converter);
			restTemplate.setMessageConverters(messageConverters);
		}
		return restTemplate;
	}

	public Appointment get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Appointment> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Appointment.class);
		return response.getBody();
	}

	public Appointment[] getAll() {
		String url = endpointUrl;
		logger.info("getAppointments: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Appointment[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Appointment[].class);
		Appointment[] appointments = response.getBody();
		return appointments;
	}

	public Appointment create(Appointment appointment) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Appointment> request = new HttpEntity<>(appointment, headers);
		final ResponseEntity<Appointment> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Appointment.class);
		return response.getBody();
	}
	public Appointment update(Appointment appointment) {
		logger.info("update: " + appointment.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Appointment> request = new HttpEntity<>(appointment, headers);
		final ResponseEntity<Appointment> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Appointment.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Appointment> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Appointment> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Appointment.class);
	}
}
