package spssoftware.opencare.repository;

import com.google.common.base.CaseFormat;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spssoftware.opencare.domain.Organisation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static spssoftware.generated.tables.Organisation.ORGANISATION;

@Component
public class OrganisationRepository extends AbstractRepository {

    private DSLContext connection;

    @Autowired
    public OrganisationRepository(DSLContext connection) {
        this.connection = connection;
    }

    public List<Organisation> find(List<String> fields, Map<String, List<String>> constraints) {

        Field[] select = ORGANISATION.fields();

        Set<Field> selectedFields = new HashSet<>();
        if (fields != null) {
            for (String field : fields) {

                String formattedField = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, field);
                for (Field dbField : ORGANISATION.fields()) {

                    if (dbField.getName().startsWith(formattedField)) {
                        selectedFields.add(dbField);
                    }
                }
            }

            if (!selectedFields.isEmpty()) {
                selectedFields.add(ORGANISATION.ORGANISATION_ID);
                select = selectedFields.toArray(new Field[selectedFields.size()]);
            }
        }

        SelectConditionStep<Record> where = connection
            .select(select).from(ORGANISATION).where(ORGANISATION.ORGANISATION_ID.isNotNull());

        if (constraints != null) {
            for (String criteria : constraints.keySet()) {

                String formattedField = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, criteria).replaceAll("\\.", "_");

                if (ORGANISATION.field(formattedField) instanceof TableField) {

                    TableField field = (TableField) ORGANISATION.field(formattedField);

                    if (field.getDataType().isString()) {
                        appendStringClauses(field, constraints.get(criteria), where);

                    } else if (field.getDataType().isDateTime()) {
                        appendTimestampClauses(field, constraints.get(criteria), where);

                    } else if (field.getDataType().isNumeric() && field.getDataType().hasPrecision()) {
                        appendBigDecimalClauses(field, constraints.get(criteria), where);

                    } else if (field.getDataType().isNumeric()) {
                        appendLongClauses(field, constraints.get(criteria), where);
                    }
                }
            }
        }

        List<Organisation> stockItems = where.fetchInto(Organisation.class);

        return stockItems;
    }

    public Organisation get(String id) {

        Organisation organisation = connection.selectFrom(ORGANISATION).where(ORGANISATION.ORGANISATION_ID.eq(id)).fetchOneInto(Organisation.class);

//        stockItem.setFeatures(connection.selectFrom(FEATURE).where(FEATURE.STOCK_ITEM_ID.eq(id)).orderBy(FEATURE.ORDINAL).fetchInto(Feature.class));
//        stockItem.setMedia(connection.selectFrom(MEDIA).where(MEDIA.STOCK_ITEM_ID.eq(id)).orderBy(MEDIA.ORDINAL).fetchInto(Media.class));

        return organisation;
    }

    public Organisation save(Organisation organisation) {

        return null;
    }

    public void delete(String id) {

    }


}
