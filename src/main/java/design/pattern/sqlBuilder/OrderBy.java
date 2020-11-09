package design.pattern.sqlBuilder;

import com.google.common.base.Preconditions;

public class OrderBy implements SqlPart {

    // 排序
    private Field field; // 排序字段，包括朴素字段，以及聚合字段
    private OrderByType orderByType; // 排序方向

    public OrderBy(Field field, OrderByType orderByType) {
        Preconditions.checkArgument(field.getAggregateType() == AggregateType.PLAIN,
                "field type should be plain, and cannot be aggregation type");
        this.field = field;
        this.orderByType = orderByType;
    }

    public Field getField() {
        return field;
    }

    public OrderByType getOrderByType() {
        return orderByType;
    }

    /**
     * 按字段升序
     *
     * @param field 朴素字段，或者聚合字段
     * @return 排序算子
     */
    public static OrderBy asc(Field field) {
        return new OrderBy(field, OrderByType.ASC);
    }

    /**
     * 按字段降序
     *
     * @param field 朴素字段，或者聚合字段
     * @return 排序算子
     */
    public static OrderBy desc(Field field) {
        return new OrderBy(field, OrderByType.DESC);
    }

    @Override
    public String toSql() {
        return String.format("%s %s", field.toSql(), orderByType);
    }
}
