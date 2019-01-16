package com.gelcen.robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Gelcen
 */
public class Main 
{

    public static void main(String[] args) throws Exception
    {   
        //Amount of threads
        int threadsCount = 1000;
        
        File file = new File("properties.txt");
        String date;
        
        if (!file.exists())
        {
            throw new Exception("File with properties doesn't exist");
        }
        
        FileReader fileReader = new FileReader(file);      
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        //Read properties from properties file
        String url = bufferedReader.readLine();
        String login = bufferedReader.readLine();
        String password = bufferedReader.readLine();
        date = bufferedReader.readLine();
        
        if (!checkWithRegExp(date))
        {
            throw new Exception("Date format is wrong. Try YYYY-MM-DD");
        }
        
        //Converting date to Sql format
        DateReader dr = new DateReader(date);       
        int year = dr.GetYear() - 1900;        
        Date sqlDate = new Date(year, dr.GetMonth()-1, dr.GetDay());
        
        
        System.out.println(sqlDate);
        
        Locale.setDefault(Locale.ENGLISH);
        
        //Count of records in database
        int countOfSites;
     
        //Getting countOfSites
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try (Connection con = DriverManager.getConnection(url, login, password); Statement st = con.createStatement()) 
        {            
            ResultSet rs;

            rs = st.executeQuery("select count(*) from CHECKSITES");

            rs.next();
            countOfSites=rs.getInt("COUNT(*)");
            System.out.println(countOfSites);
            
            rs.close();
        }      
        
        if (countOfSites==0)
        {
            throw new Exception("Database is empty");
        }
        else if(countOfSites <= threadsCount)
        {
            //if count of sites is lower than amount of threads
            threadsCount = 2;
        }
        
        //List for threads
        ArrayList<Robot> RobotList= new ArrayList<>();
        
        //Setting indexes for every thread
        int perThread = countOfSites / threadsCount;
        int a=1, b=a+perThread;
        RobotList.add(new Robot(url, login, password, sqlDate, a, b));
        while (b+perThread<countOfSites)
        {
            a=b+1;
            b=a+perThread;
            RobotList.add(new Robot(url, login, password, sqlDate, a, b));
        }
        a=b+1;
        RobotList.add(new Robot(url, login, password, sqlDate, a, countOfSites));
        
        //Starting threads
        ExecutorService executorService = Executors.newFixedThreadPool(RobotList.size());
        for (int i=0;i< RobotList.size(); i++)
        {
            executorService.submit(RobotList.get(i));
        }

    }
    public static boolean checkWithRegExp(String userNameString){  
        Pattern p = Pattern.compile("(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)");  
        Matcher m = p.matcher(userNameString);  
        return m.matches();  
    }
}

