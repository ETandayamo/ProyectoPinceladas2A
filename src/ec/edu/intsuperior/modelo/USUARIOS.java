/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.intsuperior.modelo;

/**
 *
 * @author User
 */
public class USUARIOS {
    
    private String usuUsuario;
    private String paUsuario;
   

    public USUARIOS() {
    }

    public USUARIOS(Integer idEmpleado, String usuUsuario, String usuClave, String paUsario, String usuFechaCreacion) {
        
        this.usuUsuario = usuUsuario;
        
        this.paUsuario = paUsario;
        
    }

    public USUARIOS(Integer idUsuario, Integer idEmpleado, String usuUsuario, String usuClave, String paUsario, String usuFechaCreacion) {
       
        this.usuUsuario = usuUsuario;
        
        this.paUsuario = paUsario;
       
    }

    public String getUsuUsuario() {
        return usuUsuario;
    }

    public void setUsuUsuario(String usuUsuario) {
        this.usuUsuario = usuUsuario;
    }


    public String getPaUsuario() {
        return paUsuario;
    }

    public void setPaUsuario(String paUsuario) {
        this.paUsuario = paUsuario;
    }


    
}
