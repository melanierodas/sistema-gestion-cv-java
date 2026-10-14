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


public class clsUsuarios {
    public boolean login(String usuario, String password){
        boolean acceso = false; 
        try{
            clsConexion Conexion = new clsConexion();
            Connection Con = Conexion.conectar();
            String consulta = "SELECT * FROM USUARIOS "
                    +"WHERE USUARIO=? AND PASSWORD=?"; 
            
            PreparedStatement cmd = Con.prepareStatement(consulta);
            cmd.setString(1, usuario); 
            cmd.setString(2, password); 
            ResultSet rs = cmd.executeQuery(); 
            if(rs.next()){
                acceso = true; 
            }
            rs.close(); 
            cmd.close();
            Con.close();
                    
       }catch(Exception ex){
          System.out.println("Error en Login: " + ex.getMessage());  
       }
        
        return acceso; 
        
    }

}
