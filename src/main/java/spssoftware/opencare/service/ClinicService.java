package spssoftware.opencare.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spssoftware.opencare.domain.Clinic;
import spssoftware.opencare.repository.ClinicRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ClinicService {

    private ClinicRepository clinicRepository;
    private OpenCareObjectMapper openCareObjectMapper;

    @Autowired
    public ClinicService(ClinicRepository clinicRepository, OpenCareObjectMapper openCareObjectMapper) {
        this.clinicRepository = clinicRepository;
        this.openCareObjectMapper = openCareObjectMapper;
    }

    public List<Clinic> find(List<String> fields, Map<String, List<String>> constraints) {
        return Lists.newArrayList(clinicRepository.find(fields, constraints));
    }

    public Clinic get(String id) {
        return clinicRepository.get(id);
    }

    @Transactional
    public Clinic create(Clinic clinic) {
        clinic.setClinicId(UUID.randomUUID().toString());
        clinic.setCreatedDate(Timestamp.from(Instant.now()));
        return clinicRepository.save(clinic.getClinicId(), clinic);
    }

    @Transactional
    public Clinic patch(String id, JSONObject patch) {

        Clinic clinic = get(id);

        if (clinic == null) {
            return null;
        } else {
            Clinic mergedClinic = openCareObjectMapper.patchObject(patch, get(id));
            mergedClinic.setClinicId(id);
            mergedClinic.setModifiedDate(Timestamp.from(Instant.now()));
            return clinicRepository.save(id, mergedClinic);
        }
    }

    @Transactional
    public void delete(String id) {
        clinicRepository.delete(id);
    }
}