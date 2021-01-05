package cn.dyz.tools;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.junit.Test;

public class DruidTest {

    @Test
    public void test1() throws SqlParseException {
        // Convert query to SqlNode
        String sql = "select price from transactions";
        SqlParser.Config config = SqlParser.configBuilder().build();
        SqlParser parser = SqlParser.create(sql, config);
        SqlNode node = parser.parseQuery();

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("3", 3);

        final String a = map.toString();
        System.out.println("a: " + a);
        map.clear();
        System.out.println("a:" + a);
    }
}
