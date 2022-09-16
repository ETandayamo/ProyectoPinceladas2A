/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.intsuperior.modelo;

import Conectar. Conexion;
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
public class CLIENTES{

    private Integer idCliente;
    private String cliNombre;
    private String cliApellido;
    private String cliCedula;
    private String cliDireccion;
    private String cliTelefono;
    private String cliCorreo;

    public CLIENTES(Integer idCliente, String cliNombre, String cliApellido, String cliCedula, String cliDireccion, String cliTelefono, String cliCorreo) {
        this.idCliente = idCliente;
        this.cliNombre = cliNombre;
        this.cliApellido = cliApellido;
        this.cliCedula = cliCedula;
        this.cliDireccion = cliDireccion;
        this.cliTelefono = cliTelefono;
        this.cliCorreo = cliCorreo;
    }

    public CLIENTES(String cliNombre, String cliApellido, String cliCedula, String cliDireccion, String cliTelefono, String cliCorreo) {
        this.cliNombre = cliNombre;
        this.cliApellido = cliApellido;
        this.cliCedula = cliCedula;
        this.cliDireccion = cliDireccion;
        this.cliTelefono = cliTelefono;
        this.cliCorreo = cliCorreo;
    }
     public CLIENTES() {
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getCliNombre() {
        return cliNombre;
    }

    public void setCliNombre(String cliNombre) {
        this.cliNombre = cliNombre;
    }

    public String getCliApellido() {
        return cliApellido;
    }

    public void setCliApellido(String cliApellido) {
        this.cliApellido = cliApellido;
    }

    public String getCliCedula() {
        return cliCedula;
    }

    public void setCliCedula(String cliCedula) {
        this.cliCedula = cliCedula;
    }

    public String getCliDireccion() {
        return cliDireccion;
    }

    public void setCliDireccion(String cliDireccion) {
        this.cliDireccion = cliDireccion;
    }

    public String getCliTelefono() {
        return cliTelefono;
    }

    public void setCliTelefono(String cliTelefono) {
        this.cliTelefono = cliTelefono;
    }

    public String getCliCorreo() {
        return cliCorreo;
    }

    public void setCliCorreo(String cliCorreo) {
        this.cliCorreo = cliCorreo;
    }

    public void guardar() {
       Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "INSERT INTO clientes (cliNombre,cliApellido,cliCedula,cliDireccion,cliTelefono,cliCorreo)VALUES (?,?,?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setString(1, getCliNombre());
            ps.setString(2, getCliApellido());
            ps.setString(3, getCliCedula());
            ps.setString(4, getCliDireccion());
            ps.setString(5, getCliTelefono());
            ps.setString(6, getCliCorreo());
            int n = ps.executeUpdate();
            if (n > 0) {
                System.out.println("Cliente creado con exito");
                JOptionPane.showMessageDialog(null, "Cliente creado con exito", "Actualizar Registro", JOptionPane.INFORMATION_MESSAGE);
            }
            cnn.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo crear el Cliente");
            System.out.println("No se logro grabar el Registro.." + e);
        }

    }

    public ArrayList<CLIENTES> listarClientes() {
         Conexion cc=new Conexion();
         Connection cnn=cc.ObtenerConexion();
        ArrayList<CLIENTES> clienteusuarios = new ArrayList<CLIENTES>();

        try {

            CallableStatement statement = cnn.prepareCall("{call SP_S_Cliente}");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                CLIENTES cli5 = new CLIENTES();

                cli5.setIdCliente(rs.getInt("idCliente"));
                cli5.setCliNombre(rs.getString("cliNombre"));
                cli5.setCliApellido(rs.getString("cliApellido"));
                cli5.setCliCedula(rs.getString("cliCedula"));
                cli5.setCliDireccion(rs.getString("cliDireccion"));
                cli5.setCliTelefono(rs.getString("cliTelefono"));
                cli5.setCliCorreo(rs.getString("cliCorreo"));

                clienteusuarios.add(cli5);
            }
            return clienteusuarios;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

     public  ResultSet listarClientePorParametro(String criterio, String busqueda) throws Exception {
        System.out.println("Criterio: "+criterio);
        System.out.println("Busqueda: "+busqueda);
       Conexion cc=new Conexion();
       Connection cnn=cc.ObtenerConexion();
        
        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_ClienteParametro(?,?)}");
            st.setString("pcriterio", criterio);
            st.setString("pbusqueda", busqueda);
            rs = st.executeQuery();
            System.out.println("Busqueda realizada: "+ rs);
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
        sql = "UPDATE clientes SET cliNombre=?,cliApellido=?,cliCedula=?,cliDireccion=?,cliTelefono=?,cliCorreo=?WHERE idCliente="+ getIdCliente();
        
        ps = cnn.prepareStatement(sql);
        ps.setString(1,getCliNombre());
        ps.setString(2,getCliApellido());
        ps.setString(3,getCliCedula());
        ps.setString(4,getCliDireccion());
        ps.setString(5,getCliTelefono());
        ps.setString(6,getCliCorreo());
        System.out.println(sql);   
        int ru = ps.executeUpdate();
        cnn.close();
        ps.close();
        if (ru > 0) {
            System.out.println("Cliente Actualizado con exito");
            JOptionPane.showMessageDialog(null,"Cliente Actualizado con exito", "Actualizar Registro",JOptionPane.INFORMATION_MESSAGE);          
            return true;
        } else {
            JOptionPane.showMessageDialog(null,"No se pudo Actualizar el Registro", "Actualizar Registro",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
        public void eliminarDatos(String strCodigo) throws SQLException {
 
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "DELETE FROM clientes WHERE idCliente = "+ strCodigo;
        try {
            ps = cnn.prepareStatement(sql);
            
              int n = ps.executeUpdate();
          
            if (n > 0) {
                System.out.println("Cliente Eliminado con exito");
                JOptionPane.showMessageDialog(null, "Cliente Eliminado con exito", "Eliminar Registro", JOptionPane.INFORMATION_MESSAGE);
            }

            cnn.close();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se logro Eliminar el Registro..." + ex, "Eliminar Registro", JOptionPane.ERROR_MESSAGE);
            
        }
        }
}
    
   

    
    
    
