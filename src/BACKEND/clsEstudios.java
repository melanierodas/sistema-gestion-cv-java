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
import java.sql.SQLException; 
import java.util.logging.Level;
import java.util.logging.Logger;

public class clsEstudios {
   private int idEstudio;
   private String correoCandidato;
   private String Institucion;
   private String Titulo;
   private String Descripcion;
   private String FechaInicio;
   private String FechaFinal; 

    public clsEstudios() {
    }

    public clsEstudios(int idEstudio, String correoCandidato, String Institucion, String Titulo, String Descripcion, String FechaInicio, String FechaFinal) {
        this.idEstudio = idEstudio;
        this.correoCandidato = correoCandidato;
        this.Institucion = Institucion;
        this.Titulo = Titulo;
        this.Descripcion = Descripcion;
        this.FechaInicio = FechaInicio;
        this.FechaFinal = FechaFinal;
    }

    public int getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(int idEstudio) {
        this.idEstudio = idEstudio;
    }

    public String getCorreoCandidato() {
        return correoCandidato;
    }

    public void setCorreoCandidato(String correoCandidato) {
        this.correoCandidato = correoCandidato;
    }

    public String getInstitucion() {
        return Institucion;
    }

    public void setInstitucion(String Institucion) {
        this.Institucion = Institucion;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
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
   
   
    public int fncIngresar(clsEstudios estudio){
        int Resp = 0; 
        
        try{
            clsConexion Conexion = new clsConexion(); 
            Connection Con = Conexion.conectar(); 
            String Consulta = "INSERT INTO ESTUDIOS (CORREO_CANDIDATO, INSTITUCION, TITULO, DESCRIPCION, FECHA_INICIO, FECHA_FINAL) "
                            + "VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement cmd = Con.prepareStatement(Consulta); 
            cmd.setString(1, estudio.getCorreoCandidato()); 
            cmd.setString(2, estudio.getInstitucion()); 
            cmd.setString(3, estudio.getTitulo()); 
            cmd.setString(4, estudio.getDescripcion()); 
            cmd.setString(5, estudio.getFechaInicio()); 
            cmd.setString(6, estudio.getFechaFinal()); 
            cmd.executeUpdate(); 
            Con.close(); 
            Resp = 1; 
            
        }catch(SQLException ex){
            System.out.println("Error SQL: " + ex.getMessage()); 
            Resp = -1; 
        }catch (Exception ex) {
            Logger.getLogger(clsEstudios.class.getName()).log(Level.SEVERE, null, ex);
            Resp = -1;
        }
        
        return Resp; 
    }
    
    
    public java.util.ArrayList<clsEstudios> fncListarPorCandidato(String pCorreo) {
        java.util.ArrayList<clsEstudios> lista = new java.util.ArrayList<>();
        try {
            clsConexion Conexion = new clsConexion(); 
            java.sql.Connection Con = Conexion.conectar(); 
            String Consulta = "SELECT * FROM ESTUDIOS WHERE CORREO_CANDIDATO=?";
            java.sql.PreparedStatement cmd = Con.prepareStatement(Consulta); 
            cmd.setString(1, pCorreo);
            java.sql.ResultSet rs = cmd.executeQuery(); 

            while(rs.next()) {
                clsEstudios est = new clsEstudios(); 
                est.setIdEstudio(rs.getInt("ID_ESTUDIO"));
                est.setCorreoCandidato(rs.getString("CORREO_CANDIDATO"));
                est.setInstitucion(rs.getString("INSTITUCION"));
                est.setTitulo(rs.getString("TITULO"));
                est.setDescripcion(rs.getString("DESCRIPCION"));
                est.setFechaInicio(rs.getString("FECHA_INICIO"));
                est.setFechaFinal(rs.getString("FECHA_FINAL"));
                lista.add(est);
            }
            Con.close();
        } catch(Exception ex) {
            System.out.println("Error al listar estudios web: " + ex.getMessage()); 
        }
        return lista; 
    }
    
    public int fncModificar(clsEstudios est) {
    int Resp = 0;
    try {
        clsConexion Conexion = new clsConexion();
        Connection Con = Conexion.conectar();
        String Consulta = "UPDATE ESTUDIOS SET INSTITUCION=?, TITULO=?, DESCRIPCION=?, "
                        + "FECHA_INICIO=?, FECHA_FINAL=? WHERE ID_ESTUDIO=?";
        PreparedStatement cmd = Con.prepareStatement(Consulta);
        cmd.setString(1, est.getInstitucion());
        cmd.setString(2, est.getTitulo());
        cmd.setString(3, est.getDescripcion());
        cmd.setString(4, est.getFechaInicio());
        cmd.setString(5, est.getFechaFinal());
        cmd.setInt(6, est.getIdEstudio());
        cmd.executeUpdate();
        Con.close();
        Resp = 1;
    } catch (Exception ex) {
        System.out.println("Error modificar estudio: " + ex.getMessage());
        Resp = -1;
    }
    return Resp;
}

public int fncEliminar(int pIdEstudio) {
    int Resp = 0;
    try {
        clsConexion Conexion = new clsConexion();
        Connection Con = Conexion.conectar();
        String Consulta = "DELETE FROM ESTUDIOS WHERE ID_ESTUDIO=?";
        PreparedStatement cmd = Con.prepareStatement(Consulta);
        cmd.setInt(1, pIdEstudio);
        cmd.executeUpdate();
        Con.close();
        Resp = 1;
    } catch (Exception ex) {
        System.out.println("Error eliminar estudio: " + ex.getMessage());
        Resp = -1;
    }
    return Resp;
}
}
