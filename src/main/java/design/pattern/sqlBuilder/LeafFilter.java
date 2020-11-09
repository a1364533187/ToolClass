package design.pattern.sqlBuilder;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;

public class LeafFilter extends Filter {

    private static final Escaper ESCAPER = Escapers.builder()
            .addEscape('\\', "\\\\")
            .addEscape('\n', "\\n")
            .addEscape('\t', "\\t")
            .addEscape('\b', "\\b")
            .addEscape('\f', "\\f")
            .addEscape('\r', "\\r")
            .addEscape('\0', "\\0")
            .addEscape('\'', "\\'")
            .addEscape('`', "\\`")
            .build();

    private String fieldName;
    private TableFieldValue fieldValue;
    private LeafCompareOperator compareOperator;

    public LeafFilter(String fieldName, TableFieldValue fieldValue, LeafCompareOperator compareOperator) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.compareOperator = compareOperator;
    }

    @Override
    public String toSql() {
        String val = valToSqlString();
        String compareStr = compareOperatorToSqlString();
        String where = String.format("%s %s %s", fieldName, compareStr, val);
        return where;
    }

    private String valToSqlString() {
        String val = "";
        switch (fieldValue.getFieldType()) {
            case STRING:
                val = escape(fieldValue.getFieldValueAsString());
                val = String.format("'%s'", val);
                break;
            case BOOLEAN:
                // do not need escape
                val = fieldValue.getFieldValueAsString();
                val = String.format("'%s'", val);
                break;
            case INTEGER:
            case LONG:
            case DOUBLE:
            case FLOAT:
                // 针对各种类型，都直接拿到 string 形式的值
                val = fieldValue.getFieldValueAsString();
                break;
            default:
                throw new IllegalArgumentException(String.format("unsupported field type: [%s]", fieldValue));
        }
        return val;
    }

    /**
     * compareOperator to sql string
     * @return
     */
    private String compareOperatorToSqlString() {
        String op = "";
        switch (compareOperator) {
            case EQUAL:
                op = "=";
                break;
            case NOT_EQUAL:
                op = "!=";
                break;
            case GREATER_THAN_EQUAL:
                op = ">=";
                break;
            case GREATER_THAN:
                op = ">";
                break;
            case LOWER_THAN_EQUAL:
                op = "<=";
                break;
            case LOWER_THAN:
                op = "<";
                break;
            default:
                throw new IllegalArgumentException(String.format("unsupported compareOperator: [%s]", compareOperator));
        }
        return op;
    }


    private String escape(String s) {
        if (s == null) {
            return "\\N";
        }
        return ESCAPER.escape(s);
    }

    private String quoteIdentifier(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Can't quote null as identifier");
        }
        StringBuilder sb = new StringBuilder(s.length() + 2);
        sb.append('`');
        sb.append(ESCAPER.escape(s));
        sb.append('`');
        return sb.toString();
    }

}

