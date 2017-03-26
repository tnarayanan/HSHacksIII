package com.example.hshack.hshacks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    private final String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    CalendarView calendarView;
    ListView listView;
    FloatingActionButton fab;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    private void loadLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {

            calendarView = (CalendarView) findViewById(R.id.calendarView);
            listView = (ListView) findViewById(R.id.listView);
            fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), NewEventActivity.class);
                    startActivity(i);
                }
            });

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    //BufferedReader calFile;
                    StringTokenizer st;


/*

                try {
                    calFile = new BufferedReader(new InputStreamReader(getApplicationContext().openFileInput("localCal.txt")));
                    st = new StringTokenizer(calFile.readLine());
                    while (st.hasMoreTokens()) {
                        int currDay = Integer.parseInt(st.nextToken());
                        Log.d("LOLLLLLLLLLLLLLLLLLLLLL", currDay + " " + selectedDay);
                        if (selectedDay == currDay) {
                            String timeStr = st.nextToken() + ":" + st.nextToken() + " to " + st.nextToken() + ":" + st.nextToken();
                            String temp;
                            while ((temp = st.nextToken()).equals("-1")) {
                                timeStr += temp + " ";
                            }
                        }
                        st = new StringTokenizer(calFile.readLine());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


*/



                    final Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    final int selectedDay = calendar.get(Calendar.DAY_OF_WEEK);

                    myRef = database.getReference().child("Users").child(mFirebaseUser.getUid());
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);

                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                String value = d.getValue(String.class);
                                StringTokenizer st = new StringTokenizer(value);
                                int recurring = Integer.parseInt(st.nextToken());


                                if (recurring == 1) {
                                    int currDay = Integer.parseInt(st.nextToken());
                                    if (currDay == selectedDay) {
                                        String timeStr = st.nextToken() + ":" + st.nextToken() + " to " + st.nextToken() + ":" + st.nextToken();
                                        adapter.add(d.getKey());
                                    }
                                } else {
                                    Calendar calendar1 = Calendar.getInstance();
                                    calendar1.set(Calendar.YEAR, Integer.parseInt(st.nextToken()));
                                    calendar1.set(Calendar.MONTH, Integer.parseInt(st.nextToken()) - 1);
                                    calendar1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(st.nextToken()));
                                    //Toast.makeText(getApplicationContext(), calendar.getTime().toString() + " " + calendar1.getTime().toString() + " " + calEqual(calendar, calendar1), Toast.LENGTH_LONG).show();

                                    if (calEqual(calendar, calendar1)) {
                                        String timeStr = st.nextToken() + ":" + st.nextToken() + " to " + st.nextToken() + ":" + st.nextToken();
                                        adapter.add(d.getKey());
                                    }
                                }

                            }


                            //Toast.makeText(getApplicationContext(), dataSnapshot.getChildrenCount() + " " + names.toString() + " " + values.toString(), Toast.LENGTH_LONG).show();


                            listView.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            });

        }


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
}
