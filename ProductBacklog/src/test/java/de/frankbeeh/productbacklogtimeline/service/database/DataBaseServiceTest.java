package de.frankbeeh.productbacklogtimeline.service.database;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Before;

public class DataBaseServiceTest {
	private static final String TEST_DB = "build/test.db";
    private final TestDataBase testDataBase;

    public DataBaseServiceTest(){
        testDataBase = new TestDataBase(TEST_DB);
    }
    
	protected Connection getConnection(){
		return testDataBase.getConnection();
	}
	
    protected DSLContext getDslContext() {
        return DSL.using(getConnection(), SQLDialect.SQLITE);
    }

	@Before
	public void setUpTables() throws Exception {
	    testDataBase.recreateTables();
	}

	@After
	public void tearDownConnection() throws Exception {
	    testDataBase.close();
	}
}
