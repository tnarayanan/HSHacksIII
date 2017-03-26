package com.example.hshack.hshacks;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hshack on 3/25/17.
 */

public class ProjectFunctionTest {

    public static void main(String[] args) {
        /*ArrayList<Integer> mins = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 7, 8, 9, 11));
        for(int i = 1; i <= 1440; i++) {
            mins.add(i);
        }
        //System.out.println(mins.toString() + "\n" + getAvailableTime(mins).toString());*//*
        ArrayList<ArrayList<Integer>> eventTimes = new ArrayList<>();
        eventTimes.add(new ArrayList<Integer>(Arrays.asList(720, 1440)));
        eventTimes.add(new ArrayList<Integer>(Arrays.asList(100, 200)));
       // System.out.println((eventTimes.get(0).get(1).equals(mins.get(mins.size() - 1))) + " " + eventTimes.get(0).get(1) + " " +mins.get(mins.size() - 1));
        System.out.print(getAvailableTime(getRemainingMinutes(eventTimes)));*/

        //ArrayList<ArrayList<ArrayList<Integer>>> finalList = new ArrayList<>()

        //System.out.print(compareFreeTime(new ArrayList<ArrayList<ArrayList<Integer>>>(Arrays.asList(Arrays.asList<>(new ArrayList<Integer>(Arrays.asList(1,8))), new ArrayList<Integer>(Arrays.asList(50,70)))), new ArrayList<ArrayList<Integer>>(Arrays.asList(new ArrayList<Integer>(Arrays.asList(2,10))))).toString());
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

    public static ArrayList<ArrayList<Integer>> compareFreeTime(ArrayList<ArrayList<Integer>> availTime, ArrayList<ArrayList<Integer>>availTime2) {
        ArrayList<ArrayList<Integer>> finalFreeTime = new ArrayList<>();


        for(int i = 0; i < availTime.size(); i++) {
            for(int j = 0; j < availTime2.size(); j++){
                if(availTime2.get(j).get(0) < availTime.get(i).get(1) && availTime2.get(j).get(1) > availTime.get(i).get(0)){
                    finalFreeTime.add(new ArrayList<Integer>(Arrays.asList(Math.max(availTime.get(i).get(0), availTime2.get(j).get(0)), Math.min(availTime.get(i).get(1), availTime2.get(j).get(1)))));
                    availTime.remove(i);
                    i--;
                    availTime2.remove(j);
                    j--;
                }
            }
        }

        return finalFreeTime;

    }

    public static ArrayList<ArrayList<Integer>> master_compare(ArrayList<ArrayList<ArrayList<Integer>>> list) {
        ArrayList<ArrayList<Integer>> curr = compareFreeTime(list.get(0), list.get(1));
        for (int i = 2; i < list.size(); i++) {
            curr = compareFreeTime(curr, list.get(i));
        }

        return curr;
    }

}
