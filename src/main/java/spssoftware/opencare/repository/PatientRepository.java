package spssoftware.opencare.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spssoftware.generated.tables.records.PatientRecord;
import spssoftware.opencare.domain.Patient;

import static spssoftware.generated.tables.Patient.PATIENT;

@Component
public class PatientRepository extends AbstractRepository<Patient, PatientRecord> {

    @Autowired
    public PatientRepository(DSLContext connection) {
        this.TABLE = PATIENT;
        this.ID = PATIENT.PATIENT_ID;
        this.objectClass = Patient.class;
        this.connection = connection;
    }
}