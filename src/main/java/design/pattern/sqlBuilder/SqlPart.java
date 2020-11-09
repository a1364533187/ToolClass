package design.pattern.sqlBuilder;

public interface SqlPart {

    // 转为SQL语句的部分成分
    String toSql();
}
