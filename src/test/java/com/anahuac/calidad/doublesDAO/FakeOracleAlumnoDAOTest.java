package com.anahuac.calidad.doublesDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


import java.util.HashMap;

import static org.junit.Assert.*;

public class FakeOracleAlumnoDAOTest {

    private FakeOracleAlumnoDAO dao;
    public HashMap<String, Alumno> alumnos;

    @Before
    public void setUp() throws Exception {
        dao = Mockito.mock(FakeOracleAlumnoDAO.class);
        alumnos = new HashMap<String, Alumno>();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addAlumnoTest(){

        int cuantosAntes = alumnos.size();
        System.out.println("Size antes = " + cuantosAntes);
        Alumno alumno1 = new Alumno("nombre", "001", 20, "micorreo@hola.com");

        /*
        para booleans
        when(dao.addAlumno(any(Alumno.class))).thenAnswer(
            new Answer<Boolean>(){
                public Boolean answer(InvocationOnMock invocation) throws Throwable {
                    Alumno arg = (Alumno) invocation.getArguments()[0];
                    alumnos.put(anyString(), arg);
                    System.out.println("Size despues = " + alumnos.size());
                    return true;
                }
            }
        );
        */

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Alumno arg = (Alumno) invocation.getArguments()[0];
                alumnos.put(anyString(), arg);
                System.out.println("Size despues = " + alumnos.size());
                return null;
            }
        }).when(dao).addAlumno(any(Alumno.class));

        dao.addAlumno(alumno1);
        int cuantosDespues = alumnos.size();

        assertThat(cuantosAntes + 1, is(cuantosDespues));
    }

    @Test
    public void deleteAlumnoTest(){
        int cuantosAntes = alumnos.size();
        System.out.println("Size antes = " +  cuantosAntes);
        final Alumno alumno1 = new Alumno("nombre", "001", 20, "micorreo@hola.com");

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Alumno arg = (Alumno) invocation.getArguments()[0];
                alumnos.put(anyString(), arg);
                System.out.println("Size despues = " + alumnos.size());
                return null;
            }
        }).when(dao).addAlumno(any(Alumno.class));
        dao.addAlumno(alumno1);

        doAnswer(new Answer(){
            public Object answer(InvocationOnMock invocation){
                Alumno arg = (Alumno) invocation.getArguments()[0];
                alumnos.remove(alumno1);
                System.out.println("Size despues = " + alumnos.size());
                return null;
            }
        }).when(dao).deleteAlumno(any(Alumno.class));
        dao.deleteAlumno(alumno1);


    }
}