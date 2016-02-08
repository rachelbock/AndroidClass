package com.rage.firsthomework;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

        //Button, text field, sharedPreferences variable creation
        previousDay = (Button) findViewById(R.id.main_activity_prior_day);
        Button saveButton = (Button) findViewById(R.id.main_activity_save);
        nextDay = (Button) findViewById(R.id.main_activity_next_day);
        plansText = (EditText) findViewById(R.id.main_activity_plans_field);
        spinner = (Spinner) findViewById(R.id.main_activity_spinner);
        sharedPreferences = getSharedPreferences("Homework1", MODE_PRIVATE);

        //spinner setup - adding days of week array list to drop down
        Resources res = getResources();
        daysOfWeek = res.getStringArray(R.array.days_of_week);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //On click and on selection listeners
        previousDay.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        nextDay.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        //editor set up, edit action listener setup for IME button. If the IME button is selected, save the text and hide the keyboard
        // screen. This also has a toast pop up when saving.
        editor = sharedPreferences.edit();
        plansText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    editor.putString(daysOfWeek[currentDay], plansText.getText().toString()).apply();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(plansText.getWindowToken(), 0);
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.save_text), Toast.LENGTH_SHORT).show();
                    handled = true;
                }

                return handled;
            }
        });

        plansText.setText(sharedPreferences.getString(daysOfWeek[currentDay], ""));
    }

    /**
     * function to set the previous day and next day buttons to the correct date. Also updates the hint displayed and the spinner text
     * Pulls text from shared preferences if there is data stored for the current day
     */
    public void setDay(int newDay) {
        currentDay = newDay;
        previousDay.setText(daysOfWeek[((currentDay - 1) + 7) % 7]);
        nextDay.setText(daysOfWeek[(currentDay + 1) % 7]);
        plansText.setText("");
        String hintText = getResources().getString(R.string.edit_text_hint);
        String replacedText = String.format(hintText, daysOfWeek[currentDay]);
        plansText.setHint(replacedText);
        spinner.setSelection(currentDay);
        plansText.setText(sharedPreferences.getString(daysOfWeek[currentDay], ""));
    }

    /**
     * On click - if the next day is pressed, the current day increases by one and the setDay function is called to reset the rest of
     * the fields. If the previous day is pressed, the current day is increased and the setDay function resets the rest of the days. If
     * the save button is pressed, the data is stored in shared preferences
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.main_activity_next_day) {
            setDay((currentDay+1)%7);

        }
        if (v.getId() == R.id.main_activity_prior_day) {
            setDay(((currentDay - 1) + 7) % 7);
        }

        if (v.getId() == R.id.main_activity_save) {
            editor.putString(daysOfWeek[currentDay], plansText.getText().toString()).apply();
            Toast.makeText(MainActivity.this, getResources().getString(R.string.save_text), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * When a item is selected from the spinner, the current Day is updated to the position selected in the spinner. The SetDay function
     * is called to reset the rest of the fields.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setDay(parent.getSelectedItemPosition());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
