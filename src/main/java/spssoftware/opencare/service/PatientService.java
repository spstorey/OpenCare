package spssoftware.opencare.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spssoftware.opencare.domain.Patient;
import spssoftware.opencare.repository.PatientRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private OpenCareObjectMapper openCareObjectMapper;

    @Autowired
    public PatientService(PatientRepository patientRepository, OpenCareObjectMapper openCareObjectMapper) {
        this.patientRepository = patientRepository;
        this.openCareObjectMapper = openCareObjectMapper;
    }

    public List<Patient> find(List<String> fields, Map<String, List<String>> constraints) {
        return Lists.newArrayList(patientRepository.find(fields, constraints));
    }

    public Patient get(String id) {
        return patientRepository.get(id);
    }

    @Transactional
    public Patient create(Patient patient) {
        patient.setPatientId(UUID.randomUUID().toString());
        patient.setCreatedDate(Timestamp.from(Instant.now()));
        return patientRepository.save(patient.getPatientId(), patient);
    }

    @Transactional
    public Patient patch(String id, JSONObject patch) {

        Patient patient = get(id);

        if (patient == null) {
            return null;
        } else {
            Patient mergedPatient = openCareObjectMapper.patchObject(patch, get(id));
            mergedPatient.setPatientId(id);
            mergedPatient.setModifiedDate(Timestamp.from(Instant.now()));
            return patientRepository.save(id, mergedPatient);
        }
    }

    @Transactional
    public void delete(String id) {
        patientRepository.delete(id);
    }
}