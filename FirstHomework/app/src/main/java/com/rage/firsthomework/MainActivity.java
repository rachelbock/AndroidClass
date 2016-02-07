package com.rage.firsthomework;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText plansText;
    private Button previousDay;
    private Button nextDay;
    private Spinner spinner;
    private String[] daysOfWeek;
    private int currentDay = 0;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previousDay = (Button) findViewById(R.id.main_activity_prior_day);
        Button saveButton = (Button) findViewById(R.id.main_activity_save);
        nextDay = (Button) findViewById(R.id.main_activity_next_day);
        plansText = (EditText) findViewById(R.id.main_activity_plans_field);
        spinner = (Spinner) findViewById(R.id.main_activity_spinner);
        sharedPreferences = getSharedPreferences("Homework1", MODE_PRIVATE);

        Resources res = getResources();
        daysOfWeek = res.getStringArray(R.array.days_of_week);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.days_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        previousDay.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        nextDay.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
        editor = sharedPreferences.edit();

        plansText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    editor.putString(daysOfWeek[currentDay], plansText.getText().toString()).apply();
                    handled = true;
                }

                return handled;
            }
        });

        plansText.setText(sharedPreferences.getString(daysOfWeek[currentDay], ""));
    }

    @Override
    public void onClick(View v) {

            if (v.getId() == R.id.main_activity_next_day) {
                currentDay = (currentDay + 1) % 7;
                previousDay.setText(daysOfWeek[((currentDay - 1) + 7) % 7]);
                nextDay.setText(daysOfWeek[(currentDay + 1) % 7]);
                plansText.setText("");
                plansText.setHint("Your Plans for " + daysOfWeek[currentDay]);
                spinner.setSelection(currentDay);
                plansText.setText(sharedPreferences.getString(daysOfWeek[currentDay], ""));

            }
            if (v.getId() == R.id.main_activity_prior_day) {
                currentDay = ((currentDay-1)+7)%7;
                previousDay.setText(daysOfWeek[((currentDay -1)+7)%7]);
                nextDay.setText(daysOfWeek[(currentDay + 1) % 7]);
                plansText.setHint("Your Plans for " + daysOfWeek[currentDay]);
                spinner.setSelection(currentDay);
                plansText.setText(sharedPreferences.getString(daysOfWeek[currentDay], ""));
            }

            if (v.getId() == R.id.main_activity_save) {
                editor.putString(daysOfWeek[currentDay], plansText.getText().toString()).apply();
            }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            currentDay = parent.getSelectedItemPosition();
            previousDay.setText(daysOfWeek[((currentDay - 1) + 7) % 7]);
            nextDay.setText(daysOfWeek[(currentDay + 1) % 7]);
            plansText.setHint("Your Plans for " + daysOfWeek[currentDay]);
            plansText.setText(sharedPreferences.getString(daysOfWeek[currentDay], ""));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
