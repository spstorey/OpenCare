package spssoftware.opencare.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spssoftware.generated.tables.records.OrganisationRecord;
import spssoftware.opencare.domain.Organisation;

import static spssoftware.generated.tables.Organisation.ORGANISATION;

@Component
public class OrganisationRepository extends AbstractRepository<Organisation, OrganisationRecord> {

    @Autowired
    public OrganisationRepository(DSLContext connection) {
        this.TABLE = ORGANISATION;
        this.ID = ORGANISATION.ORGANISATION_ID;
        this.objectClass = Organisation.class;
        this.connection = connection;
    }
}