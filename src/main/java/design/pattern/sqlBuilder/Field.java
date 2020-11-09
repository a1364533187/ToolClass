package design.pattern.sqlBuilder;

import java.util.Objects;

/**
 * 构造SQL语句中的 select 字段，有 2 类
 * - select field       // 直接select字段
 * - select sum(field)  // select聚合字段，如求和
 */
public class Field implements SqlPart {

    private String fieldName; // 字段名称
    private AggregateType aggregateType; // 聚合类型

    /**
     * 构造原始的 field 字段 （无聚合）
     *
     * @param fieldName 字段名称，比如 video_clicks
     * @return Field，构造出的 field 字段
     */
    public static Field field(String fieldName) {
        return new Field(fieldName, AggregateType.PLAIN);
    }

    /**
     * 构造 field 字段 （SUM 聚合）
     *
     * @param field 字段，比如 field('video_clicks')
     * @return Field，构造出新的 field 字段 （SUM类型）
     */
    public static Field sum(Field field) {
        // 不要修改传入的参数，因为可能其他地方还在使用 field，因此需要重新构造一个 field对象
        return new Field(field.fieldName, AggregateType.SUM);
    }

    /**
     * 构造 field 字段 （COUNT 聚合）, usage example: count(field('video_clicks'))
     *
     * @param field 字段，比如 field('video_clicks')
     * @return Field，构造出新的 field 字段 （COUNT类型）
     */
    public static Field count(Field field) {
        return new Field(field.fieldName, AggregateType.COUNT);
    }

    /**
     * 构造 field 字段 （AVG 聚合）, usage example: avg(field('video_clicks'))
     *
     * @param field 字段，比如 field('video_clicks')
     * @return Field，构造出新的 field 字段 （AVG类型）
     */
    public static Field avg(Field field) {
        return new Field(field.fieldName, AggregateType.AVG);
    }

    /**
     * 构造 field 字段 （MAX 聚合）, usage example: max(field('video_clicks'))
     *
     * @param field 字段，比如 field('video_clicks')
     * @return Field，构造出新的 field 字段 （MAX类型）
     */
    public static Field max(Field field) {
        return new Field(field.fieldName, AggregateType.MAX);
    }

    /**
     * 构造 field 字段 （MAX 聚合）, usage example: max(field('video_clicks'))
     *
     * @param field 字段，比如 field('video_clicks')
     * @return Field，构造出新的 field 字段 （MAX类型）
     */
    public static Field min(Field field) {
        return new Field(field.fieldName, AggregateType.MIN);
    }

    private Field(String fieldName, AggregateType aggregateType) {
        this.fieldName = fieldName;
        this.aggregateType = aggregateType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public AggregateType getAggregateType() {
        return aggregateType;
    }

    @Override
    public String toSql() {
        String aggFunc = aggregateType.getAggregateFunction();
        return String.format("%s(%s)", aggFunc, fieldName);
    }

    /**
     * 构造过滤条件：一个字段等于给定值
     *
     * @param tableFieldValue 目标值
     * @return 过滤算子
     */
    public Filter equal(TableFieldValue tableFieldValue) {
        return new LeafFilter(fieldName, tableFieldValue, LeafCompareOperator.EQUAL);
    }

    /**
     * 构造过滤条件：一个字段不等于给定值
     *
     * @param tableFieldValue 目标值
     * @return 过滤算子
     */
    public Filter notEqual(TableFieldValue tableFieldValue) {
        return new LeafFilter(fieldName, tableFieldValue, LeafCompareOperator.NOT_EQUAL);
    }

    /**
     * 构造过滤条件：一个字段大于给定值
     *
     * @param tableFieldValue 目标值
     * @return 过滤算子
     */
    public Filter greaterThan(TableFieldValue tableFieldValue) {
        return new LeafFilter(fieldName, tableFieldValue, LeafCompareOperator.GREATER_THAN);
    }

    /**
     * 构造过滤条件：一个字段大于等于给定值
     *
     * @param tableFieldValue 目标值
     * @return 过滤算子
     */
    public Filter greaterThanEqual(TableFieldValue tableFieldValue) {
        return new LeafFilter(fieldName, tableFieldValue, LeafCompareOperator.GREATER_THAN_EQUAL);
    }

    /**
     * 构造过滤条件：一个字段小于给定值
     *
     * @param tableFieldValue 目标值
     * @return 过滤算子
     */
    public Filter lowerThan(TableFieldValue tableFieldValue) {
        return new LeafFilter(fieldName, tableFieldValue, LeafCompareOperator.LOWER_THAN);
    }

    /**
     * 构造过滤条件：一个字段小于等于给定值
     *
     * @param tableFieldValue 目标值
     * @return 过滤算子
     */
    public Filter lowerThanEqual(TableFieldValue tableFieldValue) {
        return new LeafFilter(fieldName, tableFieldValue, LeafCompareOperator.LOWER_THAN_EQUAL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Field field = (Field) o;
        return fieldName.equals(field.fieldName) && aggregateType == field.aggregateType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, aggregateType);
    }
}
