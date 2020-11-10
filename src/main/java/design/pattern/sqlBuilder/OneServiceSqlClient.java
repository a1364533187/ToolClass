package design.pattern.sqlBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

public class OneServiceSqlClient {

    // 字段名称规则
    private static final String FIELD_NAME_PATTERN_STRING = "^[A-Za-z._\\d]+$";
    private static final Pattern FIELD_NAME_PATTERN = Pattern.compile(FIELD_NAME_PATTERN_STRING);

    // offset & limit 参数上限
    private static final int OFFSET_UPPER_BOUND = 10000;
    private static final int LIMIT_UPPER_BOUND = 1000;

    private static Collection<Field> selectFields;
    private static Table table;
    private static Filter filter;
    private static Collection<Field> groupByFields;
    private static Collection<OrderBy> orderBys;
    private static Integer offset;
    private static Integer limit;
    private String sql;

    public String getSql() {
        return sql;
    }

    private void setSql(String sql) {
        this.sql = sql;
    }

    private static void setSelectFields(Collection<Field> selectFields) {
        OneServiceSqlClient.selectFields = selectFields;
    }

    private static void setTable(Table table) {
        OneServiceSqlClient.table = table;
    }

    private static void setFilter(Filter filter) {
        OneServiceSqlClient.filter = filter;
    }

    private static void setGroupByFields(Collection<Field> groupByFields) {
        OneServiceSqlClient.groupByFields = groupByFields;
    }

    private static void setOrderBys(Collection<OrderBy> orderBys) {
        OneServiceSqlClient.orderBys = orderBys;
    }

    private static void setOffset(Integer offset) {
        OneServiceSqlClient.offset = offset;
    }

    private static void setLimit(Integer limit) {
        OneServiceSqlClient.limit = limit;
    }

    public static SelectSection select(Field... fields) {
        setSelectFields(Arrays.asList(fields));
        return new SelectSection();
    }

    public static final class SelectSection {

        /**
         * 查询的虚拟表对象
         *
         * @param virtualTable 客户端侧根据 apiName 拿到虚拟表
         */
        public FromSection from(Table virtualTable) {
            setTable(virtualTable);
            return new FromSection();
        }
    }

    public static final class FromSection {

        public WhereSection where(Filter filter) {
            setFilter(filter);
            return new WhereSection();
        }
    }

    public static final class WhereSection {

        public GroupBySection groupby(Field... groupByFields) {
            setGroupByFields(Arrays.asList(groupByFields));
            return new GroupBySection();
        }
    }

    public static final class GroupBySection {

        public OrderBySection orderby(OrderBy... orderBys) {
            setOrderBys(Arrays.asList(orderBys));
            return new OrderBySection();
        }
    }

    public static final class OrderBySection {

        public OffsetSection offset(Integer offset) {
            setOffset(offset);
            return new OffsetSection();
        }
    }

    public static final class OffsetSection {

        public LimitSection limit(Integer offset) {
            setLimit(offset);
            return new LimitSection();
        }
    }

    public static final class LimitSection implements SqlPart {

        public OneServiceSqlClient fetch() {
            OneServiceSqlClient sqlClient = new OneServiceSqlClient();
            //set selectFields table ....
            sqlClient.setSql(toSql());
            return sqlClient;
        }

        @Override
        public String toSql() {
            Preconditions.checkArgument(selectFields != null && selectFields.size() > 0,
                    "select fields cannot be empty");
            Preconditions.checkArgument(table != null, "virtualTable cannot be null");
            Preconditions.checkArgument(filter != null, "filter cannot be null");

            // 所有的 SQL 查询都必须含有如下基础结构
            String sql = String.format("SELECT %s FROM %s WHERE %s",
                    selectFields.stream().map(Field::toSql).collect(Collectors.joining(", ")),
                    table.toSql(), filter.toSql());

            // 如果有 groupBy 成分
            if (groupByFields != null && groupByFields.size() > 0) {
                sql += String.format(" GROUP BY %s",
                        groupByFields.stream().map(Field::toSql).collect(Collectors.joining(", ")));
            }

            // 如果设置了 orderBy
            if (orderBys != null && orderBys.size() > 0) {
                sql += String.format(" ORDER BY %s",
                        orderBys.stream().map(OrderBy::toSql).collect(Collectors.joining(", ")));
            }

            // 如果设置了需要 limit
            if (limit != null) {
                // 如果设置需要 offset
                if (offset != null) {
                    // Mysql dialect
                    sql += String.format(" LIMIT %s, %s", offset, limit);
                } else {
                    sql += String.format(" LIMIT %s", limit);
                }
            }

            return sql;
        }
    }

}
