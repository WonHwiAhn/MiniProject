package com.example.ahn.MyStudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ahn.studyviewer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ahn on 2017-03-10.
 */

public class MyStudyMain extends AppCompatActivity{
    private String TAG="@@@";
    private String currentUserEmail;
    private String currentUserUid;
    private String data;
    private FirebaseUser user;
    private ListView showMyStudyList;
    private ArrayAdapter<String> showMyStudyLishAdapter;
    private DatabaseReference root;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.mystudy_main);

        root = FirebaseDatabase.getInstance().getReference().child("group");

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserEmail = user.getEmail();
            currentUserUid = user.getUid();
        }

        showMyStudyList = (ListView) findViewById(R.id.showMyStudyList);
        showMyStudyLishAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        showMyStudyList.setAdapter(showMyStudyLishAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot studyData : dataSnapshot.getChildren()) {
                    data = studyData.getValue().toString();
                    data = data.replaceAll(",","=");
                    data = data.replaceAll(" ","");
                    data = data.replace("}","");
                    String[] confirmUser = data.split("=");
                    /*studyTitleData = dataSnapshot.getValue().toString().replaceFirst("\\{","");
                    studyTitleData = studyTitleData.replaceAll("\\}, ", "={");
                    String[] myStudyTitle = studyTitleData.split("=\\{");*/
                    String[] myStudyTitle = studyData.getRef().toString().split("/");
                    Log.d(TAG, "@@@@@@@@@@"+data+"@@@@@@@@@@@@"+confirmUser[1]);
                    Log.d(TAG, "########"+studyData.getRef().toString());
                    //Log.d(TAG, "@@@@@@@@@@"+dataSnapshot.getValue().toString());
                    for(int i=0; i<confirmUser.length;i++) {
                        if (confirmUser[i].equals(currentUserEmail)) {
                            showMyStudyLishAdapter.add(myStudyTitle[4]);
                            showMyStudyLishAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void backBtn(View view){
        finish();
    }
}
