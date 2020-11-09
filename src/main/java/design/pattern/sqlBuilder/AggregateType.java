package design.pattern.sqlBuilder;

/**
 * SQL select 语句中，对部分字段可以进行聚合
 */
public enum AggregateType {
    // 朴素字段，不含有任何聚合
    PLAIN(""),
    // sum(field), 对字段进行求和
    SUM("sum"),
    // 对字段进行计数统计
    COUNT("count"),
    // 对字段值求平均
    AVG("avg"),
    // 对字段值求最大值
    MAX("max"),
    // 对字段值求最小值
    MIN("min"),;

    // SQL聚合函数
    private final String aggregateFunction;

    /**
     * 初始化，并且设置聚合函数
     * @param aggregateFunction
     */
    AggregateType(String aggregateFunction) {
        this.aggregateFunction = aggregateFunction;
    }

    /**
     * 获得对应的聚合函数
     * @return
     */
    public String getAggregateFunction() {
        return aggregateFunction;
    }
}
