package com.pecenio.appoms.service;
import com.pecenio.appoms.model.AppointmentSetter;
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
public class AppointmentSetterService {
	Logger logger = LoggerFactory.getLogger(AppointmentSetterService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8083/api/appointmentSetter";

	protected static AppointmentSetterService service= null;
	public static AppointmentSetterService getService(){
		if(service == null){
			service = new AppointmentSetterService();
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

	public AppointmentSetter get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<AppointmentSetter> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, AppointmentSetter.class);
		return response.getBody();
	}

	public AppointmentSetter[] getAll() {
		String url = endpointUrl;
		logger.info("getAppointmentSetters: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<AppointmentSetter[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, AppointmentSetter[].class);
		AppointmentSetter[] appointmentSetters = response.getBody();
		return appointmentSetters;
	}

	public AppointmentSetter create(AppointmentSetter appointmentSetter) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<AppointmentSetter> request = new HttpEntity<>(appointmentSetter, headers);
		final ResponseEntity<AppointmentSetter> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, AppointmentSetter.class);
		return response.getBody();
	}
	public AppointmentSetter update(AppointmentSetter appointmentSetter) {
		logger.info("update: " + appointmentSetter.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<AppointmentSetter> request = new HttpEntity<>(appointmentSetter, headers);
		final ResponseEntity<AppointmentSetter> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, AppointmentSetter.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<AppointmentSetter> request = new HttpEntity<>(null, headers);
		final ResponseEntity<AppointmentSetter> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, AppointmentSetter.class);
	}
}
