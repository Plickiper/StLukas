package com.pecenio.appoms.service;
import com.pecenio.appoms.model.Patient;
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
public class PatientService {
	Logger logger = LoggerFactory.getLogger(PatientService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8083/api/patient";

	protected static PatientService service= null;
	public static PatientService getService(){
		if(service == null){
			service = new PatientService();
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

	public Patient get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Patient> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Patient.class);
		return response.getBody();
	}

	public Patient[] getAll() {
		String url = endpointUrl;
		logger.info("getPatients: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<Patient[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, Patient[].class);
		Patient[] patients = response.getBody();
		return patients;
	}

	public Patient create(Patient patient) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Patient> request = new HttpEntity<>(patient, headers);
		final ResponseEntity<Patient> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, Patient.class);
		return response.getBody();
	}
	public Patient update(Patient patient) {
		logger.info("update: " + patient.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Patient> request = new HttpEntity<>(patient, headers);
		final ResponseEntity<Patient> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, Patient.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Patient> request = new HttpEntity<>(null, headers);
		final ResponseEntity<Patient> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, Patient.class);
	}
}
