/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.intsuperior.modelo;

import Conectar.Conexion;
import com.sun.jndi.cosnaming.CNNameParser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class DETALLE {

    private Integer idDetalle;
    private Integer idEncabezado;
    private Integer idInventario;
    private String detFecha;

    public DETALLE(Integer idDetalle, Integer idEncabezado, Integer idInventario, String detFecha) {
        this.idDetalle = idDetalle;
        this.idEncabezado = idEncabezado;
        this.idInventario = idInventario;
        this.detFecha = detFecha;
    }

    public DETALLE(Integer idEncabezado, Integer idInventario, String detfecha) {
        this.idEncabezado = idEncabezado;
        this.idInventario = idInventario;
        this.detFecha = detfecha;
    }
    public DETALLE(){
        
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdEncabezado() {
        return idEncabezado;
    }

    public void setIdEncabezado(Integer idEncabezado) {
        this.idEncabezado = idEncabezado;
    }

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public String getDetFecha() {
        return detFecha;
    }

    public void setDetFecha(String detFecha) {
        this.detFecha = detFecha;
    }

        public void guardar() {
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = " INSERT INTO detalle(IDENCABEZADO,IDINVENTARIO,DETFECHA)VALUES (?,?,?)";
        try {

            ps = cnn.prepareStatement(sql);

            ps.setInt(1, getIdEncabezado());
            ps.setInt(2, getIdInventario());
            ps.setString(3, getDetFecha());

            int n = ps.executeUpdate();

            if (n > 0) {
                System.out.println("Producto Agregado con exito");
                JOptionPane.showMessageDialog(null, "Producto Agregado Creado con exito", "Actualizar Registro", JOptionPane.INFORMATION_MESSAGE);
            }

            cnn.close();
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el Producto");
            System.out.println("No se logro grabar el Registro.." + e);
        }
    }

}       