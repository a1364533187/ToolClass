package cn.dyz.tools;

import static javafx.css.StyleOrigin.AUTHOR;
import static org.jooq.impl.DSL.count;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit shuffle for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the shuffle case
     *
     * @param testName name of the shuffle case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testApp1()
    {
    }
}
