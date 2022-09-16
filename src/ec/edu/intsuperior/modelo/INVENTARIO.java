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
public class INVENTARIO {

    private Integer idInventario;
    private Integer idProveedor;
    private Integer idProducto;
    private String invFecha;
    private Integer invStock;

    public INVENTARIO (Integer idInventario, Integer idProveedor, Integer idProducto, String invFecha, Integer invStock) {
        this.idInventario = idInventario;
        this.idProveedor = idProveedor;
        this.idProducto = idProducto;
        this.invFecha = invFecha;
        this.invStock = invStock;
    }

    public INVENTARIO(Integer idProveedor, Integer idProducto, String invFecha, Integer invStock) {
        this.idProveedor = idProveedor;
        this.idProducto = idProducto;
        this.invFecha = invFecha;
        this.invStock = invStock;

    }
   

    public INVENTARIO() {

    }

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getInvFecha() {
        return invFecha;
    }

    public void setInvFecha(String invFecha) {
        this.invFecha = invFecha;
    }

    public Integer getInvStock() {
        return invStock;
    }

    public void setInvStock(Integer invStock) {
        this.invStock = invStock;
    }

    public void guardar() {
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "INSERT INTO `inventario` (`IDPROVEEDOR`, `IDPRODUCTO`, `INVFECHA`, `INVSTOK`) VALUES (?,?,?,?)";
        mostrarDatos();
        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, getIdProveedor());
            ps.setInt(2, getIdProducto());
            ps.setString(3, getInvFecha());
            ps.setInt(4, getInvStock());

            int n = ps.executeUpdate();
            if (n > 0) {
                System.out.println("Inventario Creado con Exito");
                JOptionPane.showMessageDialog(null, "Inventario Creado con exito", "Crear Registro", JOptionPane.INFORMATION_MESSAGE);

            }

            cnn.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("No se logro grabar el Registro.." + e);
        }
    }

    public void mostrarDatos() {
        System.out.println(getIdProveedor());
        System.out.println(getIdProducto());
        System.out.println(getInvFecha());

        System.out.println(getInvStock());
    }

    public ArrayList<INVENTARIO> listarInventario() {
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        ArrayList<INVENTARIO> inventarioArray = new ArrayList<INVENTARIO>();

        try {

            CallableStatement statement = cnn.prepareCall("{call SP_S_Inventario}");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                INVENTARIO inventarioArrayList = new INVENTARIO();
                inventarioArrayList.setIdInventario(rs.getInt("idInventario"));
                inventarioArrayList.setIdProveedor(rs.getInt("idProveedor"));
                inventarioArrayList.setIdProducto(rs.getInt("idProducto"));
                inventarioArrayList.setInvFecha(rs.getString("invFecha"));
                inventarioArrayList.setInvStock(rs.getInt("invStok"));
                inventarioArray.add(inventarioArrayList);
            }
            return inventarioArray;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet listarInventarioPorParametro(String criterio, String busqueda) throws Exception {
        System.out.println("Criterio: " + criterio);
        System.out.println("Busqueda: " + busqueda);
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_InventarioParametro(?,?)}");
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
        sql = "UPDATE inventario SET idProveedor=?, idProducto=?, invFecha=?, invStok=? WHERE idInventario=" + getIdInventario();

        mostrarDatos();

        ps = cnn.prepareStatement(sql);
        ps.setInt(1, getIdProveedor());
        ps.setInt(2, getIdProducto());
        ps.setString(3, getInvFecha());
        ps.setInt(4, getInvStock());
        System.out.println(sql);
        int ru = ps.executeUpdate();
        cnn.close();
        ps.close();
        if (ru > 0) {
            JOptionPane.showMessageDialog(null, "Actualización Exitosa", "Actualizar Registro", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo Actualizar el Registro", "Actualizar Registro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void DatosEliminar(String strCodigo) throws SQLException {
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "DELETE FROM inventario WHERE idInventario= " + strCodigo;
        try {
            ps = cnn.prepareStatement(sql);

            int n = ps.executeUpdate();

            if (n > 0) {

                JOptionPane.showMessageDialog(null, "Inventario Eliminado con exito", "Eliminar Registro", JOptionPane.INFORMATION_MESSAGE);

            }

            cnn.close();
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se logro Eliminar el Registro..." + ex, "Eliminar Registro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarStock() throws SQLException {
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;
        sql = "UPDATE inventario SET  invStok=? WHERE idInventario=" + getIdInventario();

        try {
            ps = cnn.prepareStatement(sql);
            ps.setInt(0, getIdInventario());
            ps.setInt(4, getInvStock());

            System.out.println(sql);
            int ru = ps.executeUpdate();
            cnn.close();
            ps.close();
            if (ru > 0) {
                JOptionPane.showMessageDialog(null, "Actualizacion Exitosa", "Cambio de Clave", JOptionPane.INFORMATION_MESSAGE);
            } else {

                JOptionPane.showMessageDialog(null, "Actualizacion fallida", "Actualizacion Exitosa", JOptionPane.ERROR_MESSAGE);

            }
        } catch (SQLException ex) {
            System.out.println("No se logro acualizar el registro usuarios: " + ex);
            //Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
