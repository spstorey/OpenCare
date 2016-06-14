package spssoftware.opencare.repository;

import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spssoftware.opencare.domain.Organisation;
import spssoftware.opencare.generated.tables.records.OrganisationRecord;

import java.util.List;
import java.util.UUID;

import static spssoftware.opencare.generated.tables.Address.ADDRESS;
import static spssoftware.opencare.generated.tables.Organisation.ORGANISATION;

@Component
public class OrganisationRepository {

    private static final Logger logger = LoggerFactory.getLogger(OrganisationRepository.class);

    private DSLContext connection;

    @Autowired
    public OrganisationRepository(DSLContext connection) {
        this.connection = connection;
    }

    public List<Organisation> list(String name, String town, String county,
                                               String country, String postCode) {

        SelectJoinStep from = connection.select(ORGANISATION.fields()).from(ORGANISATION);

        if (town != null || county != null || country != null || postCode != null) {
            from = from.join(ADDRESS).on(ADDRESS.ORGANISATION_ID.eq(ORGANISATION.ID));
        }

        SelectConditionStep where = from.where(ORGANISATION.ID.isNotNull());
        if (town != null) {
            where = where.and(ADDRESS.TOWN.eq(town));
        }
        if (county != null) {
            where = where.and(ADDRESS.COUNTY.eq(county));
        }
        if (country != null) {
            where = where.and(ADDRESS.COUNTRY.eq(country));
        }
        if (postCode != null) {
            where = where.and(ADDRESS.POSTCODE.eq(postCode.replaceAll(" ", "").toUpperCase()));
        }
        if (name != null) {
            where = where.and(ORGANISATION.NAME.eq(name));
        }

        return where.fetchInto(Organisation.class);
    }

    public Organisation get(String id) {
        return connection.selectFrom(ORGANISATION).where(ORGANISATION.ID.eq(id)).fetchOneInto(Organisation.class);
    }

    public String add(Organisation organisation) {
        try {
            OrganisationRecord record = new OrganisationRecord();
            record.setId(UUID.randomUUID().toString());
            record.setName(organisation.getName());
            record.setDescription(organisation.getDescription());
            record.setOrganisationType(organisation.getOrganisationType());
            record.setWebsiteUrl(organisation.getWebsiteUrl());
            connection.newRecord(ORGANISATION, record).store();
            return record.getId();
        } catch (Exception e) {
            logger.error("Failed to add Organisation " + organisation, e);
            throw new RuntimeException("Failed to add Organisation " + organisation, e);
        }
    }

    public void update(Organisation organisation) {
        try {
            connection.update(ORGANISATION)
                    .set(ORGANISATION.NAME, organisation.getName())
                    .set(ORGANISATION.DESCRIPTION, organisation.getDescription())
                    .set(ORGANISATION.ORGANISATION_TYPE, organisation.getOrganisationType())
                    .set(ORGANISATION.WEBSITE_URL, organisation.getWebsiteUrl())
                    .where(ORGANISATION.ID.eq(organisation.getId()));
        } catch (Exception e) {
            logger.error("Failed to update Organisation " + organisation, e);
            throw new RuntimeException("Failed to update Organisation " + organisation, e);
        }

    }

    public void delete(String id) {
        connection.deleteFrom(ORGANISATION).where(ORGANISATION.ID.eq(id));
    }
}
