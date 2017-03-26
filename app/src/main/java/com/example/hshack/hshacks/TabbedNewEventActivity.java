package com.example.hshack.hshacks;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class TabbedNewEventActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private static FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static String mUserId = mFirebaseUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_new_event);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed_new_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance() {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_one, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            final EditText name = (EditText) rootView.findViewById(R.id.eventName1);
            final CalendarView calendarView = (CalendarView) rootView.findViewById(R.id.calendarView2);
            calendarView.setEnabled(true);
            final TimePicker startTime1 = (TimePicker) rootView.findViewById(R.id.startTime1);
            final TimePicker endTime1 = (TimePicker) rootView.findViewById(R.id.endTime1);
            Button submit = (Button) rootView.findViewById(R.id.submit1);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date(calendarView.getDate()));
                    System.out.println(startTime1.getHour());
                    mDatabase.child("Users").child(mUserId).child(name.getText().toString() + "0").setValue("0 " + cal.get(Calendar.YEAR) + " " + (cal.get(Calendar.MONTH) + 1) + " " + cal.get(Calendar.DAY_OF_MONTH) + " " + startTime1.getHour() + " " + startTime1.getMinute() + " " + endTime1.getHour()
                    + " " + endTime1.getMinute());
                }
            });

            textView.setText("Non-Repeating Event");
            return rootView;
        }
    }

    public static class PlaceholderFragmentTwo extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        public PlaceholderFragmentTwo() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragmentTwo newInstance() {
            PlaceholderFragmentTwo fragment = new PlaceholderFragmentTwo();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_two, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            final EditText name = (EditText) rootView.findViewById(R.id.eventName2);
            final TimePicker startTime1 = (TimePicker) rootView.findViewById(R.id.startTime2);
            final TimePicker endTime1 = (TimePicker) rootView.findViewById(R.id.endTime2);
            Button submit = (Button) rootView.findViewById(R.id.submit2);
            final CheckBox sunday = (CheckBox) rootView.findViewById(R.id.sunday);
            final CheckBox monday = (CheckBox) rootView.findViewById(R.id.monday);
            final CheckBox tuesday = (CheckBox) rootView.findViewById(R.id.Tuesday);
            final CheckBox wednesday = (CheckBox) rootView.findViewById(R.id.wednesday);
            final CheckBox thursday = (CheckBox) rootView.findViewById(R.id.thursday);
            final CheckBox friday = (CheckBox) rootView.findViewById(R.id.friday);
            final CheckBox saturday = (CheckBox) rootView.findViewById(R.id.saturday);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    if (sunday.isChecked()) mDatabase.child("Users").child(mUserId).child(name.getText().toString() + "1").setValue("1 1 " + startTime1.getHour() + " " + startTime1.getMinute() + " " + endTime1.getHour() + " " + endTime1.getMinute());
                    if (monday.isChecked()) mDatabase.child("Users").child(mUserId).child(name.getText().toString() + "2").setValue("1 2 " + startTime1.getHour() + " " + startTime1.getMinute() + " " + endTime1.getHour() + " " + endTime1.getMinute());
                    if (tuesday.isChecked()) mDatabase.child("Users").child(mUserId).child(name.getText().toString() + "3").setValue("1 3 " + startTime1.getHour() + " " + startTime1.getMinute() + " " + endTime1.getHour() + " " + endTime1.getMinute());
                    if (wednesday.isChecked()) mDatabase.child("Users").child(mUserId).child(name.getText().toString() + "4").setValue("1 4 " + startTime1.getHour() + " " + startTime1.getMinute() + " " + endTime1.getHour() + " " + endTime1.getMinute());
                    if (thursday.isChecked()) mDatabase.child("Users").child(mUserId).child(name.getText().toString() + "5").setValue("1 5 " + startTime1.getHour() + " " + startTime1.getMinute() + " " + endTime1.getHour() + " " + endTime1.getMinute());
                    if (friday.isChecked()) mDatabase.child("Users").child(mUserId).child(name.getText().toString() + "6").setValue("1 6 " + startTime1.getHour() + " " + startTime1.getMinute() + " " + endTime1.getHour() + " " + endTime1.getMinute());
                    if (saturday.isChecked()) mDatabase.child("Users").child(mUserId).child(name.getText().toString() + "7").setValue("1 7 " + startTime1.getHour() + " " + startTime1.getMinute() + " " + endTime1.getHour() + " " + endTime1.getMinute());

                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);
                    return;
                }
            });
            textView.setText("Repeating Event");
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return PlaceholderFragment.newInstance();
                case 1: return PlaceholderFragmentTwo.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
