package spssoftware.opencare.repository;


import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.TableField;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class AbstractRepository {

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
