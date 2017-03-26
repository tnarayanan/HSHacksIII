package com.example.hshack.hshacks;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hshack on 3/25/17.
 */

public class ProjectFunctionTest {

    public static void main(String[] args) {
        ArrayList<Integer> mins = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 7, 8, 9, 11));
        for(int i = 1; i <= 1440; i++) {
            mins.add(i);
        }
        //System.out.println(mins.toString() + "\n" + getAvailableTime(mins).toString());*/
        ArrayList<ArrayList<Integer>> eventTimes = new ArrayList<>();
        eventTimes.add(new ArrayList<Integer>(Arrays.asList(720, 1440)));
        eventTimes.add(new ArrayList<Integer>(Arrays.asList(100, 200)));
       // System.out.println((eventTimes.get(0).get(1).equals(mins.get(mins.size() - 1))) + " " + eventTimes.get(0).get(1) + " " +mins.get(mins.size() - 1));
        System.out.print(getAvailableTime(getRemainingMinutes(eventTimes)));
    }

    private static ArrayList<ArrayList<Integer>> getAvailableTime(ArrayList<Integer> minutes) {
        ArrayList<ArrayList<Integer>> freeTime = new ArrayList<>();

        int i = 0;
        int first = 0;
        boolean inRange = true;
        for(i = 0; i < minutes.size(); i++) {
            if(i + 1 < minutes.size()) {
                if (minutes.get(i) + 1 != minutes.get(i + 1)) {
                    ArrayList<Integer> timeSpace = new ArrayList<Integer>();
                    timeSpace.add(minutes.get(first));
                    timeSpace.add(minutes.get(i));
                    freeTime.add(timeSpace);
                    first = i + 1;
                }
            } else {
                ArrayList<Integer> timeSpace = new ArrayList<Integer>();
                timeSpace.add(minutes.get(first));
                timeSpace.add(minutes.get(i));
                freeTime.add(timeSpace);
            }
        }
        return freeTime;
    }
    public static ArrayList<Integer> getRemainingMinutes(ArrayList<ArrayList<Integer>> eventTimes){


        ArrayList<Integer> minutes = new ArrayList<>();
        for(int i = 1; i <= 1440; i++){
            minutes.add(i);
        }
        for(int i = 0; i < eventTimes.size(); i++){
            for(int j = minutes.size() - 1; j >= 0; j--){
                if(minutes.get(j).equals(eventTimes.get(i).get(1))){
                    //System.out.println("yes");

                    while(!minutes.get(j).equals(eventTimes.get(i).get(0)) && j > 0){
                        minutes.remove(j);
                        j--;
                    }
                    minutes.remove(j);
                }
            }
        }
        return minutes;
    }

}
