package com.bigcow.com.jooq;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.junit.Test;

public class jooq {

    @Test
    public void jooqDsl() throws ClassNotFoundException, SQLException {
        Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
        try (Connection connection = DriverManager
                .getConnection("jdbc:clickhouse://proxy.clickhouse.internal:18081", "dp", "")) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            // Fetch a SQL string from a jOOQ Query in order to manually execute it with another tool.
            // For simplicity reasons, we're using the API to construct case-insensitive object references, here.
            //dp_data_factory.sub_dw_app_ptc_dc_stat_web_service_di_v2  clickhouse 表
            Query query = create.select(field("user_id"), field("ac_id"), field("ac_type"))
                    .from(table("dp_data_factory.sub_dw_app_ptc_dc_stat_web_service_di_v2"))
                    .where(field("p_date").eq("20200328")).limit(inline(3)).offset(7);

            // your inlined bind values will be properly escaped to avoid SQL syntax errors and SQL injection.
            String sql = query.getSQL(ParamType.INLINED);

            //通过 Record.into 方法可以将默认Record对象，转换为表的Record对象
            Result<?> results = ((SelectConditionStep) query).fetch();
            System.out.println("--->" + results);
            for (Record record : results) {
                Long id = (Long) record.getValue("user_id"); //获取所需的结果集
                System.out.println("id: " + id);
            }

            // fetchInto方法可以可以传入任意class类型，或者表常量
            // 会直接返回任意class类型的List集合，或者指定表Record的结果集对象
            List<ReturnValue> results1 = ((SelectConditionStep) query).fetchInto(ReturnValue.class);
            System.out.println(results1);
            System.out.println("sql: " + sql);
        }
    }

}

class ReturnValue {

    private Integer userId;
    private String acId;
    private String acType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAcId() {
        return acId;
    }

    public void setAcId(String acId) {
        this.acId = acId;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    @Override
    public String toString() {
        return "ReturnValue{" + "userId=" + userId + ", acId='" + acId + '\'' + ", acType='"
                + acType + '\'' + '}';
    }
}
