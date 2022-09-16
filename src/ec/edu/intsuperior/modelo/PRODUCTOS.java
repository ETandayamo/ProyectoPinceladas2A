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
public class PRODUCTOS {

    INVENTARIO inv = new INVENTARIO();

    private Integer idProducto;
    private String prodCategoria;
    private String prodDescripcion;
    private String prodMarca;
    private Double prodPrecioUnitario;

    public PRODUCTOS(Integer idProducto, String prodCategoria, String prodDescripcion, String prodMarca, Double prodPrecioUnitario) {
        this.idProducto = idProducto;
        this.prodCategoria = prodCategoria;
        this.prodDescripcion = prodDescripcion;
        this.prodMarca = prodMarca;
        this.prodPrecioUnitario = prodPrecioUnitario;

    }

    public PRODUCTOS(String prodCategoria, String prodDescripcion, String prodMarca, Double prodPrecioUnitario) {
        this.prodCategoria = prodCategoria;
        this.prodDescripcion = prodDescripcion;
        this.prodMarca = prodMarca;
        this.prodPrecioUnitario = prodPrecioUnitario;
    }

    public PRODUCTOS() {
    }

    public INVENTARIO getInv() {
        return inv;
    }

    public void setInv(INVENTARIO inv) {
        this.inv = inv;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getProdCategoria() {
        return prodCategoria;
    }

    public void setProdCategoria(String prodCategoria) {
        this.prodCategoria = prodCategoria;
    }

    public String getProdDescripcion() {
        return prodDescripcion;
    }

    public void setProdDescripcion(String prodDescripcion) {
        this.prodDescripcion = prodDescripcion;
    }

    public String getProdMarca() {
        return prodMarca;
    }

    public void setProdMarca(String prodMarca) {
        this.prodMarca = prodMarca;
    }

    public Double getProdPrecioUnitario() {
        return prodPrecioUnitario;
    }

    public void setProdPrecioUnitario(Double prodPrecioUnitario) {
        this.prodPrecioUnitario = prodPrecioUnitario;
    }

    public void guardar() {
        Conexion cc = new Conexion();
        Connection cnn = cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "INSERT INTO productos (ProdCategoria,proDescripcion,ProdMarca,ProdPrecioUnitario)VALUES (?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setString(1, getProdCategoria());
            ps.setString(2, getProdDescripcion());
            ps.setString(3, getProdMarca());
            ps.setDouble(4, getProdPrecioUnitario());

            int n = ps.executeUpdate();
            if (n > 0) {
                System.out.println("Producto Creado con Exito");
                JOptionPane.showMessageDialog(null, "Producto Creado con Exito", "Crear Registro", JOptionPane.INFORMATION_MESSAGE);
                listarProductos();
            }

            cnn.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("No se logro grabar el Registro.." + e);
        }

    }

    public ArrayList<PRODUCTOS> listarProductos() {
//        db.conectar con = new conectar();
        Conexion cc = new Conexion();
        Connection cnn = cc.ObtenerConexion();
        ArrayList<PRODUCTOS> productosArray = new ArrayList<PRODUCTOS>();

        try {
            CallableStatement statement = cnn.prepareCall("{call SP_S_Productos}");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                PRODUCTOS productosArrayList = new PRODUCTOS();
                productosArrayList.setIdProducto(rs.getInt("idProducto"));
                productosArrayList.setProdCategoria(rs.getString("prodCategoria"));
                productosArrayList.setProdDescripcion(rs.getString("prodDescripcion"));
                productosArrayList.setProdMarca(rs.getString("prodMarca"));
                productosArrayList.setProdPrecioUnitario(rs.getDouble("proPrecioUnitario"));

                productosArray.add(productosArrayList);
            }
            return productosArray;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet listarProductoPorParametro(String criterio, String busqueda) throws Exception {
        System.out.println("Criterio: " + criterio);
        System.out.println("Busqueda: " + busqueda);
        Conexion cc = new Conexion();
        Connection cnn = cc.ObtenerConexion();

        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_ProductoParametro(?,?)}");
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
        Conexion cc = new Conexion();
        Connection cnn = cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;
        sql = "UPDATE productos SET prodCategoria=?, prodDescripcion=?,prodMarca=?, prodPrecioUnitario=? WHERE idProducto=" + getIdProducto();

//                private Integer idProducto;
//                private String prodCategoria;
//                private String proDescripcion;
        //               private String prodMarca; 
//                private Double prodPrecioUnitario;
        ps = cnn.prepareStatement(sql);

        ps.setString(1, getProdCategoria());
        ps.setString(2, getProdDescripcion());
        ps.setString(3, getProdMarca());
        ps.setDouble(4, getProdPrecioUnitario());

        System.out.println(sql);
        int ru = ps.executeUpdate();
        cnn.close();
        ps.close();
        if (ru > 0) {
            System.out.println("Producto Actualizado con exito");
            JOptionPane.showMessageDialog(null, "Producto Actualizado con exito", "Actualizar Registro", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo Actualizar el Registro", "Actualizar Registro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void Limpiar() {

    }

    public void Eliminar(String strCodigo) throws SQLException {

        Conexion cc = new Conexion();
        Connection cnn = cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "DELETE FROM productos WHERE idProducto= " + strCodigo;
        try {
            ps = cnn.prepareStatement(sql);

            int n = ps.executeUpdate();

            if (n > 0) {
                System.out.println("Producto Eliminado con exito");
                JOptionPane.showMessageDialog(null, "Producto Eliminado con exito", "Eliminar Registro", JOptionPane.INFORMATION_MESSAGE);
            }

            cnn.close();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se logro Eliminar el Registro..." + ex, "Eliminar Registro", JOptionPane.ERROR_MESSAGE);

        }
    }

    public ArrayList<PRODUCTOS> listarProductosFactura() {
        Conexion cc = new Conexion();
        Connection cnn = cc.ObtenerConexion();
        ArrayList<PRODUCTOS> productoFacturaArray = new ArrayList<PRODUCTOS>();
        try {
            CallableStatement statement = cnn.prepareCall("{call SP_S_ProductoFactura}");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PRODUCTOS FacturaProductoArrayList = new PRODUCTOS();
                FacturaProductoArrayList.setIdProducto(rs.getInt("idProducto"));
                FacturaProductoArrayList.setProdCategoria(rs.getString("prodCategoria"));
                FacturaProductoArrayList.setProdDescripcion(rs.getString("prodDescripcion"));
                FacturaProductoArrayList.setProdMarca(rs.getString("prodMarca"));
                FacturaProductoArrayList.setProdPrecioUnitario(rs.getDouble("prodPrecioUnitario"));
                productoFacturaArray.add(FacturaProductoArrayList);
            }
            return productoFacturaArray;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet listarProductoFactPorParametro(String criterio, String busqueda) throws Exception {
        System.out.println("Criterio: " + criterio);
        System.out.println("Busqueda: " + busqueda);
        Conexion cc = new Conexion();
        Connection cnn = cc.ObtenerConexion();

        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_ProductoFactParametro(?,?)}");
            st.setString("pcriterio", criterio);
            st.setString("pbusqueda", busqueda);
            rs = st.executeQuery();
            System.out.println("Busqueda realizada: " + rs);
            return rs;
        } catch (SQLException SQLex) {
            throw SQLex;
        }
    }
}
