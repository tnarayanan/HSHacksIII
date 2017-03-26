package com.example.hshack.hshacks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class NewProjectActivity extends AppCompatActivity {

    EditText newProjectField, dueOn, hoursNeeded;
    ListView listView;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        newProjectField = (EditText) findViewById(R.id.newProjectText);
        dueOn = (EditText) findViewById(R.id.dueOnField);
        hoursNeeded = (EditText) findViewById(R.id.timeComplete);

        listView = (ListView) findViewById(R.id.listView);

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
