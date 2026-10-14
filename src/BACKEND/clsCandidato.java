/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BACKEND;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.SQLException; 
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;

/**
 *
 * @author famro
 */
public class clsCandidato {
    private String Correo; 
    private String Nombre;
    private String Telefono;
    private String Direccion;
    private String Foto;
    private String Perfil;

    public clsCandidato() {
    }
    
    public clsCandidato(String Correo, String Nombre, String Telefono, String Direccion, String Foto, String Perfil){
        this.Correo = Correo; 
        this.Nombre = Nombre; 
        this.Telefono = Telefono; 
        this.Direccion = Direccion; 
        this.Foto = Foto; 
        this.Perfil = Perfil; 
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String Foto) {
        this.Foto = Foto;
    }

    public String getPerfil() {
        return Perfil;
    }

    public void setPerfil(String Perfil) {
        this.Perfil = Perfil;
    }
    
    public int fncIngresar(clsCandidato candidato){
        int Resp = 0; 
        
        try{
            clsConexion Conexion = new clsConexion(); 
            Connection Con = Conexion.conectar(); 
            String Consulta = "INSERT INTO CANDIDATOS (CORREO, NOMBRE, TELEFONO, DIRECCION, FOTO, PERFIL) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement cmd = Con.prepareStatement(Consulta); 
            cmd.setString(1, candidato.getCorreo()); 
            cmd.setString(2, candidato.getNombre()); 
            cmd.setString(3, candidato.getTelefono()); 
            cmd.setString(4, candidato.getDireccion()); 
            cmd.setString(5, candidato.getFoto()); 
            cmd.setString(6, candidato.getPerfil()); 
            cmd.executeUpdate(); 
            Resp = 1; 
            Con.close();
           
        }catch(SQLException ex){
            System.out.println("Error SQL: " + ex.getMessage()); 
            Resp = -1; 
        }catch (Exception ex) {
            Logger.getLogger(clsCandidato.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return Resp; 
    }
    
   public clsCandidato fncBuscar(String pCorreo){
       clsCandidato candidato = null; 
       try{
           clsConexion Conexion = new clsConexion(); 
           Connection Con = Conexion.conectar(); 
           String Consulta = "SELECT * FROM CANDIDATOS "
                   + "WHERE CORREO=?";
           PreparedStatement cmd = Con.prepareStatement(Consulta); 
           cmd.setString(1, pCorreo); 
           ResultSet rs = cmd.executeQuery(); 
           
           if(rs.next()){
               candidato = new clsCandidato(); 
               candidato.setCorreo(rs.getString("CORREO")); 
               candidato.setNombre(rs.getString("NOMBRE"));
               candidato.setTelefono(rs.getString("TELEFONO"));
               candidato.setDireccion(rs.getString("DIRECCION"));
               candidato.setFoto(rs.getString("FOTO"));
               candidato.setPerfil(rs.getString("PERFIL"));
           }
           Con.close();
           
       }catch(Exception ex){
           System.out.println("Error: " + ex.getMessage()); 
       }
       return candidato; 
   } 
    
   public int fncModificar(clsCandidato candidato){
       int Resp = 0; 
       
       try{
           clsConexion Conexion = new clsConexion(); 
           Connection Con = Conexion.conectar();
           String Consulta = "UPDATE CANDIDATOS "
                   + "SET NOMBRE=?, TELEFONO=?, DIRECCION=?, FOTO=?, PERFIL=? "
                   + "WHERE CORREO=?"; 
           PreparedStatement cmd = Con.prepareStatement(Consulta); 
           cmd.setString(1, candidato.getNombre()); 
           cmd.setString(2, candidato.getTelefono());
           cmd.setString(3, candidato.getDireccion());
           cmd.setString(4, candidato.getFoto());
           cmd.setString(5, candidato.getPerfil());
           cmd.setString(6, candidato.getCorreo());
           cmd.executeUpdate(); 
           Resp = 1; 
           Con.close();
           
       }catch(Exception ex){
           System.out.println("Error: " + ex.getMessage());
           Resp = -1; 
       }
       return Resp; 
   }
   
   public int fncEliminar(String pCorreo){
       int Resp = 0; 
       
       try{
           clsConexion Conexion = new clsConexion(); 
           Connection Con = Conexion.conectar(); 
           String Consulta = "DELETE FROM CANDIDATOS "
                   + "WHERE CORREO=?"; 
           PreparedStatement cmd = Con.prepareStatement(Consulta); 
           cmd.setString(1, pCorreo);
           cmd.executeUpdate(); 
           Resp = 1;
           Con.close();
           
       }catch(Exception ex){
           System.out.println("Error: " + ex.getMessage()); 
           Resp = -1; 
       }
       return Resp; 
   }
   
   public java.util.ArrayList<clsCandidato> fncListarTodos(){
    java.util.ArrayList<clsCandidato> lista = new java.util.ArrayList<>();
    try {
        clsConexion Conexion = new clsConexion(); 
        Connection Con = Conexion.conectar(); 
        String Consulta = "SELECT * FROM CANDIDATOS";
        PreparedStatement cmd = Con.prepareStatement(Consulta); 
        ResultSet rs = cmd.executeQuery(); 
        
        while(rs.next()){
            clsCandidato cand = new clsCandidato(); 
            cand.setCorreo(rs.getString("CORREO")); 
            cand.setNombre(rs.getString("NOMBRE"));
            cand.setTelefono(rs.getString("TELEFONO"));
            cand.setDireccion(rs.getString("DIRECCION"));
            cand.setFoto(rs.getString("FOTO"));
            cand.setPerfil(rs.getString("PERFIL"));
            lista.add(cand);
        }
        Con.close();
        
    } catch(Exception ex) {
        System.out.println("Error al listar: " + ex.getMessage()); 
    }
    return lista; 
}
}
