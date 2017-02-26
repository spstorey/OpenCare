package spssoftware.opencare.repository;


import com.google.common.base.CaseFormat;
import org.jooq.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AbstractRepository<T, U extends UpdatableRecord> {

    DSLContext connection;
    Table<U> TABLE;
    Field ID;
    Class<T> objectClass;

    public List<T> find(List<String> fields, Map<String, List<String>> constraints) {

        Field[] select = TABLE.fields();

        Set<Field> selectedFields = new HashSet<>();
        if (fields != null) {
            for (String field : fields) {

                String formattedField = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, field);
                for (Field dbField : TABLE.fields()) {

                    if (dbField.getName().startsWith(formattedField)) {
                        selectedFields.add(dbField);
                    }
                }
            }

            if (!selectedFields.isEmpty()) {
                selectedFields.add(ID);
                select = selectedFields.toArray(new Field[selectedFields.size()]);
            }
        }

        SelectConditionStep<Record> where = connection
                .select(select).from(TABLE).where(ID.isNotNull());

        if (constraints != null) {
            for (String criteria : constraints.keySet()) {

                String formattedField = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, criteria).replaceAll("\\.", "_");

                if (TABLE.field(formattedField) instanceof TableField) {

                    TableField field = (TableField) TABLE.field(formattedField);

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

        List<T> results = where.fetchInto(objectClass);

        return results;
    }

    public T get(String id) {

        T object = connection.selectFrom(TABLE).where(ID.eq(id)).fetchOneInto(objectClass);
        return object;
    }

    public U getRecord(String id) {

        return connection.selectFrom(TABLE).where(ID.eq(id)).fetchOne();
    }

    public T save(String id, T object) {

        U originalObject = getRecord(id);
        BeanUtils.copyProperties(object, originalObject);
        originalObject.store();

        return get(id);
    }

    public void delete(String id) {

        int deleteCount = connection.deleteFrom(TABLE).where(ID.eq(id)).execute();
        if (deleteCount == 0) {
            throw new RuntimeException("Organisation [id="+id+"] was NOT deleted");
        }
    }

    SelectConditionStep<Record> appendStringClauses(TableField<?, String> field, List<String> values, SelectConditionStep<Record> where) {

        if (values != null) {
            for (String value : values) {

                if (value != null) {
                    ValueOperator valueOperator = ValueOperator.getValueOperator(value);
                    String actualValue = ValueOperator.getValueAsString(value);

                    if (ValueOperator.IS_NULL.equals(valueOperator)) {
                        where = where.and(field.isNull());
                    } else if (ValueOperator.IS_NOT_NULL.equals(valueOperator)) {
                        where = where.and(field.isNotNull());
                    } else if (ValueOperator.LESS_THAN.equals(valueOperator)) {
                        where = where.and(field.lessThan(actualValue));
                    } else if (ValueOperator.LESS_THAN_OR_EQUALS.equals(valueOperator)) {
                        where = where.and(field.lessOrEqual(actualValue));
                    } else if (ValueOperator.GREATER_THAN.equals(valueOperator)) {
                        where = where.and(field.greaterThan(actualValue));
                    } else if (ValueOperator.GREATER_THAN_OR_EQUALS.equals(valueOperator)) {
                        where = where.and(field.greaterOrEqual(actualValue));
                    } else if (ValueOperator.NOT_EQUALS.equals(valueOperator)) {
                        where = where.and(field.notEqual(actualValue));
                    } else if (ValueOperator.EQUALS.equals(valueOperator)) {
                        where = where.and(field.equal(actualValue));
                    }
                }
            }
        }

        return where;
    }

    SelectConditionStep<Record> appendTimestampClauses(TableField<?, Timestamp> field, List<String> values, SelectConditionStep<Record> where) {

        if (values != null) {
            for (String value : values) {

                if (value != null) {
                    ValueOperator valueOperator = ValueOperator.getValueOperator(value);
                    Timestamp actualValue = ValueOperator.getValueAsTimestamp(value);

                    if (ValueOperator.IS_NULL.equals(valueOperator)) {
                        where = where.and(field.isNull());
                    } else if (ValueOperator.IS_NOT_NULL.equals(valueOperator)) {
                        where = where.and(field.isNotNull());
                    } else if (ValueOperator.LESS_THAN.equals(valueOperator)) {
                        where = where.and(field.lessThan(actualValue));
                    } else if (ValueOperator.LESS_THAN_OR_EQUALS.equals(valueOperator)) {
                        where = where.and(field.lessOrEqual(actualValue));
                    } else if (ValueOperator.GREATER_THAN.equals(valueOperator)) {
                        where = where.and(field.greaterThan(actualValue));
                    } else if (ValueOperator.GREATER_THAN_OR_EQUALS.equals(valueOperator)) {
                        where = where.and(field.greaterOrEqual(actualValue));
                    } else if (ValueOperator.NOT_EQUALS.equals(valueOperator)) {
                        where = where.and(field.notEqual(actualValue));
                    } else if (ValueOperator.EQUALS.equals(valueOperator)) {
                        where = where.and(field.equal(actualValue));
                    }
                }
            }
        }

        return where;
    }

    SelectConditionStep<Record> appendLongClauses(TableField<?, Long> field, List<String> values, SelectConditionStep<Record> where) {

        if (values != null) {
            for (String value : values) {

                if (value != null) {
                    ValueOperator valueOperator = ValueOperator.getValueOperator(value);
                    Long actualValue = ValueOperator.getValueAsLong(value);

                    if (ValueOperator.IS_NULL.equals(valueOperator)) {
                        where = where.and(field.isNull());
                    } else if (ValueOperator.IS_NOT_NULL.equals(valueOperator)) {
                        where = where.and(field.isNotNull());
                    } else if (ValueOperator.LESS_THAN.equals(valueOperator)) {
                        where = where.and(field.lessThan(actualValue));
                    } else if (ValueOperator.LESS_THAN_OR_EQUALS.equals(valueOperator)) {
                        where = where.and(field.lessOrEqual(actualValue));
                    } else if (ValueOperator.GREATER_THAN.equals(valueOperator)) {
                        where = where.and(field.greaterThan(actualValue));
                    } else if (ValueOperator.GREATER_THAN_OR_EQUALS.equals(valueOperator)) {
                        where = where.and(field.greaterOrEqual(actualValue));
                    } else if (ValueOperator.NOT_EQUALS.equals(valueOperator)) {
                        where = where.and(field.notEqual(actualValue));
                    } else if (ValueOperator.EQUALS.equals(valueOperator)) {
                        where = where.and(field.equal(actualValue));
                    }
                }
            }
        }

        return where;
    }

    SelectConditionStep<Record> appendBigDecimalClauses(TableField<?, BigDecimal> field, List<String> values, SelectConditionStep<Record> where) {

        if (values != null) {
            for (String value : values) {

                if (value != null) {
                    ValueOperator valueOperator = ValueOperator.getValueOperator(value);
                    BigDecimal actualValue = ValueOperator.getValueAsBigDecimal(value);

                    if (ValueOperator.IS_NULL.equals(valueOperator)) {
                        where = where.and(field.isNull());
                    } else if (ValueOperator.IS_NOT_NULL.equals(valueOperator)) {
                        where = where.and(field.isNotNull());
                    } else if (ValueOperator.LESS_THAN.equals(valueOperator)) {
                        where = where.and(field.lessThan(actualValue));
                    } else if (ValueOperator.LESS_THAN_OR_EQUALS.equals(valueOperator)) {
                        where = where.and(field.lessOrEqual(actualValue));
                    } else if (ValueOperator.GREATER_THAN.equals(valueOperator)) {
                        where = where.and(field.greaterThan(actualValue));
                    } else if (ValueOperator.GREATER_THAN_OR_EQUALS.equals(valueOperator)) {
                        where = where.and(field.greaterOrEqual(actualValue));
                    } else if (ValueOperator.NOT_EQUALS.equals(valueOperator)) {
                        where = where.and(field.notEqual(actualValue));
                    } else if (ValueOperator.EQUALS.equals(valueOperator)) {
                        where = where.and(field.equal(actualValue));
                    }
                }
            }
        }

        return where;
    }
}
