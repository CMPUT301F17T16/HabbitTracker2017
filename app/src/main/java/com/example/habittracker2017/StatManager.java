/*
*StatManager
*
* version 1.0
*
* Dec 3, 2017
*
*Copyright (c) 2017 Team 16, CMPUT301, University of Alberta - All Rights Reserved.
*You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
*You can find a copy of the license in this project. Otherwise please contact contact@abc.ca.
*
*/

package com.example.habittracker2017;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 *
 * @author team 16
 * @version 1.0
 * @since 1.0
 */

public class StatManager {

    private static HashMap<Integer, Boolean> schedule;
    private static ArrayList<String> dueDates;
    private static ArrayList<HabitEvent> events;

    /**
     * returns an array list with floats of
     * percent of completed habits,
     * percent of missed habits,
     * number of missed habits,
     * and number of completed habits.
     *
     * @param startDate
     * @param endDate
     * @param habit
     * @return
     */
    public static ArrayList<Float> completedStats(Date startDate, Date endDate, Habit habit){

        schedule = habit.getSchedule();
        dueDates = daysBetween(startDate, endDate, schedule);
        int maxDays = dueDates.size();

        /*
        Find number of missed days by removing the amount of days completed from the valid days list
        and finding the number of remaining days
         */
        events = habit.getEvents();
        for (HabitEvent events : events){
            DateTime completedDay = new DateTime(events.getDate());
            DateTimeFormatter fmt = DateTimeFormat.forPattern("DD-MM-yyyy");
            String strCompletedDay = fmt.print(completedDay);
            if (dueDates.contains(strCompletedDay)){
                dueDates.remove(strCompletedDay);
            }
        }
        int missedDays = dueDates.size();

        //Get the percentage of days completed and percentage of days missed
        float miss = (missedDays/(float)maxDays)*100;
        float complete = (100-miss);

        ArrayList<Float> completedPercent = new ArrayList<>();
        completedPercent.add(complete);
        completedPercent.add(miss);
        completedPercent.add((float)missedDays);//number of missed days
        completedPercent.add((float)maxDays-missedDays); // number of complete days
        return completedPercent;

    }

    /**
     *
     * returns an array list with list of dates that the habit was due
     *
     * @param begin
     * @param end
     * @param daysOfWeek
     * @return
     */
    public static ArrayList<String> daysBetween(Date begin, Date end, HashMap<Integer, Boolean> daysOfWeek){
        ArrayList<String> days = new ArrayList<>();

        DateTime startDay = new DateTime(begin);
        DateTime endDay = new DateTime(end);
        Log.i("days",endDay.toString());
        DateTimeFormatter formater = DateTimeFormat.forPattern("DD-MM-yyyy");


        while (startDay.isBefore(endDay)){
            if ( startDay.getDayOfWeek() == DateTimeConstants.MONDAY && daysOfWeek.get(2)== Boolean.TRUE){
                String strStartDay = formater.print(startDay);
                days.add(strStartDay);
            }
            if ( startDay.getDayOfWeek() == DateTimeConstants.TUESDAY && daysOfWeek.get(3)== Boolean.TRUE){
                String strStartDay = formater.print(startDay);
                days.add(strStartDay);
            }
            if ( startDay.getDayOfWeek() == DateTimeConstants.WEDNESDAY && daysOfWeek.get(4)== Boolean.TRUE){
                String strStartDay = formater.print(startDay);
                days.add(strStartDay);
            }
            if ( startDay.getDayOfWeek() == DateTimeConstants.THURSDAY && daysOfWeek.get(5)== Boolean.TRUE){
                String strStartDay = formater.print(startDay);
                days.add(strStartDay);
            }
            if ( startDay.getDayOfWeek() == DateTimeConstants.FRIDAY && daysOfWeek.get(6)== Boolean.TRUE){
                String strStartDay = formater.print(startDay);
                days.add(strStartDay);
            }
            if ( startDay.getDayOfWeek() == DateTimeConstants.SATURDAY && daysOfWeek.get(7)== Boolean.TRUE){
                String strStartDay = formater.print(startDay);
                days.add(strStartDay);
            }
            if ( startDay.getDayOfWeek() == DateTimeConstants.SUNDAY && daysOfWeek.get(1)== Boolean.TRUE){
                String strStartDay = formater.print(startDay);
                days.add(strStartDay);
            }
            startDay = startDay.plusDays(1);
        }

        return days;
    }

}