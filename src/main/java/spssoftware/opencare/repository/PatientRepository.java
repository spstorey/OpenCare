package spssoftware.opencare.repository;

import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spssoftware.opencare.domain.Patient;
import spssoftware.opencare.generated.tables.records.PatientRecord;

import java.util.List;
import java.util.UUID;

import static spssoftware.opencare.generated.tables.Address.ADDRESS;
import static spssoftware.opencare.generated.tables.Appointment.APPOINTMENT;
import static spssoftware.opencare.generated.tables.Patient.PATIENT;

@Component
public class PatientRepository {

    private static final Logger logger = LoggerFactory.getLogger(PatientRepository.class);

    private DSLContext connection;

    @Autowired
    public PatientRepository(DSLContext connection) {
        this.connection = connection;
    }

    public List<Patient> list(String clinicId, String surname, String postCode) {

        SelectJoinStep from = connection.select(PATIENT.fields()).from(PATIENT);

        if (clinicId != null) {
            from = from.join(APPOINTMENT).on(APPOINTMENT.PATIENT_ID.eq(PATIENT.ID));
        }

        if (postCode != null) {
            from = from.join(ADDRESS).on(ADDRESS.PATIENT_ID.eq(PATIENT.ID));
        }

        SelectConditionStep where = from.where(PATIENT.ID.isNotNull());
        if (clinicId != null) {
            where = where.and(APPOINTMENT.CLINIC_ID.eq(clinicId));
        }

        if (postCode != null) {
            where = where.and(ADDRESS.POSTCODE.eq(postCode.replaceAll(" ", "").toUpperCase()));
        }

        if (surname !=  null) {
            where = where.and(PATIENT.SURNAME.eq(surname));
        }

        return where.fetchInto(Patient.class);
    }

    public Patient get(String id) {
        return connection.selectFrom(PATIENT).where(PATIENT.ID.eq(id)).fetchOneInto(Patient.class);
    }

    public String add(Patient patient) {
        try {

            PatientRecord record = new PatientRecord();
            record.setId(UUID.randomUUID().toString());
            record.setTitle(patient.getTitle());
            record.setFirstName(patient.getFirstName());
            record.setMiddleNames(patient.getMiddleNames());
            record.setSurname(patient.getSurname());
            record.setDateOfBirth(patient.getDateOfBirth());
            record.setDateOfDeath(patient.getDateOfDeath());
            record.setNationalInsuranceNumber(patient.getNationalInsuranceNumber());
            record.setNhsNumber(patient.getNhsNumber());
            connection.newRecord(PATIENT, record).store();
            return record.getId();
        } catch (Exception e) {
            logger.error("Failed to add Patient " + patient, e);
            throw new RuntimeException("Failed to add Patient " + patient, e);
        }
    }

    public void update(Patient patient) {
        try {
            connection.update(PATIENT)
                    .set(PATIENT.TITLE, patient.getTitle())
                    .set(PATIENT.FIRST_NAME, patient.getFirstName())
                    .set(PATIENT.MIDDLE_NAMES, patient.getMiddleNames())
                    .set(PATIENT.SURNAME, patient.getSurname())
                    .set(PATIENT.DATE_OF_BIRTH, patient.getDateOfBirth())
                    .set(PATIENT.DATE_OF_DEATH, patient.getDateOfDeath())
                    .set(PATIENT.NATIONAL_INSURANCE_NUMBER, patient.getNationalInsuranceNumber())
                    .set(PATIENT.NHS_NUMBER, patient.getNhsNumber())
                    .where(PATIENT.ID.eq(patient.getId()));
        } catch (Exception e) {
            logger.error("Failed to update Patient " + patient, e);
            throw new RuntimeException("Failed to update Patient " + patient, e);
        }
    }

    public void delete(String id) {
        connection.deleteFrom(PATIENT).where(PATIENT.ID.eq(id));
    }
}
