package com.example.ahn.StudyBoard;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.ahn.studyviewer.R;

/**
 * Created by Ahn on 2017-02-26.
 */

public class BoardMake extends AppCompatActivity {

    int year, month, day, hour, minute;
    EditText calendarStart;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_make);

        calendarStart = (EditText) findViewById(R.id.calendarStartEText);

        GregorianCalendar calendar = new GregorianCalendar();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public void calendarStart(View view){
        new DatePickerDialog(BoardMake.this, dateSetListener, year, month, day).show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d/%d/%d", year,monthOfYear+1, dayOfMonth);
            calendarStart.setText(msg);
            calendarStart.setTextColor(Color.BLACK);
        }
    };
}
