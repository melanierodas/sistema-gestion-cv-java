/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BACKEND;

/**
 *
 * @author famro
 */
import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.util.logging.Level;
import java.util.logging.Logger;


public class clsExperiencia {
    private int idExperiencia; 
    private String correoCandidato; 
    private String Empresa; 
    private String Puesto; 
    private String Funciones; 
    private String FechaInicio; 
    private String FechaFinal; 
    

    public clsExperiencia() {
    }

    public clsExperiencia(int idExperiencia, String correoCandidato, String Empresa, String Puesto, String Funciones, String FechaInicio, String FechaFinal) {
        this.idExperiencia = idExperiencia;
        this.correoCandidato = correoCandidato;
        this.Empresa = Empresa;
        this.Puesto = Puesto;
        this.Funciones = Funciones;
        this.FechaInicio = FechaInicio;
        this.FechaFinal = FechaFinal;
    }

    public int getIdExperiencia() {
        return idExperiencia;
    }

    public void setIdExperiencia(int idExperiencia) {
        this.idExperiencia = idExperiencia;
    }

    public String getCorreoCandidato() {
        return correoCandidato;
    }

    public void setCorreoCandidato(String correoCandidato) {
        this.correoCandidato = correoCandidato;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String Empresa) {
        this.Empresa = Empresa;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String Puesto) {
        this.Puesto = Puesto;
    }

    public String getFunciones() {
        return Funciones;
    }

    public void setFunciones(String Funciones) {
        this.Funciones = Funciones;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public String getFechaFinal() {
        return FechaFinal;
    }

    public void setFechaFinal(String FechaFinal) {
        this.FechaFinal = FechaFinal;
    }
    
    
    
    public int fncIngresar(clsExperiencia exp){
        int Resp = 0; 
        
        try{
            clsConexion Conexion = new clsConexion(); 
            Connection Con = Conexion.conectar(); 
            String Consulta = "INSERT INTO EXPERIENCIA (CORREO_CANDIDATO, EMPRESA, PUESTO, FUNCIONES, FECHA_INICIO, FECHA_FINAL) "
                            + "VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement cmd = Con.prepareStatement(Consulta); 
            cmd.setString(1, exp.getCorreoCandidato()); 
            cmd.setString(2, exp.getEmpresa()); 
            cmd.setString(3, exp.getPuesto()); 
            cmd.setString(4, exp.getFunciones()); 
            cmd.setString(5, exp.getFechaInicio()); 
            cmd.setString(6, exp.getFechaFinal()); 
            
            cmd.executeUpdate(); 
            Con.close(); 
            Resp = 1; 
            
        }catch(SQLException ex){
            System.out.println("Error SQL: " + ex.getMessage()); 
            Resp = -1; 
            
        }catch (Exception ex) {
            Logger.getLogger(clsExperiencia.class.getName()).log(Level.SEVERE, null, ex);
            Resp = -1;
        }
        
        return Resp; 
    }
    
    public java.util.ArrayList<clsExperiencia> fncListarPorCandidato(String pCorreo) {
        java.util.ArrayList<clsExperiencia> lista = new java.util.ArrayList<>();
        try {
            clsConexion Conexion = new clsConexion(); 
            java.sql.Connection Con = Conexion.conectar(); 
            String Consulta = "SELECT * FROM EXPERIENCIA WHERE CORREO_CANDIDATO=?";
            java.sql.PreparedStatement cmd = Con.prepareStatement(Consulta); 
            cmd.setString(1, pCorreo);
            java.sql.ResultSet rs = cmd.executeQuery(); 

            while(rs.next()) {
                clsExperiencia exp = new clsExperiencia(); 
                exp.setIdExperiencia(rs.getInt("ID_EXPERIENCIA"));
                exp.setCorreoCandidato(rs.getString("CORREO_CANDIDATO"));
                exp.setEmpresa(rs.getString("EMPRESA"));
                exp.setPuesto(rs.getString("PUESTO"));
                exp.setFunciones(rs.getString("FUNCIONES"));
                exp.setFechaInicio(rs.getString("FECHA_INICIO"));
                exp.setFechaFinal(rs.getString("FECHA_FINAL"));
                lista.add(exp);
            }
            Con.close();
        } catch(Exception ex) {
            System.out.println("Error al listar experiencias web: " + ex.getMessage()); 
        }
        return lista; 
    }
    
    public int fncModificar(clsExperiencia exp) {
    int respuesta = 0;

    try {
        clsConexion conexion = new clsConexion();
        java.sql.Connection cn = conexion.conectar();

        String sql = "UPDATE EXPERIENCIA SET "
                   + "EMPRESA = ?, "
                   + "PUESTO = ?, "
                   + "FUNCIONES = ?, "
                   + "FECHA_INICIO = ?, "
                   + "FECHA_FINAL = ? "
                   + "WHERE ID_EXPERIENCIA = ?";

        java.sql.PreparedStatement ps = cn.prepareStatement(sql);

        ps.setString(1, exp.getEmpresa());
        ps.setString(2, exp.getPuesto());
        ps.setString(3, exp.getFunciones());
        ps.setString(4, exp.getFechaInicio());
        ps.setString(5, exp.getFechaFinal());
        ps.setInt(6, exp.getIdExperiencia());

        respuesta = ps.executeUpdate();

        ps.close();
        cn.close();

    } catch (Exception e) {
        System.out.println("Error al modificar experiencia: " + e.getMessage());
    }

    return respuesta;
}

public int fncEliminar(int id) {
    int Resp = 0;
    Connection Con = null;
    PreparedStatement cmd = null;
    
    // Eliminamos por ID_EXPERIENCIA, que es la llave primaria única
    String Consulta = "DELETE FROM EXPERIENCIA WHERE ID_EXPERIENCIA = ?";
    
    try {
        clsConexion Conexion = new clsConexion();
        Con = Conexion.conectar();
        cmd = Con.prepareStatement(Consulta);
        cmd.setInt(1, id);
        
        int filasAfectadas = cmd.executeUpdate();
        
        if (filasAfectadas > 0) {
            Resp = 1; // Se eliminó con éxito
        } else {
            Resp = 0; // No se encontró el ID
        }
    } catch (Exception ex) {
        System.out.println("Error al eliminar experiencia: " + ex.getMessage());
        Resp = -1;
    } finally {
        try {
            if (cmd != null) cmd.close();
            if (Con != null) Con.close();
        } catch (Exception e) {
            System.out.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
    return Resp;
}

public java.util.ArrayList<clsExperiencia> fncListarPorCorreo(String correoCandidato) {
    java.util.ArrayList<clsExperiencia> lista = new java.util.ArrayList<>();

    try {
        clsConexion conexion = new clsConexion();
        java.sql.Connection cn = conexion.conectar();

        String sql = "SELECT ID_EXPERIENCIA, EMPRESA, PUESTO, FECHA_INICIO, FECHA_FINAL, FUNCIONES "
                   + "FROM EXPERIENCIA "
                   + "WHERE CORREO_CANDIDATO = ?";

        java.sql.PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, correoCandidato);

        java.sql.ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            clsExperiencia exp = new clsExperiencia();

            exp.setIdExperiencia(rs.getInt("ID_EXPERIENCIA"));
            exp.setEmpresa(rs.getString("EMPRESA"));
            exp.setPuesto(rs.getString("PUESTO"));
            exp.setFechaInicio(rs.getString("FECHA_INICIO"));
            exp.setFechaFinal(rs.getString("FECHA_FINAL"));
            exp.setFunciones(rs.getString("FUNCIONES"));

            lista.add(exp);
        }

        rs.close();
        ps.close();
        cn.close();

    } catch (Exception e) {
        System.out.println("Error al listar experiencias por correo: " + e.getMessage());
    }

    return lista;
}
}
