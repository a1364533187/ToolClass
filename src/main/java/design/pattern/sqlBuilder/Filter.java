package design.pattern.sqlBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class Filter implements SqlPart {

    /**
     * 以 AND 的方式组合多个 Filter 算子 （包括当前Filter对象）
     *
     * @param filters 除当前Filter对象外的其他 Filter 过滤条件
     * @return 组合过滤条件
     */
    public CompositeFilter and(Filter... filters) {
        return new CompositeFilter(mergeFilters(filters), CompositeCompareOpeartor.AND);
    }

    /**
     * 以 OR 的方式组合多个 Filter 算子 （包括当前Filter对象）
     *
     * @param filters 除当前Filter对象外的其他 Filter 过滤条件
     * @return 组合过滤条件
     */
    public CompositeFilter or(Filter... filters) {
        return new CompositeFilter(mergeFilters(filters), CompositeCompareOpeartor.OR);
    }

    private Collection<Filter> mergeFilters(Filter... filters) {
        List<Filter> mergedFilters = new ArrayList<>();
        mergedFilters.add(this);
        mergedFilters.addAll(Arrays.asList(filters));
        return mergedFilters;
    }
}
