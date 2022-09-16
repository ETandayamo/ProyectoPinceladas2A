/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conectar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Conexion {

  
    private static String USUARIO = "root";
    private static String PASSWORD = "Junior,rosa,yo,ozil!";
    private static String URL = "jdbc:mysql://localhost:3306/dbpinceladas";

    

    public Connection ObtenerConexion() {
        Connection cnn = null;
        try {
            cnn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("conexion exitosa");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cnn;
    }

    
}
