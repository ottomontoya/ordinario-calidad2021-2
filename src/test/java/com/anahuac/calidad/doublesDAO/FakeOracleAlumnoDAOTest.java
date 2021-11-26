package com.anahuac.calidad.doublesDAO;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
// Import HasMap
import java.util.HashMap;
//Import mockito
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
//Import hamcrest
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class FakeOracleAlumnoDAOTest {
	
	private FakeOracleAlumnoDAO DAO;
	// Initialize HashMap
	public HashMap <String, Alumno> alumnos; 
	// Create student
	Alumno alumno1; 
	// Act email
	String nuevoCorreo = "nuevocorreo@hola.com";

	@Before
	public void setUp() throws Exception {
		DAO = Mockito.mock(FakeOracleAlumnoDAO.class);
		alumnos = new HashMap <String, Alumno>();
		// Declare the object with parameters
		alumno1 = new Alumno("001","nombre", "micorreo2@hola.com", 23); 
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	// DAO Add Student
	@Test
	public void addAlumno_test() {
		
		// Set the value of students before adding a new one
		int cuantosAntes = alumnos.size(); 
		System.out.println("Add Alumno Mock"); 
		System.out.println("Size antes=" + cuantosAntes); 
		
		// Set behaviors 
		when(DAO.addAlumno(any(Alumno.class))).thenAnswer(new Answer<Boolean>() {
			// Method within the class
			public Boolean answer(InvocationOnMock invocation) throws Throwable{
				// Set behavior in every invocation 
				Alumno arg = (Alumno) invocation.getArguments()[0]; 
				alumnos.put(anyString(), arg); 
				System.out.println("Size despues=" + alumnos.size() + "\n"); 
				// Return the invoked value
				return true; 
				}
			}
		);
		// Call the method and add one student
		DAO.addAlumno(alumno1);
		int cuantosDesp = alumnos.size(); 
		assertThat(cuantosAntes+1,is(cuantosDesp)); 	
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
