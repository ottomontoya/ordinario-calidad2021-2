package com.anahuac.calidad.doublesDAO;

public interface AlumnoDAO {
    public Boolean addAlumno(Alumno a);
    public Boolean deleteAlumno(Alumno a);
    public Boolean updateEmail(Alumno a);
    public Alumno consultarAlumno(String id);

}
