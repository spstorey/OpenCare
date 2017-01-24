package spssoftware.opencare.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;

public enum ValueOperator {
    EQUALS(""),
    NOT_EQUALS("!"),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUALS("<="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUALS(">="),
    IS_NULL("NULL"),
    IS_NOT_NULL("!NULL");

    String prefix;

    ValueOperator(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public static ValueOperator getValueOperator(String value) {

        if (value.equalsIgnoreCase(IS_NOT_NULL.getPrefix())) {
            return ValueOperator.IS_NOT_NULL;
        } else if (value.equalsIgnoreCase(IS_NULL.getPrefix())) {
            return ValueOperator.IS_NULL;
        } else if (value.startsWith(GREATER_THAN_OR_EQUALS.getPrefix())) {
            return ValueOperator.GREATER_THAN_OR_EQUALS;
        } else if (value.startsWith(GREATER_THAN.getPrefix())) {
            return ValueOperator.GREATER_THAN;
        } else if (value.startsWith(LESS_THAN_OR_EQUALS.getPrefix())) {
            return ValueOperator.LESS_THAN_OR_EQUALS;
        } else if (value.startsWith(LESS_THAN.getPrefix())) {
            return ValueOperator.LESS_THAN;
        } else if (value.startsWith(NOT_EQUALS.getPrefix())) {
            return ValueOperator.NOT_EQUALS;
        } else {
            return ValueOperator.EQUALS;
        }
    }

    public static String getValueAsString(String value) {
        ValueOperator op = getValueOperator(value);
        return value.replace(op.prefix, "");
    }

    public static Timestamp getValueAsTimestamp(String value) {
        ValueOperator op = getValueOperator(value);
        String actualValue = value.replace(op.prefix, "");
        return new Timestamp(Long.valueOf(actualValue));
    }

    public static BigDecimal getValueAsBigDecimal(String value) {
        ValueOperator op = getValueOperator(value);
        String actualValue = value.replace(op.prefix, "");
        return new BigDecimal(actualValue);
    }

    public static Long getValueAsLong(String value) {
        ValueOperator op = getValueOperator(value);
        String actualValue = value.replace(op.prefix, "");
        return Long.valueOf(actualValue);
    }
}
