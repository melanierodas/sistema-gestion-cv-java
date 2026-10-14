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
import java.sql.DriverManager; 
import java.sql.SQLException; 

public class clsConexion {
    private String cadenaConexion = "jdbc:sqlserver://localhost:1433;"
        +"database=DB_RECLUTAMIENTO;"
        +"user=TU_USUARIO;"
        +"password=TU_PASSWORD;"
        +"encrypt=false;"  
        +"trustServerCertificate=true;";
    
    public Connection conectar() throws Exception{
        Connection cn = null; 
        
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
            cn=DriverManager.getConnection(cadenaConexion); 
            System.out.println("CONEXION EXITOSA"); 
            
        }catch(SQLException e){
             System.out.println("ERROR SQL: " + e.getMessage()); 
        }
       return cn; 
    }
    
    
}
