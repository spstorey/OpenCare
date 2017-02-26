package spssoftware.opencare.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spssoftware.generated.tables.records.StaffRecord;
import spssoftware.opencare.domain.Staff;

import static spssoftware.generated.tables.Staff.STAFF;

@Component
public class StaffRepository extends AbstractRepository<Staff, StaffRecord> {

    @Autowired
    public StaffRepository(DSLContext connection) {
        this.TABLE = STAFF;
        this.ID = STAFF.STAFF_ID;
        this.objectClass = Staff.class;
        this.connection = connection;
    }
}
