package com.anahuac.calidad.dbunit;

import com.anahuac.calidad.doublesDAO.Alumno;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class DAOAlumnoTest extends DBTestCase {

    public DAOAlumnoTest(){
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:33060/pruebas_db");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "travis");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/resources/iniDB.xml"));
    }

    @Before
    public void setUp() throws Exception{
        IDatabaseConnection connection = getConnection();
        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
        } catch (Exception e){
            fail("Error in setup: " + e.getMessage());
        } finally {
            connection.close();
        }
    }

    @After
    public void tearDown() throws Exception{
    }

    @Test
    public void testAdd() {
        Alumno alumno = new Alumno("otto", "15", 21, "hola@gmail.com");
        AlumnoDAOMySQL daoMySQL = new AlumnoDAOMySQL();

        daoMySQL.addAlumno(alumno);

        try {
            IDataSet databaseDataSet = getConnection().createDataSet(); //esta es toda la base de datos
            ITable actualTable = databaseDataSet.getTable("alumnos_tbl");

            //Leer el archivo con el resultado esperado
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/insert_result.xml"));
            ITable expectedTable = expectedDataSet.getTable("alumnos_tbl");

            Assertion.assertEquals(expectedTable, actualTable);
        } catch (Exception e) {
            fail("Error in CREATE test: " + e.getMessage());
        }
    }

    @Test
    public void testRead() {
        AlumnoDAOMySQL daoMySQL = new AlumnoDAOMySQL();

        daoMySQL.consultarAlumno("001");

        try {
            IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("alumnos_tbl");

            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/read_result.xml"));
            ITable expectedTable = expectedDataSet.getTable("alumnos_tbl");

            Assertion.assertEquals(expectedTable, actualTable);
        } catch (Exception e){
            fail("Error in READ test: " + e.getMessage());
        }
    }

    @Test
    public void testUpdate() {
        Alumno alumno = new Alumno("hola", "001", 10, "update@hola.com");
        AlumnoDAOMySQL daoMySQL = new AlumnoDAOMySQL();

        daoMySQL.updateEmail(alumno);

        try {
            IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("alumnos_tbl");

            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/update_result.xml"));
            ITable expectedTable = expectedDataSet.getTable("alumnos_tbl");

            Assertion.assertEquals(expectedTable, actualTable);
        } catch (Exception e){
            fail("Error in UPDATE test: " + e.getMessage());
        }
    }

    @Test
    public void testDelete() {
        Alumno alumno = new Alumno("hola", "001", 10, "hola@hola.com");
        AlumnoDAOMySQL daoMySQL = new AlumnoDAOMySQL();

        daoMySQL.deleteAlumno(alumno);

        try {
            IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("alumnos_tbl");

            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/delete_result.xml"));
            ITable expectedTable = expectedDataSet.getTable("alumnos_tbl");

            Assertion.assertEquals(expectedTable, actualTable);
        } catch (Exception e){
            fail("Error in DELETE test: " + e.getMessage());
        }
    }
}