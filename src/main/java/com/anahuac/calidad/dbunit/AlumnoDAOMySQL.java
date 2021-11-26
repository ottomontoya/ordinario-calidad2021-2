package com.anahuac.calidad.dbunit;

import com.anahuac.calidad.doublesDAO.Alumno;
import com.anahuac.calidad.doublesDAO.AlumnoDAO;

import java.sql.*;

public class AlumnoDAOMySQL implements AlumnoDAO{

    public Connection getConnectionMySQL() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:33060/pruebas_db", "root", "secret"
            );
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    @Override
    public boolean addAlumno(Alumno a){
        Connection connection = getConnectionMySQL();
        PreparedStatement preparedStatement;
        boolean result = false;
        try {
            preparedStatement = connection.prepareStatement(
                    "insert INTO alumnos_tbl(id, nombre, email, edad) values (?, ?, ?, ?)"
            );
            preparedStatement.setString(1, a.getId());
            preparedStatement.setString(2, a.getNombre());
            preparedStatement.setString(3, a.getEmail());
            preparedStatement.setInt(4, a.getEdad());

            if(preparedStatement.executeUpdate() >= 1){
                result = true;
            }

            connection.close();

        } catch(Exception e){
            System.out.println(e);
        }
        return result;
    }

    @Override
    public boolean deleteAlumno(Alumno a){
        Connection connection = getConnectionMySQL();
        PreparedStatement preparedStatement;
        boolean result = false;

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM alumno_tbl WHERE id = ?");

            preparedStatement.setString(1, a.getId());

            if(preparedStatement.executeUpdate() >= 1){
                result = true;
            }

            connection.close();
        } catch(SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public boolean updateEmail(Alumno a) {
        Connection connection = getConnectionMySQL();
        PreparedStatement preparedStatement;
        boolean result = false;

        try {
            preparedStatement = connection.prepareStatement("UPDATE alumno:tbl SET email = ? WHERE id = ?");
            preparedStatement.setString(1, a.getEmail());
            preparedStatement.setString(2, a.getId());
            if(preparedStatement.executeUpdate() >= 1){
                result = true;
            }

            connection.close();
        } catch(SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    @Override
    public Alumno consultarAlumno(String id){
        Connection connection = getConnectionMySQL();
        PreparedStatement preparedStatement;
        ResultSet rs;

        Alumno retrieved = null;

        try{
            preparedStatement = connection.prepareStatement("SELECT * FROM alumno_tbl WHERE id = ?");
            preparedStatement.setString(1, id);
            rs = preparedStatement.executeQuery();

            rs.next();

            String retrieveId = rs.getString(1);
            String retrieveName = rs.getString(2);
            String retrieveEmail = rs.getString(3);
            int retrieveAge = rs.getInt(4);

            retrieved = new Alumno(retrieveName, retrieveId, retrieveAge, retrieveEmail);

            rs.close();
            preparedStatement.close();
            connection.close();
        } catch(SQLException e){
            System.out.println(e);
        }
        return retrieved;
    }

}
