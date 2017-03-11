package com.example.ahn.StudyBoard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahn.studyviewer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ahn on 2017-03-08.
 */

public class BoardConfirm extends AppCompatActivity{
    String TAG = "@@@";
    private String studyTitle;
    DatabaseReference root, root1;
    String[] studyTitles;
    TextView TView01, TView02, TView03, TView04, TView05, TView06, TView07, TView08, TView09;
    FirebaseAuth.AuthStateListener auth;
    String currentUserEmail, currentUserUid;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.board_confirm);

        TView01 = (TextView) findViewById(R.id.TView01);
        TView02 = (TextView) findViewById(R.id.TView02);
        TView03 = (TextView) findViewById(R.id.TView03);
        TView04 = (TextView) findViewById(R.id.TView04);
        TView05 = (TextView) findViewById(R.id.TView05);
        TView06 = (TextView) findViewById(R.id.TView06);
        TView07 = (TextView) findViewById(R.id.TView07);
        TView08 = (TextView) findViewById(R.id.TView08);
        TView09 = (TextView) findViewById(R.id.TView09);

        root = FirebaseDatabase.getInstance().getReference().child("study");
        root1 = FirebaseDatabase.getInstance().getReference().child("group");

        Intent intent = getIntent();
        studyTitles = intent.getExtras().getString("studyTitle").split(" ");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "@@@@@@@@@@@@@" + studyTitles[2]);
                for (DataSnapshot studyData : dataSnapshot.getChildren()) {
                    String data = studyData.getValue().toString();
                    String[] array = data.split("studyTitle=");
                    String studyTitleData = array[1].replace("}", "");
                    Log.d(TAG, "studyTitles[2]" + studyTitles[2] + "array[1]=" + studyTitleData+"  ");
                    if(studyTitles[2].equals(studyTitleData)) {
                        data = data.replaceAll(" ", "");
                        data = data.replaceAll(",", "=");
                        data = data.replace("}", "");
                        Log.d(TAG, "@@@@@@@@@@@@@" + data);
                        String[] completeData = data.split("=");
                        for(int i=0; i<completeData.length; i++){
                            Log.d(TAG, "@@@@@@@@@@@@@" + completeData[i]);
                        }
                        TView01.setText(completeData[21]);
                        TView02.setText(completeData[17]);
                        TView03.setText(completeData[15]);
                        TView04.setText(completeData[1]);
                        TView05.setText(completeData[5]);
                        TView06.setText(completeData[7]);
                        TView07.setText(completeData[11]);
                        TView08.setText(completeData[19]);
                        TView09.setText(completeData[3]);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserEmail = user.getEmail();
            currentUserUid = user.getUid();
        }
    }
    public void registerMyStudyBtn(View view){
        root1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(root1.child(studyTitles[2]).equals("")){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(studyTitles[2], "");
                    root1.updateChildren(map);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(!(root1.child(studyTitles[2]).equals(currentUserUid))) {
            Map<String, Object> groupUserMap = new HashMap<String, Object>();
            groupUserMap.put(currentUserUid,currentUserEmail);
            root1.child(studyTitles[2]).updateChildren(groupUserMap);
        }else{
            Toast.makeText(getApplicationContext(), "이미 등록된 스터디입니다!", Toast.LENGTH_LONG).show();
        }
    }
    public void backBtn(View view){
        finish();
    }
}
