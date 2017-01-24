package spssoftware.opencare.repository;

import com.google.common.base.CaseFormat;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spssoftware.opencare.domain.Staff;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static spssoftware.generated.tables.Staff.STAFF;

@Component
public class StaffRepository extends AbstractRepository {

    private DSLContext connection;

    @Autowired
    public StaffRepository(DSLContext connection) {
        this.connection = connection;
    }

    public List<Staff> find(List<String> fields, Map<String, List<String>> constraints) {

        Field[] select = STAFF.fields();

        Set<Field> selectedFields = new HashSet<>();
        if (fields != null) {
            for (String field : fields) {

                String formattedField = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, field);
                for (Field dbField : STAFF.fields()) {

                    if (dbField.getName().startsWith(formattedField)) {
                        selectedFields.add(dbField);
                    }
                }
            }

            if (!selectedFields.isEmpty()) {

                selectedFields.add(STAFF.STAFF_ID);
                select = selectedFields.toArray(new Field[selectedFields.size()]);
            }
        }

        SelectConditionStep<Record> where = connection
            .select(select).from(STAFF).where(STAFF.STAFF_ID.isNotNull());


        for (String criteria : constraints.keySet()) {

            String formattedField = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, criteria).replaceAll("\\.", "_");

            if (STAFF.field(formattedField) instanceof TableField) {

                TableField field = (TableField) STAFF.field(formattedField);

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

        List<Staff> stockItems = where.fetchInto(Staff.class);

        return stockItems;
    }

    public Staff get(String id) {

        Staff organisation = connection.selectFrom(STAFF).where(STAFF.STAFF_ID.eq(id)).fetchOneInto(Staff.class);

//        stockItem.setFeatures(connection.selectFrom(FEATURE).where(FEATURE.STOCK_ITEM_ID.eq(id)).orderBy(FEATURE.ORDINAL).fetchInto(Feature.class));
//        stockItem.setMedia(connection.selectFrom(MEDIA).where(MEDIA.STOCK_ITEM_ID.eq(id)).orderBy(MEDIA.ORDINAL).fetchInto(Media.class));

        return organisation;
    }

    public Staff save(Staff organisation) {

        return null;
    }

    public void delete(String id) {

    }


}
