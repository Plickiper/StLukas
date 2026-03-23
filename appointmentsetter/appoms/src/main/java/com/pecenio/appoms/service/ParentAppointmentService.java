package com.pecenio.appoms.service;
import com.pecenio.appoms.model.ParentAppointment;
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
public class ParentAppointmentService {
	Logger logger = LoggerFactory.getLogger(ParentAppointmentService.class);
	@Value("${service.api.endpoint}")
	private String endpointUrl = "http://localhost:8083/api/parentAppointment";

	protected static ParentAppointmentService service= null;
	public static ParentAppointmentService getService(){
		if(service == null){
			service = new ParentAppointmentService();
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

	public ParentAppointment get(Integer id) {
		String url = endpointUrl + "/" + Integer.toString(id);
		logger.info("get: "  + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<ParentAppointment> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, ParentAppointment.class);
		return response.getBody();
	}

	public ParentAppointment[] getAll() {
		String url = endpointUrl;
		logger.info("getParentAppointments: " + url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity request = new HttpEntity<>(null, headers);
		final ResponseEntity<ParentAppointment[]> response =
		getRestTemplate().exchange(url, HttpMethod.GET, request, ParentAppointment[].class);
		ParentAppointment[] parentAppointments = response.getBody();
		return parentAppointments;
	}

	public ParentAppointment create(ParentAppointment parentAppointment) {
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ParentAppointment> request = new HttpEntity<>(parentAppointment, headers);
		final ResponseEntity<ParentAppointment> response =
		getRestTemplate().exchange(url, HttpMethod.PUT, request, ParentAppointment.class);
		return response.getBody();
	}
	public ParentAppointment update(ParentAppointment parentAppointment) {
		logger.info("update: " + parentAppointment.toString());
		String url = endpointUrl;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ParentAppointment> request = new HttpEntity<>(parentAppointment, headers);
		final ResponseEntity<ParentAppointment> response =
		getRestTemplate().exchange(url, HttpMethod.POST, request, ParentAppointment.class);
		return response.getBody();
	}

	public void delete(Integer id){
		logger.info("delete: " + Integer.toString(id));
		String url = endpointUrl + " / " + Integer.toString(id);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ParentAppointment> request = new HttpEntity<>(null, headers);
		final ResponseEntity<ParentAppointment> response =
		getRestTemplate().exchange(url, HttpMethod.DELETE, request, ParentAppointment.class);
	}
}
