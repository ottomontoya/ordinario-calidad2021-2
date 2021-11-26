package com.anahuac.calidad.doublesDAO;

public class FakeOracleAlumnoDAO implements AlumnoDAO {

    @Override
    public Boolean addAlumno (Alumno a) {
        return false;
    }

    @Override
    public Boolean deleteAlumno(Alumno a) {
        return false;
    }

    @Override
    public Boolean updateEmail (Alumno a){
        return false;
    }

    @Override
    public Alumno searchAlumno (String id) {
        return null;
    }
}
