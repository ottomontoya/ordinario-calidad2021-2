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

    // DAO Delete Student
	@Test
	public void deleteAlumno_test() {
		
		// Put the student in the hashmap
		alumnos.put("001", alumno1); 
		// Set the value of students before deleting an student
		int cuantosAntes = alumnos.size(); 
		System.out.println("Delete Alumno Mock"); 
		System.out.println("Size antes=" + cuantosAntes); 
		
		// Set behaviors 
		when(DAO.deleteAlumno(any(Alumno.class))).thenAnswer(new Answer<Boolean>() {
			// Method within the class
			public Boolean answer(InvocationOnMock invocation) throws Throwable{
				// Set behavior in every invocation 
				Alumno arg = (Alumno) invocation.getArguments()[0]; 
				alumnos.remove(arg.getId(), arg); 
				System.out.println("Size despues=" + alumnos.size() + "\n"); 
				// Return the invoked value
				return true; 
				}
			}
		);
		// Call the method and add one student
		DAO.deleteAlumno(alumno1);
		int cuantosDesp = alumnos.size(); 
		assertThat(cuantosAntes-1,is(cuantosDesp)); 
	}
	
	
	// DAO Update Email
	@Test
	public void updateEmail_test() {
		// Put the student in the hashmap
		alumnos.put("001", alumno1); 
		// Set the value of students before deleting an student
		String correoActual = alumno1.getEmail(); 
		System.out.println("Update Email "); 
		System.out.println("Correo actual= " + correoActual); 
		
		// Set behaviors 
		when(DAO.updateEmail(any(Alumno.class))).thenAnswer(new Answer<Boolean>() {
			// Method within the class
			public Boolean answer(InvocationOnMock invocation) throws Throwable{
				// Set behavior in every invocation 
				Alumno arg = (Alumno) invocation.getArguments()[0]; 
				alumnos.replace(arg.getId(), arg); 
				// Return the invoked value
				return true; 
				}
			}
		);
		// Call the method and add one student
		alumno1.setEmail(nuevoCorreo);
		DAO.updateEmail(alumno1);
		assertThat(correoActual,is(not(nuevoCorreo)));
		System.out.println("Correo Actualizado= " + nuevoCorreo + "\n");  
	} 
	
	
	// DAO search Student
	@Test
	public void searchAlumno_test() { 			
		System.out.println("Search Alumno"); 
		// Set behaviors 
		when(DAO.searchAlumno(anyString())).thenAnswer(new Answer<Alumno>() {
			// Method within the class
			public Alumno answer(InvocationOnMock invocation) throws Throwable{
				// Set behavior in every invocation 
				String arg = (String) invocation.getArguments()[0]; 
				// Return the invoked value
				return alumno1; 
				}
			}
		);
		// Call the method and add one student
		Alumno alumnoRes = DAO.searchAlumno("001");
		assertThat(alumno1.getId(), is(alumnoRes.getId()));
		assertThat(alumno1.getEdad(), is(alumnoRes.getEdad()));
		assertThat(alumno1.getNombre(), is(alumnoRes.getNombre()));
		assertThat(alumno1.getEmail(), is(alumnoRes.getEmail()));
		System.out.println("Alumno encontrado"+ "\n"); 
	}

}
