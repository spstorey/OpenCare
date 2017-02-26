package spssoftware.opencare.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spssoftware.generated.tables.records.ClinicRecord;
import spssoftware.opencare.domain.Clinic;

import static spssoftware.generated.tables.Clinic.CLINIC;

@Component
public class ClinicRepository extends AbstractRepository<Clinic, ClinicRecord> {

    @Autowired
    public ClinicRepository(DSLContext connection) {
        this.TABLE = CLINIC;
        this.ID = CLINIC.CLINIC_ID;
        this.objectClass = Clinic.class;
        this.connection = connection;
    }
}