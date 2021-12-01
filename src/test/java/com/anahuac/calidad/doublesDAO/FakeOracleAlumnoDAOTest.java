package com.anahuac.calidad.doublesDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.CoreMatchers.not;
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
    Alumno alumno1;

    @Before
    public void setUp() throws Exception {
        dao = Mockito.mock(FakeOracleAlumnoDAO.class);
        alumnos = new HashMap<String, Alumno>();
        alumno1 = new Alumno("nombre", "001", 21, "micorreo@hola.com");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addAlumnoTest() {

        System.out.println("\n----- CREATE ALUMNO TEST -----");
        int cuantosAntes = alumnos.size();
        System.out.println("Size antes = " + cuantosAntes);

        when(dao.addAlumno(any(Alumno.class))).thenAnswer(
            new Answer<Boolean>(){
                public Boolean answer(InvocationOnMock invocation) throws Throwable {
                    Alumno arg = (Alumno) invocation.getArguments()[0];
                    alumnos.put("001", arg);
                    System.out.println("Size despues = " + alumnos.size());
                    return true;
                }
            }
        );

        /*
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Alumno arg = (Alumno) invocation.getArguments()[0];
                alumnos.put("001", arg);
                System.out.println("Size despues = " + alumnos.size());
                return null;
            }
        }).when(dao).addAlumno(any(Alumno.class));
        */

        dao.addAlumno(alumno1);
        int cuantosDespues = alumnos.size();

        assertThat(cuantosAntes + 1, is(cuantosDespues));
    }


    @Test
    public void readAlumnoTest() {

        System.out.println("\n----- RETRIEVE ALUMNO TEST ------");
        alumnos.put("001", alumno1);
        int cuantosAntes = alumnos.size();
        System.out.println("Size antes = " + cuantosAntes);

        when(dao.consultarAlumno(any(String.class))).thenAnswer(
            new Answer<Alumno>(){
                public Alumno answer(InvocationOnMock invocation) throws Throwable {
                    String arg = (String) invocation.getArguments()[0];
                    System.out.println("Consultar Alumno = " + alumnos.get(arg).getNombre());
                    return alumnos.get(arg);
                }
        });

        dao.consultarAlumno("001");
        int cuantosDespues = alumnos.size();
        System.out.println("Size despues = " + cuantosDespues);

        assertThat(cuantosAntes, is(cuantosDespues));
    }

    @Test
    public void updateEmailTest() {

        System.out.println("\n----- UPDATE ALUMNO TEST -----");
        alumnos.put(alumno1.getId(), alumno1);
        String emailBefore = alumno1.getEmail();
        System.out.println("Correo antes = " + emailBefore);
        alumno1 = new Alumno("nombre", "001", 21, "otrocorreo@hola.com");

        doAnswer(new Answer(){
            public Object answer(InvocationOnMock invocation){
                Alumno arg = (Alumno) invocation.getArguments()[0];
                alumnos.replace(arg.getId(), arg);
                return null;
            }
        }).when(dao).updateEmail(any(Alumno.class));

        dao.updateEmail(alumno1);
        String emailAfter = alumnos.get(alumno1.getId()).getEmail();
        System.out.println("Correo despues = " + emailAfter);

        assertThat(emailBefore, is(not(emailAfter)));
    }

    @Test
    public void deleteALumnoTest() {

        alumnos.put("001", alumno1);

        System.out.println("\n----- DELETE ALUMNO TEST -----");
        int cuantosAntes = alumnos.size();
        System.out.println("Size antes = " + cuantosAntes);

        when(dao.deleteAlumno(any(Alumno.class))).thenAnswer(
                new Answer<Boolean>() {
                    public Boolean answer(InvocationOnMock invocation) throws Throwable {
                        Alumno arg = (Alumno) invocation.getArguments()[0];
                        alumnos.remove(arg.getId(), arg);

                        return true;
                    }
                }
        );dao.deleteAlumno(alumno1);

        System.out.println("Size despues = " + alumnos.size());
        int cuantosDespues = alumnos.size();
        assertThat(cuantosAntes - 1, is(cuantosDespues));
    }
}
