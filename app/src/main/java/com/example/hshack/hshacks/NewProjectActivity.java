package com.example.hshack.hshacks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class NewProjectActivity extends AppCompatActivity {

    EditText newProjectField, dueOn, hoursNeeded;
    ListView listView;
    Button submit;
    public int GLOBALCOUNT = 0;
    public Calendar[] calendars;
    public Calendar cal;

    ArrayList<String> uids = new ArrayList<>();
    ArrayList<String> checkedUids = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> events = new ArrayList<>();
    public ArrayList<ArrayList<ArrayList<Integer>>> finalList = new ArrayList<ArrayList<ArrayList<Integer>>>();
    public ArrayList<ArrayList<ArrayList<Integer>>> dailyRestrictions;


    ArrayAdapter<String> adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference emails, users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        adapter = new ArrayAdapter<>(NewProjectActivity.this, android.R.layout.simple_list_item_multiple_choice);

        emails = database.getReference("userEmail");
        users = database.getReference("Users");
        emails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    adapter.add(d.getValue().toString());
                    Toast.makeText(getApplicationContext(), d.getValue().toString(), Toast.LENGTH_LONG).show();
                    uids.add(d.getKey());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        newProjectField = (EditText) findViewById(R.id.newProjectText);
        dueOn = (EditText) findViewById(R.id.dueOnField);
        hoursNeeded = (EditText) findViewById(R.id.timeComplete);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setItemsCanFocus(false);
        // we want multiple clicks
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new CheckBoxClick());

        submit = (Button) findViewById(R.id.submit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cal = Calendar.getInstance();
                Calendar targetCal = Calendar.getInstance();
                targetCal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(dueOn.getText().toString()));

                dailyRestrictions = new ArrayList<ArrayList<ArrayList<Integer>>>();

                //Toast.makeText(getApplicationContext(), Integer.parseInt(dueOn.getText().toString()), Toast.LENGTH_LONG).show();

                for (GLOBALCOUNT = 0; GLOBALCOUNT < Integer.parseInt(dueOn.getText().toString()); GLOBALCOUNT++) {
                    users.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<ArrayList<ArrayList<Integer>>> masterArr = new ArrayList<ArrayList<ArrayList<Integer>>>();
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                if (checkedUids.contains(d.getKey())) {
                                    ArrayList<ArrayList<Integer>> currRestrictions = new ArrayList<ArrayList<Integer>>();
                                    DatabaseReference subUser = users.child(d.getKey());
                                    subUser.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                                                StringTokenizer st = new StringTokenizer(d1.getValue().toString());
                                                if (Integer.parseInt(st.nextToken()) == 1) {
                                                    if (!(Integer.parseInt(st.nextToken()) == (int) cal.get(Calendar.DAY_OF_WEEK))) {
                                                        continue;
                                                    }
                                                } else {
                                                    Calendar tempCal = Calendar.getInstance();
                                                    tempCal.set(Calendar.YEAR, Integer.parseInt(st.nextToken()));
                                                    tempCal.set(Calendar.MONTH, Integer.parseInt(st.nextToken()));
                                                    tempCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(st.nextToken()));

                                                    if (!calEqual(tempCal, cal)) {
                                                        continue;
                                                    }
                                                }

                                                ArrayList<Integer> temp = new ArrayList<Integer>();
                                                temp.add(convertToMins(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
                                                temp.add(convertToMins(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));


                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });

                                    masterArr.add(getAvailableTime(getRemainingMinutes(currRestrictions)));
                                }

                            }
                            dailyRestrictions.add(master_compare(masterArr));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    Toast.makeText(getApplicationContext(), dailyRestrictions.toString(), Toast.LENGTH_LONG).show();
                }

                /*
                calendars = new Calendar[Integer.parseInt(dueOn.getText().toString())];


                calendars[0] = cal;
                for (int i = 1; i < calendars.length; i++) {
                    calendars[i] = (Calendar) calendars[i - 1].clone();
                    calendars[i].add(Calendar.DAY_OF_MONTH, 1);
                }


                for (GLOBALCOUNT = 0; GLOBALCOUNT < Integer.parseInt(dueOn.getText().toString()); GLOBALCOUNT++) {


                    users.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                if (checkedUids.contains(d.getKey())) {
                                    DatabaseReference subUser = users.child(d.getKey());
                                    subUser.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                                                StringTokenizer st = new StringTokenizer(d1.getValue().toString());
                                                if (st.nextToken().equals("0")) {
                                                    if (Integer.parseInt(st.nextToken()) == (int) calendars[GLOBALCOUNT].get(Calendar.YEAR)) {
                                                        if (Integer.parseInt(st.nextToken()) == (int) calendars[GLOBALCOUNT].get(Calendar.MONTH)) {
                                                            if (Integer.parseInt(st.nextToken()) == (int) calendars[GLOBALCOUNT].get(Calendar.DAY_OF_MONTH)) {

                                                            } else {
                                                                continue;
                                                            }
                                                        } else {
                                                            continue;
                                                        }
                                                    } else {
                                                        continue;
                                                    }
                                                } else {
                                                    if ((int) calendars[GLOBALCOUNT].get(Calendar.DAY_OF_WEEK) == Integer.parseInt(st.nextToken())) {

                                                    } else {
                                                        continue;
                                                    }
                                                }

                                                ArrayList<Integer> temp = new ArrayList<Integer>();
                                                temp.add(0, convertToMins(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
                                                temp.add(1, convertToMins(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));

                                                events.add(temp);
                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    count++;
                                }

                                finalList.add(getAvailableTime(getRemainingMinutes(events)));

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    ArrayList<ArrayList<Integer>> finalRanges = master_compare(finalList);
                    Toast.makeText(getApplicationContext(), finalRanges.toString(), Toast.LENGTH_LONG).show();
                } */




            }

        });
    }

    private boolean calEqual(Calendar c1, Calendar c2) {
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
            if (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
                if (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static int convertToMins(int hr, int min) {
        return hr * 60 + min;
    }

    public class CheckBoxClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            CheckedTextView ctv = (CheckedTextView)arg1;
            if(ctv.isChecked()){
                checkedUids.add(uids.get(arg2));
            } else {
                checkedUids.remove(checkedUids.indexOf(uids.get(arg2)));
            }
        }
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


