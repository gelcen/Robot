/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gelcen.robot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Gelcen
 */
public class Robot implements Runnable 
{
    private String _url;
    private String _user;
    private String _password;
    Date _checkDate;
    private int _a;
    private int _b;
    public int GetA()
    {
        return _a;
    }
    public int GetB()
    {
        return _b;
    }
    public Robot(String url, String user, String password, Date checkDate, int a, int b)
    {
        _url = new String(url);
        _user= new String(user);
        _password = new String(password);
        _checkDate=(Date)checkDate.clone();
        _a = a;
        _b = b;
    }
    
    @Override
    public void run() 
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(_url, _user, _password); Statement st = con.createStatement()) 
        {
            for (int i=_a; i < _b; i++)
            {
                ResultSet rs = st.executeQuery("select * from CHECKSITES where id='" + i +"'");
                rs.next();
                if (rs.getDate(3).before(_checkDate))
                {
                    String urlFromDB = rs.getString(2);
                    
                    HttpURLConnection connection = null;
                    URL u = new URL(urlFromDB);
                    connection = (HttpURLConnection) u.openConnection();
                    connection.setRequestMethod("HEAD");
                    int code = connection.getResponseCode();
                    System.out.println("" + code);              
                    connection.disconnect();
                    
                    String sql = "update CHECKSITES set status = "+code+" where id= "+i+" ";
                    st.executeUpdate(sql);
                }
                rs.close();
            }

            
        } catch (SQLException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            //Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
