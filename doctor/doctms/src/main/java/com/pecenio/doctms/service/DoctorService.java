package com.pecenio.doctms.service;
import com.pecenio.doctms.model.Doctor;
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
public class DoctorService {
	Logger logger = LoggerFactory.getLogger(DoctorService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8082/api/doctor";

	protected static DoctorService service= null;
	public static DoctorService getService(){
		if(service == null){
			service = new DoctorService();
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

	public Doctor get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Doctor> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Doctor.class);
		return response.getBody();
	}

	public Doctor[] getAll() {
		String url = endpointUrl;
		logger.info("getDoctors: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Doctor[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Doctor[].class);
		Doctor[] doctors = response.getBody();
		return doctors;
	}

	public Doctor create(Doctor doctor) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Doctor> request = new HttpEntity<>(doctor, headers);
		final ResponseEntity<Doctor> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Doctor.class);
		return response.getBody();
	}
	public Doctor update(Doctor doctor) {
		logger.info("update: " + doctor.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Doctor> request = new HttpEntity<>(doctor, headers);
		final ResponseEntity<Doctor> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Doctor.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Doctor> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Doctor> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Doctor.class);
	}
}
