package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    private static String url = "jdbc:mysql://localhost:3306/pagosbd";
    private static String usuario = "root";     
    private static String contrasena = "";       

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            throw e;
        }
    }
}