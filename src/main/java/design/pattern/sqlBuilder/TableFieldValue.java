package design.pattern.sqlBuilder;

import com.google.common.base.MoreObjects;

public class TableFieldValue {

    private TableDataType fieldType; // 字段类型，基础数据类型
    private String fieldValue; // 字段值

    public TableDataType getFieldType() {
        return fieldType;
    }

    public TableFieldValue(int v) {
        fieldType = TableDataType.INTEGER;
        fieldValue = Integer.toString(v);
    }

    public int getFieldValueAsInteger() {
        return Integer.parseInt(fieldValue);
    }

    public TableFieldValue(long v) {
        fieldType = TableDataType.LONG;
        fieldValue = Long.toString(v);
    }

    public long getFieldValueAsLong() {
        return Long.parseLong(fieldValue);
    }

    public TableFieldValue(double v) {
        fieldType = TableDataType.DOUBLE;
        fieldValue = Double.toString(v);
    }

    public double getFieldValueAsDouble() {
        return Double.parseDouble(fieldValue);
    }

    public TableFieldValue(float v) {
        fieldType = TableDataType.FLOAT;
        fieldValue = Float.toString(v);
    }

    public float getFieldValueAsFloat() {
        return Float.parseFloat(fieldValue);
    }

    public TableFieldValue(String v) {
        fieldType = TableDataType.STRING;
        fieldValue = v;
    }

    public String getFieldValueAsString() {
        return fieldValue;
    }

    public TableFieldValue(boolean v) {
        fieldType = TableDataType.BOOLEAN;
        fieldValue = Boolean.toString(v);
    }

    public boolean getFieldValueAsBoolean() {
        return Boolean.parseBoolean(fieldValue);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("fieldType", fieldType)
                .add("fieldValue", fieldValue).toString();
    }
}
