package design.pattern.sqlBuilder;

import org.junit.Test;

/**
 * Create by suzhiwu on 2020/11/8
 */
public class SqlBuilderTest {

    @Test
    public void test1() {
        OneServiceSqlClient sqlClient = OneServiceSqlClient
                .select(Field.field("haha"))
                .from(Table.getTableByApiName("api1"))
                .where(Field.field("p_date")
                        .greaterThanEqual(new TableFieldValue("20201012"))
                .and(Field.field("p_date").lowerThanEqual(new TableFieldValue("20201020")))
                .and(Field.field("date_type").equal(new TableFieldValue("recent_30d")))
                .and(Field.field("user_behav_type").equal(new TableFieldValue("comment_user"))))
                .groupby(Field.field("p_date"))
                .orderby()
                .offset(10)
                .limit(13)
                .fetch();
        System.out.println(sqlClient.getSql());
    }
}
