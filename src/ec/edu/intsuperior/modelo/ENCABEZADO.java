/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.intsuperior.modelo;
import Conectar.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ENCABEZADO {

    private Integer idEncabezado;
    private Integer idEmpleado;
    private Integer idCliente;
    private String encFecha;
    private String encNumeroFactura;

    public ENCABEZADO (Integer idEncabezado, Integer idEmpleado, Integer idCliente, String encFecha, String encNumeroFactura) {
        this.idEncabezado = idEncabezado;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
        this.encFecha = encFecha;
        this.encNumeroFactura = encNumeroFactura;
    }

    public ENCABEZADO (Integer idEmpleado, Integer idCliente, String encNumeroFactura, String encFecha) {
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
        this.encNumeroFactura = encNumeroFactura;
        this.encFecha = encFecha;
    }

    public ENCABEZADO() {
    }

    public Integer getIdEncabezado() {
        return idEncabezado;
    }

    public void setIdEncabezado(Integer idEncabezado) {
        this.idEncabezado = idEncabezado;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getEncFecha() {
        return encFecha;
    }

    public void setEncFecha(String encFecha) {
        this.encFecha = encFecha;
    }

    public String getEncNumeroFactura() {
        return encNumeroFactura;
    }

    public void setEncNumeroFactura(String encNumeroFactura) {
        this.encNumeroFactura = encNumeroFactura;
    }

    public void guardar() {
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "INSERT INTO encabezados(IDEMPLEADO,IDCLIENTE,ENCFECHA,ENCNUMEROFACTURA) VALUES (?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, getIdEmpleado());
            ps.setInt(2, getIdCliente());
            ps.setString(4, getEncFecha());
            ps.setString(3, getEncNumeroFactura());

            int n = ps.executeUpdate();
            if (n > 0) {
                System.out.println("Encabezado Creado con exito");
                JOptionPane.showMessageDialog(null, "Encabezado de Factura Creado con exito", "Crear Registro", JOptionPane.INFORMATION_MESSAGE);
            }

            cnn.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo crear el Encabezado");
            System.out.println("No se logro grabar el Registro.." + e);
        }
    }

    public String generadorNumfactura() {
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();;
        PreparedStatement ps;
        ResultSet rs;
        String serie = null;
        String sql;
        sql = "SELECT max(IDENCABEZADO) FROM encabezados;";

        try {
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                serie = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return serie;
    }

}
