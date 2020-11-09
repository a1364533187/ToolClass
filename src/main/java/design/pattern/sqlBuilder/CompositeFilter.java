package design.pattern.sqlBuilder;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

/**
 * @author nishun <nishun@kuaishou.com>
 * Created on 2020-09-17
 */
public class CompositeFilter extends Filter {

    // 若干 Filter 列表
    private Collection<Filter> filters;

    // 多个 Filter 组合
    private CompositeCompareOpeartor compareOperator; // 组合逻辑判断

    CompositeFilter(Collection<Filter> filters, CompositeCompareOpeartor compareOperator) {
        this.filters = filters;
        this.compareOperator = compareOperator;
    }

    @Override
    public String toSql() {
        if (CollectionUtils.isEmpty(filters)) {
            return "";
        }

        return filters.stream().map(e -> String.format("(%s)", e.toSql()))
                .collect(Collectors.joining(String.format(" %s ", compareOperatorToSqlString())));
    }

    /**
     * compareOperator to sql string
     */
    private String compareOperatorToSqlString() {
        String op = "";
        switch (compareOperator) {
            case AND:
                op = "AND";
                break;
            case OR:
                op = "OR";
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("unsupported compareOperator: [%s]", compareOperator));
        }
        return op;
    }
}
