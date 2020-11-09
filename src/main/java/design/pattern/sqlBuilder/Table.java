package design.pattern.sqlBuilder;

public class Table implements SqlPart {

    private String apiName;

    /**
     * 不允许外部用户使用
     * @param apiName
     */
    Table(String apiName) {
        this.apiName = apiName;
    }

    public static Table getTableByApiName(String apiName) {
        return new Table(apiName);
    }

    /**
     * 在客户端侧暂时无法获得真实的 table 名称，先使用虚拟表占位符 (TABLE.[apiName]) 的形式去表示
     * @return
     */
    public String getTableName() {
        return "TABLE." + apiName;
    }

    /**
     * 获得虚拟表对应的 apiName
     * @return
     */
    public String getApiName() {
        return apiName;
    }

    @Override
    public String toSql() {
        return getTableName();
    }
}
