/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DATABASE;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author vitor
 */
public class DataBase {
    
    public static Connection connectDb(){
        
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/cafeaustral", "root", "");
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
    
}
