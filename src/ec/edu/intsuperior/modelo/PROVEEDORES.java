/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.intsuperior.modelo;

import Conectar.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class PROVEEDORES {

    private Integer idProveedor;
    private String proNombre;
    private String proApellido;
    private String proTelefono;
    private String proDireccion;
    private String proRuc;
    private String proCorreo;

    public PROVEEDORES(Integer idProveedor, String proNombre, String proApellido, String proTelefono, String proDireccion, String proRuc, String proCorreo) {
        this.idProveedor = idProveedor;
        this.proNombre = proNombre;
        this.proApellido = proApellido;
        this.proTelefono = proTelefono;
        this.proDireccion = proDireccion;
        this.proRuc = proRuc;
        this.proCorreo = proCorreo;
    }

    public PROVEEDORES(String proNombre, String proApellido, String proTelefono, String proDireccion, String proRuc, String proCorreo) {
        this.proNombre = proNombre;
        this.proApellido = proApellido;
        this.proTelefono = proTelefono;
        this.proDireccion = proDireccion;
        this.proRuc = proRuc;
        this.proCorreo = proCorreo;

    }

    public PROVEEDORES() {
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public String getProApellido() {
        return proApellido;
    }

    public void setProApellido(String proApellido) {
        this.proApellido = proApellido;
    }

    public String getProTelefono() {
        return proTelefono;
    }

    public void setProTelefono(String proTelefono) {
        this.proTelefono = proTelefono;
    }

    public String getProDireccion() {
        return proDireccion;
    }

    public void setProDireccion(String proDireccion) {
        this.proDireccion = proDireccion;
    }

    public String getProRuc() {
        return proRuc;
    }

    public void setProRuc(String proRuc) {
        this.proRuc = proRuc;
    }

    public String getProCorreo() {
        return proCorreo;
    }

    public void setProCorreo(String proCorreo) {
        this.proCorreo = proCorreo;
    }

    public void guardar() {
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "INSERT INTO proveedores (proNombre,proApellido,ProTelefono,proDireccion, proRuc,proCorre)VALUES (?,?,?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setString(1, getProNombre());
            ps.setString(2, getProApellido());
            ps.setString(3, getProTelefono());
            ps.setString(4, getProDireccion());
            ps.setString(5, getProRuc());
            ps.setString(6, getProCorreo());

            int n = ps.executeUpdate();
            if (n > 0) {
                System.out.println("Provedor Creado con Exito");
                JOptionPane.showMessageDialog(null, "Proveedor Creado con Exito", "Crear Registro", JOptionPane.INFORMATION_MESSAGE);
            }

            cnn.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("No se logro grabar el Registro.." + e);
        }

    }

    public ArrayList<PROVEEDORES> listarProvedores() {
     Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();

        ArrayList<PROVEEDORES> proveedoresArray = new ArrayList<PROVEEDORES>();

        try {

            CallableStatement statement = cnn.prepareCall("{call SP_S_Proveedores}");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                PROVEEDORES empArrayList = new PROVEEDORES();

                empArrayList.setIdProveedor(rs.getInt("idProveedor"));
                empArrayList.setProNombre(rs.getString("proNombre"));
                empArrayList.setProApellido(rs.getString("proApellido"));
                empArrayList.setProTelefono(rs.getString("proTelefono"));
                empArrayList.setProDireccion(rs.getString("proDireccion"));
                empArrayList.setProRuc(rs.getString("proRuc"));
                empArrayList.setProCorreo(rs.getString("proCorreo"));

//                private Integer idProvedor;
//                private String proNombre;
//                private String proApellido;
//                private String proTelefono;
//                private String proDireccion;
//                private String proRuc;
//                private String proCorreo;
//                
//                
                proveedoresArray.add(empArrayList);
            }
            return proveedoresArray;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet listarProveedoresPorParametro(String criterio, String busqueda) throws Exception {
        System.out.println("Criterio: " + criterio);
        System.out.println("Busqueda: " + busqueda);
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_ProveedorParametro(?,?)}");
            st.setString("pcriterio", criterio);
            st.setString("pbusqueda", busqueda);
            rs = st.executeQuery();
            System.out.println("Busqueda realizada: " + rs);

            return rs;
        } catch (SQLException SQLex) {
            throw SQLex;
        }
    }

    public boolean actualizar() throws SQLException {
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "UPDATE proveedores SET proNombre=?, proNombreContacto=?,proRuc=?, proCorreo=?, proTelefono=?,proDireccion=?,proPaginaWeb=? "
                + "WHERE idProveedor=" + getIdProveedor();

        ps = cnn.prepareStatement(sql);
        ps.setString(1, getProNombre());
        ps.setString(2, getProApellido());
        ps.setString(3, getProTelefono());
        ps.setString(4, getProDireccion());
        ps.setString(5, getProRuc());
        ps.setString(6, getProCorreo());

        System.out.println(sql);
        int ru = ps.executeUpdate();
        cnn.close();
        ps.close();
        if (ru > 0) {
            System.out.println("Proveedor Actualizado");
            JOptionPane.showMessageDialog(null, "Proveedor Actualizado con exito", "Actualizar Registro", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo Actualizar el Registro", "Actualizar Registro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void Eliminar(String strCodigo) throws SQLException {

        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "DELETE FROM proveedores WHERE idProveedor = " + strCodigo;
        try {
            ps = cnn.prepareStatement(sql);

            int n = ps.executeUpdate();

            if (n > 0) {
                System.out.println("Proveedor Eliminado con exito");
                JOptionPane.showMessageDialog(null, "Proveedor Eliminado con exito", "Eliminar Registro", JOptionPane.INFORMATION_MESSAGE);
            }

            cnn.close();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se logro Eliminar el Registro..." + ex, "Eliminar Registro", JOptionPane.ERROR_MESSAGE);

        }
    }

}

