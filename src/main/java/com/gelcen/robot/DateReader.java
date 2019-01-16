/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gelcen.robot;

/**
 *
 * @author Gelcen
 */
public class DateReader{
    private String date;

    DateReader(String date) {
        this.date=date;
    }

    public int GetDay()
    {
        int day;

        String dayString = date.substring(8, 10);
        day = Integer.parseInt(dayString);
        
        return day;
    }
    
    public int GetMonth()
    {
        return Integer.parseInt(date.substring(5, 7));
    }
    
    public int GetYear()
    {
        return Integer.parseInt(date.substring(0, 4));
    }
}
