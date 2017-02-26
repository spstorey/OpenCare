package spssoftware.opencare.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spssoftware.opencare.domain.Appointment;
import spssoftware.opencare.repository.AppointmentRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private OpenCareObjectMapper openCareObjectMapper;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, OpenCareObjectMapper openCareObjectMapper) {
        this.appointmentRepository = appointmentRepository;
        this.openCareObjectMapper = openCareObjectMapper;
    }

    public List<Appointment> find(List<String> fields, Map<String, List<String>> constraints) {
        return Lists.newArrayList(appointmentRepository.find(fields, constraints));
    }

    public Appointment get(String id) {
        return appointmentRepository.get(id);
    }

    @Transactional
    public Appointment create(Appointment appointment) {
        appointment.setAppointmentId(UUID.randomUUID().toString());
        appointment.setCreatedDate(Timestamp.from(Instant.now()));
        return appointmentRepository.save(appointment.getAppointmentId(), appointment);
    }

    @Transactional
    public Appointment patch(String id, JSONObject patch) {

        Appointment appointment = get(id);

        if (appointment == null) {
            return null;
        } else {
            Appointment mergedAppointment = openCareObjectMapper.patchObject(patch, get(id));
            mergedAppointment.setAppointmentId(id);
            mergedAppointment.setModifiedDate(Timestamp.from(Instant.now()));
            return appointmentRepository.save(id, mergedAppointment);
        }
    }

    @Transactional
    public void delete(String id) {
        appointmentRepository.delete(id);
    }
}