package com.pecenio.appoms.serviceimpl;
import com.pecenio.appoms.entity.AppointmentSetterData;
import com.pecenio.appoms.model.AppointmentSetter;
import com.pecenio.appoms.repository.AppointmentSetterDataRepository;
import com.pecenio.appoms.service.AppointmentSetterService;
import com.pecenio.appoms.transform.TransformAppointmentSetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class AppointmentSetterServiceImpl implements AppointmentSetterService {
	Logger logger = LoggerFactory.getLogger(AppointmentSetterServiceImpl.class);
	@Autowired
	AppointmentSetterDataRepository appointmentSetterDataRepository;
	@Autowired
	TransformAppointmentSetterService tansformAppointmentSetterService;
	@Override
	public AppointmentSetter[] getAll() {
		List<AppointmentSetterData> appointmentSettersData = new ArrayList<>();
		List<AppointmentSetter> appointmentSetters = new ArrayList<>();
		appointmentSetterDataRepository.findAll().forEach(appointmentSettersData::add);
		Iterator<AppointmentSetterData> it = appointmentSettersData.iterator();
		while(it.hasNext()) {
			AppointmentSetterData appointmentSetterData = it.next();
			AppointmentSetter appointmentSetter = tansformAppointmentSetterService.transform(appointmentSetterData);
			appointmentSetters.add(appointmentSetter);
		}
		AppointmentSetter[] array = new AppointmentSetter[appointmentSetters.size()];
		for  (int i=0; i<appointmentSetters.size(); i++){
			array[i] = appointmentSetters.get(i);
		}
		return array;
	}
	@Override
	public AppointmentSetter create(AppointmentSetter appointmentSetter) {
		logger.info(" add:Input " + appointmentSetter.toString());
		AppointmentSetterData appointmentSetterData = tansformAppointmentSetterService.transform(appointmentSetter);
		appointmentSetterData = appointmentSetterDataRepository.save(appointmentSetterData);
		logger.info(" add:Input " + appointmentSetterData.toString());
			AppointmentSetter newAppointmentSetter = tansformAppointmentSetterService.transform(appointmentSetterData);
		return newAppointmentSetter;
	}

	@Override
	public AppointmentSetter update(AppointmentSetter appointmentSetter) {
		AppointmentSetter updatedAppointmentSetter = null;
		int id = appointmentSetter.getId();
		Optional<AppointmentSetterData> optional  = appointmentSetterDataRepository.findById(appointmentSetter.getId());
		if(optional.isPresent()){
			AppointmentSetterData originalAppointmentSetterData = tansformAppointmentSetterService.transform(appointmentSetter);
			originalAppointmentSetterData.setCreated(optional.get().getCreated());
			AppointmentSetterData appointmentSetterData = appointmentSetterDataRepository.save(originalAppointmentSetterData);
			updatedAppointmentSetter = tansformAppointmentSetterService.transform(appointmentSetterData);
		}
		else {
			logger.error("AppointmentSetter record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedAppointmentSetter;
	}

	@Override
	public AppointmentSetter get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		AppointmentSetter appointmentSetter = null;
		Optional<AppointmentSetterData> optional = appointmentSetterDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			appointmentSetter = tansformAppointmentSetterService.transform(optional.get());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return appointmentSetter;
	}
	@Override
	public void delete(Integer id) {
		AppointmentSetter appointmentSetter = null;
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<AppointmentSetterData> optional = appointmentSetterDataRepository.findById(id);
		if( optional.isPresent()) {
			AppointmentSetterData appointmentSetterDatum = optional.get();
			appointmentSetterDataRepository.delete(optional.get());
			logger.info(" Successfully deleted AppointmentSetter record with id: " + Integer.toString(id));
			appointmentSetter = tansformAppointmentSetterService.transform(optional.get());
		}
		else {
			logger.error(" Unable to locate appointmentSetter with id:" +  Integer.toString(id));
		}
	}
}
