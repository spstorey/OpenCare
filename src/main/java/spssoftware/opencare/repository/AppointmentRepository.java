package spssoftware.opencare.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spssoftware.generated.tables.records.AppointmentRecord;
import spssoftware.opencare.domain.Appointment;

import static spssoftware.generated.tables.Appointment.APPOINTMENT;

@Component
public class AppointmentRepository extends AbstractRepository<Appointment, AppointmentRecord> {

    @Autowired
    public AppointmentRepository(DSLContext connection) {
        this.TABLE = APPOINTMENT;
        this.ID = APPOINTMENT.APPOINTMENT_ID;
        this.objectClass = Appointment.class;
        this.connection = connection;
    }
}