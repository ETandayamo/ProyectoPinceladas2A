/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.intsuperior.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Consultas {

    public static Connection cnn;

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "Junior,rosa,yo,ozil!";
    private static final String url = "jdbc:mysql://localhost:3306/dbpinceladas";

    PreparedStatement ps;
    ResultSet rs;

    public Connection conexion() {
        cnn = null;
        try {
            cnn = (Connection) DriverManager.getConnection(url, user, pass);
            if (cnn != null) {
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.toString());
        }
        return cnn;
    }

    public void LeerProveedores(String tabla, JTable visor) {
        String sql = "Select * from " + tabla;
        Statement st;
        Connection conexion = conexion();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("idproveedor");
        model.addColumn("pronombre");
        model.addColumn("proapellido");
        model.addColumn("protelefono");
        model.addColumn("prodireccion");
        model.addColumn("proruc");
        model.addColumn("procorreo");

        visor.setModel(model);
        String[] dato = new String[7];
        try {
            st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getString(5);
                dato[5] = rs.getString(6);
                dato[6] = rs.getString(7);
                model.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void InsertarEmpleado(JTextField empNombre, JTextField empApellido, JTextField empCedula, JTextField empDireccion,
            JTextField empCargo, JTextField empSeguro) {

        try {
            Connection conecta = conexion();
            ps = conecta.prepareStatement("INSERT INTO empleados(idEmpleado,empnombre,"
                    + " empapellido, empcedula,empdireccion,empcargo, empseguro)"
                    + " VALUES (?,?,?,?,?,?)");

            ps.setString(1, empNombre.getText());
            ps.setString(2, empApellido.getText());
            ps.setString(3, empCedula.getText());
            ps.setString(4, empDireccion.getText());
            ps.setString(5, empCargo.getText());
            ps.setString(6, empSeguro.getText());

            ps.execute();
            int n = ps.executeUpdate();
            if (n > 0) {
                System.out.println("Empleado Creado con exito");
                JOptionPane.showMessageDialog(null, "Empleado Creado con exito", "Actualizar Registro", JOptionPane.INFORMATION_MESSAGE);
            }

            cnn.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("No se logro grabar el Registro.." + e);
        }
    }

    public void InsertarCliente(JTextField cliNombre, JTextField cliApellido, JTextField cliCedula, JTextField cliApellido2,
            JTextField cliDireccion, JTextField cliTelefono,
            JTextField cliCorreo) {
        try {
            Connection conecta = conexion();
            CallableStatement proc = conecta.prepareCall(" CALL empleados(?,?,?,?,?,?,?,?)");
            proc.setString(1, cliNombre.getText());
            proc.setString(2, cliApellido.getText());
            proc.setString(3, cliCedula.getText());
            proc.setString(4, cliDireccion.getText());
            proc.setString(5, cliTelefono.getText());
            proc.setString(6, cliCorreo.getText());
            proc.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void EliminaRegistro(String id, String tabla, String id_name) {
        String sql = "delete from " + tabla + " where " + id_name + " = " + id;
        Statement st;
        Connection conexion = conexion();
        try {
            st = conexion.createStatement();
            int rs = st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void ActualizarEmpleado(String id, JTextField empNombre, JTextField empApellido, JTextField empCedula,
            JTextField empDireccion, JTextField empCargo, JTextField empSeguro) {
        String sql = "', empnombre = '" + empNombre.getText() + "', empapellido = '" + empApellido.getText()
                + "', empcedula = '" + empCedula.getText() + "', empdireccion = '" + empDireccion.getText() 
                + "', empseguro = '" + empSeguro.getText() + "', empcargo = '" + empCargo.getText()
                + "' where idempleado = " + id;

        Statement st;
        Connection conexion = conexion();
        try {
            st = conexion.createStatement();
            int rs = st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Empleado Actualizado");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void ActualizarCliente(JTextField cliNombre, JTextField cliApellido, JTextField cliCedula, JTextField cliApellido2,
            JTextField cliDireccion, JTextField cliTelefono,
            JTextField cliCorreo, String id) {
        String sql = "update clientes  = '" + cliNombre.getText() + "', clinombre = '" + cliApellido.getText() + "', cliapellido = '" 
                + cliCedula.getText() + "', clicedula= '" + "', clidireccion = '"
                + cliDireccion.getText() + "', clitelefono = '" + cliTelefono.getText() + "', clicorreo = '"
                + cliCorreo.getText() + "' where idcliente = " + id;
        Statement st;
        Connection conexion = conexion();
        try {
            st = conexion.createStatement();
            int rs = st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Cliente Actualizado");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void ActualizarProducto(JTextField prodNombre, JTextField prodPrecio, JTextField prodSabor, JTextField prodPorcentaje, String id) {

        String sql = "update productos set prodnombre = '" + prodNombre.getText() + "', prodpreciounitario = '"
                + prodPrecio.getText() + "', prodpreciounitario = '" + prodSabor.getText() + "', prodsabor = '"
                + "' where idproducto = " + id;
        Statement st;
        Connection conexion = conexion();
        try {
            st = conexion.createStatement();
            int rs = st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Producto Actualizado");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void ActualizarInventario(JTextField invStock, JTextField invFecha, String id) {
        String sql = "update inventario set invStock = '" + invStock.getText() + "', invfecha = '"
                + invFecha.getText() + "' where idinventario = " + id;
        Statement st;
        Connection conexion = conexion();
        try {
            st = conexion.createStatement();
            int rs = st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Inventario Actualizado");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void ActualizarProveedor(JTextField proNombre, JTextField proApellido,
             JTextField proTelefono,JTextField proDireccion,JTextField proRuc,
            JTextField proCorreo, String id) {
        String sql = "update proveedores= "+ "', pronombre = '" +proNombre.getText()
                + "', proapellido = '"+ proApellido.getText()
                + "', prodireccion = '" + proDireccion.getText()
                + "'protelefono= '" + proTelefono.getText()
                + "', procorreo= '"+ proCorreo.getText() + "', proruc = '" + proRuc.getText() + "' where idinventario = " + id;
        Statement st;
        Connection conexion = conexion();
        try {
            st = conexion.createStatement();
            int rs = st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Inventario Actualizado");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
