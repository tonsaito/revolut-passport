package com.revolut.tonsaito.dao;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Fields;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientDAOTest {

	@Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConn;
    @Mock
    PreparedStatement mockPreparedStmnt;
    @Mock
    ResultSet mockResultSet;
    int userId = 100;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockDataSource.getConnection(anyString(), anyString())).thenReturn(mockConn);
        doNothing().when(mockConn).commit();
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStmnt);
        doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
        when(mockPreparedStmnt.execute()).thenReturn(Boolean.TRUE);
        when(mockPreparedStmnt.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
//        when(mockResultSet.getInt(Fields.GENERATED_KEYS)).thenReturn(userId);
    }
    
//    @Test
//    public void shouldCreateEntityWithoutErrors() {
//    		ClientDAO.createEntity();
//    }
//    
//    @Test
//    public void shouldCreateEntityWithoutErrors() {
//    		ClientDAO.createEntity();
//    }
}
