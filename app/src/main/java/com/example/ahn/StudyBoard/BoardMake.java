package com.example.ahn.StudyBoard;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.ahn.studyviewer.R;
import com.google.android.gms.maps.MapView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ahn on 2017-02-26.
 */

public class BoardMake extends AppCompatActivity {

    int year, month, day, hour, minute;
    EditText studyTitle, studyMember, mobileNumber, studyDetail, calendarStart, calendarEnd, timeStart, timeEnd;
    boolean calendarFlag, timeFlag;
    double longitude, latitude;
    String address;
    //TimePickerDialog time;
    MapView googleMap;

    private FirebaseAuth auth;
    TextView currentAddress;
    Intent intent;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("study");

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_make);

        studyTitle = (EditText) findViewById(R.id.studyTitle);
        studyMember = (EditText) findViewById(R.id.studyMember);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        studyDetail = (EditText) findViewById(R.id.studyDetail);

        calendarStart = (EditText) findViewById(R.id.calendarStartEText);
        calendarEnd = (EditText) findViewById(R.id.calendarEndEText);

        timeStart = (EditText) findViewById(R.id.timeStartEText);
        timeEnd = (EditText) findViewById(R.id.timeEndEText);

        currentAddress = (TextView) findViewById(R.id.currentAddress);

        GregorianCalendar calendar = new GregorianCalendar();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        auth = FirebaseAuth.getInstance();
    }

    public void calendarStart(View view){
        calendarFlag = true;
        new DatePickerDialog(BoardMake.this, dateSetListener, year, month, day).show();
    }

    public void calendarEnd(View view){
        calendarFlag = false;
        new DatePickerDialog(BoardMake.this, dateSetListener, year, month, day).show();
    }

    public void timeStart(View view){
        timeFlag = true;
        new TimePickerDialog(this, timeSetListener, 15, 24, true).show();  //false로 하면 오전, 오후 선택할 수 있음
                                                                           //15는 설정시각, 24는 설정분
    }

    public void timeEnd(View view){
        timeFlag = false;
        new TimePickerDialog(this, timeSetListener, 15, 24, true).show();
    }

    public void mapStart(View view){
                intent = new Intent(getApplicationContext(), BoardPopupMap.class);
                intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            address = data.getExtras().getString("address");
            latitude = data.getExtras().getDouble("latitude");
            longitude = data.getExtras().getDouble("longitude");

            currentAddress.setText("현재 입력된 주소\n" + address);
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d/%d/%d", year,monthOfYear+1, dayOfMonth);
            if(calendarFlag)
                calendarStart.setText(msg);
            else
                calendarEnd.setText(msg);
            calendarStart.setTextColor(Color.BLACK);
            calendarEnd.setTextColor(Color.BLACK);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // 설정버튼 눌렀을 때
            String msg = String.format("%d:%d", hourOfDay, minute);
            if(timeFlag)
                timeStart.setText(msg);
            else
                timeEnd.setText(msg);
            timeStart.setTextColor(Color.BLACK);
            timeEnd.setTextColor(Color.BLACK);
        }
    };

    public void btnSubmit(View view){
        BoardMainData bmd = new BoardMainData(studyTitle.getText().toString(), studyMember.getText().toString(), calendarStart.getText().toString(), calendarEnd.getText().toString(), timeStart.getText().toString(), timeEnd.getText().toString(), mobileNumber.getText().toString(), currentAddress.getText().toString(), studyDetail.getText().toString(), longitude, latitude);
        root.push().setValue(bmd);
        finish();
    }
}
