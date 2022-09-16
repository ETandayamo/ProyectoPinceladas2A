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
public class EMPLEADOS{

    private Integer idEmpleado;
    private String empNombre;
    private String empApellido;
    private String empCedula;
    private String empDireccion;
    private String empCargo;
    private String empSeguro;

    public EMPLEADOS(Integer idEmpleado, String empNombre, String empApellido, String empCedula, String empDireccion, String empCargo, String empSeguro) {
        this.idEmpleado = idEmpleado;
        this.empNombre = empNombre;
        this.empApellido = empApellido;
        this.empCedula = empCedula;
        this.empDireccion = empDireccion;
        this.empCargo = empCargo;
        this.empSeguro = empSeguro;
    }

    public EMPLEADOS(String empNombre, String empApellido, String empCedula, String empDireccion, String empCargo, String empSeguro) {
        this.empNombre = empNombre;
        this.empApellido = empApellido;
        this.empCedula = empCedula;
        this.empDireccion = empDireccion;
        this.empCargo = empCargo;
        this.empSeguro = empSeguro;
    }

    public EMPLEADOS() {
        
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getEmpNombre() {
        return empNombre;
    }

    public void setEmpNombre(String empNombre) {
        this.empNombre = empNombre;
    }

    public String getEmpApellido() {
        return empApellido;
    }

    public void setEmpApellido(String empApellido) {
        this.empApellido = empApellido;
    }

    public String getEmpCedula() {
        return empCedula;
    }

    public void setEmpCedula(String empCedula) {
        this.empCedula = empCedula;
    }

    public String getEmpDireccion() {
        return empDireccion;
    }

    public void setEmpDireccion(String empDireccion) {
        this.empDireccion = empDireccion;
    }

    public String getEmpCargo() {
        return empCargo;
    }

    public void setEmpCargo(String empCargo) {
        this.empCargo = empCargo;
    }

    public String getEmpSeguro() {
        return empSeguro;
    }

    public void setEmpSeguro(String empSeguro) {
        this.empSeguro = empSeguro;
    }

    public void guardar() {
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "INSERT INTO empleados (empNombre,empApellido,empCedula,empDireccion,empCargo,empSeguro)VALUES (?,?,?,?,?,?)";
        try {
            ps = cnn.prepareStatement(sql);
            ps.setString(1, getEmpNombre());
            ps.setString(2, getEmpApellido());
            ps.setString(3, getEmpCedula());
            ps.setString(4, getEmpDireccion());
            ps.setString(5, getEmpCargo());
            ps.setString(6, getEmpSeguro());

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

    public ArrayList<EMPLEADOS> listarEmpleados() {
     Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        

        ArrayList<EMPLEADOS> empleadosArray = new ArrayList<EMPLEADOS>();

        try {

            CallableStatement statement = cnn.prepareCall("{call SP_S_Empleado}");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                EMPLEADOS empArrayList = new EMPLEADOS();

                empArrayList.setIdEmpleado(rs.getInt("idEmpleado"));
                empArrayList.setEmpNombre(rs.getString("empNombre"));
                empArrayList.setEmpApellido(rs.getString("empApellido"));
                empArrayList.setEmpCedula(rs.getString("empCedula"));
                empArrayList.setEmpDireccion(rs.getString("empDireccion"));
                empArrayList.setEmpCargo(rs.getString("empCargo"));
                empArrayList.setEmpSeguro(rs.getString("empSeguro"));
                empleadosArray.add(empArrayList);
            }
            return empleadosArray;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet listarEmpleadoPorParametro(String criterio, String busqueda) throws Exception {
        System.out.println("Criterio: " + criterio);
        System.out.println("Busqueda: " + busqueda);
        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();

        ResultSet rs = null;
        try {
            CallableStatement st = cnn.prepareCall("{call SP_S_EmpleadoParametro(?,?)}");
            st.setString("pcriterio", criterio);
            st.setString("pbusqueda", busqueda);
            rs = st.executeQuery();
            System.out.println("" + criterio);
            System.out.println("" + busqueda);
            System.out.println("Busqueda realizada: " + rs);
            return rs;
        } catch (SQLException SQLex) {
            throw SQLex;
        }
    }

    public boolean actualizarDatos() throws SQLException {
      Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;
        String sql;
        sql = "UPDATE empleados SET   empNombre=?,empApellido=?,empCedula=?, empDireccion=?,empCargo=?,empSeguro=? WHERE idEmpleado=" + getIdEmpleado();

        ps = cnn.prepareStatement(sql);
        ps.setString(1, getEmpNombre());
        ps.setString(2, getEmpApellido());
        ps.setString(3, getEmpCedula());
        ps.setString(4, getEmpDireccion());
        ps.setString(5, getEmpCargo());
        ps.setString(6, getEmpSeguro());
        System.out.println(sql);
        int ru = ps.executeUpdate();
        cnn.close();
        ps.close();
        if (ru > 0) {
            System.out.println("Empleado Actualizado con exito");
            JOptionPane.showMessageDialog(null, "Empleado Actualizado con exito", "Actualizar Registro", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo Actualizar el Registro", "Actualizar Registro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void eliminarDatos(String strCodigo) throws SQLException {

        Conexion cc=new Conexion();
    Connection cnn=cc.ObtenerConexion();
        PreparedStatement ps = null;

        String sql;

        sql = "DELETE FROM empleados WHERE idEmpleado = " + strCodigo;
        try {
            ps = cnn.prepareStatement(sql);

            int n = ps.executeUpdate();

            if (n > 0) {
                System.out.println("Empleado Eliminado con exito");
                JOptionPane.showMessageDialog(null, "Empleado Eliminado con exito", "Eliminar Registro", JOptionPane.INFORMATION_MESSAGE);
            }

            cnn.close();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se logro Eliminar el Registro..." + ex, "Eliminar Registro", JOptionPane.ERROR_MESSAGE);

        }
    }

}


