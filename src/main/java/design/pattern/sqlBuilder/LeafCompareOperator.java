package design.pattern.sqlBuilder;

public enum LeafCompareOperator {
    // refer to https://docs.tibco.com/pub/amx-bpm/4.3.0/doc/html/bpmhelp/GUID-C5D8843C-F16D-4F2C-A7E4-1FB9EEF6CDC6.html
    EQUAL, // EQ
    NOT_EQUAL, // NEQ
    GREATER_THAN, // GT
    GREATER_THAN_EQUAL, // GTE
    LOWER_THAN, // LT
    LOWER_THAN_EQUAL, // LTE
    // IN, 值在一个列表中, 暂时不支持
}
