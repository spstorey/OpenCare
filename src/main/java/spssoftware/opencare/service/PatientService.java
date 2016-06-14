package spssoftware.opencare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spssoftware.opencare.domain.Patient;
import spssoftware.opencare.repository.PatientRepository;

import java.util.List;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> list(String clinicId, String surname, String postCode) {
        return patientRepository.list(clinicId, surname, postCode);
    }

    public Patient get(String id) {
        return patientRepository.get(id);
    }

    public void add(Patient patient) {
        patientRepository.add(patient);
    }

    public void update(Patient patient) {
        patientRepository.update(patient);
    }

    public void delete(String id) {
        patientRepository.delete(id);
    }
}