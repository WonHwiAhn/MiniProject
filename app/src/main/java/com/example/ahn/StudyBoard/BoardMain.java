package com.example.ahn.StudyBoard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ahn.studyviewer.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.ahn.studyviewer.R.id.fab;

/**
 * Created by Ahn on 2017-02-23.
 */

public class BoardMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ArrayAdapter<String> boardMainAdapter;  // Board Title만 표출
    ArrayList<String> boardMainData = new ArrayList<>();  //Board에 담긴 모든 정보
    DatabaseReference root;
    int count = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_main);

        root = FirebaseDatabase.getInstance().getReference().child("study");

        setTitle("스터디 모집 게시판");  //타이틀 바 제목 수정

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);  //board_app_bar_main에 있음(상태바)
        setSupportActionBar(toolbar);

        /*********************************스터디 추가 버튼 눌렀을 때 부분*****************************/
        Button addStudy = (Button) findViewById(fab); //board_app_bar_main에 있음(편지모양 버튼)

        addStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*팝업으로*/
                Intent intent = new Intent(getApplicationContext(), BoardPopupActivity.class);
                intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
            }
        });
        /************************************************************************************/



        /**********************레이아웃에 토글을 삽입하기 위한 부분***************************/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //board_main에 있음 (전체 레이아웃)

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /**************************************************************************************/

        /*********************보드 제목 리스트 부분******************************************/
        final ListView boardList = (ListView) findViewById(R.id.boardList);
        boardMainAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, boardMainData);
        boardList.setAdapter(boardMainAdapter);
        /************************************************************************************/
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                BoardMainData newPost = dataSnapshot.getValue(BoardMainData.class);

                boardMainData.add(count+"  "+ newPost.getStudyTitle());  //Board정보에서 Title data get
                boardMainAdapter.notifyDataSetChanged();
                count++;  //게시물 번호 생성하기 위함 Ex) 1,2,3....
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        /***********************************************************
         *           BoardList에서 item 클릭시                     *
         ***********************************************************/
        boardList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), BoardConfirm.class);
                intent.putExtra("studyTitle", boardMainData.get(position).toString());
                Toast.makeText(getApplicationContext(), boardMainData.get(position).toString(), Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

    /***********************************************************
     *           디바이스에서 뒤로가기 버튼 클릭시             *
     ***********************************************************/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /***********************************************************
     *           게시판 생성시 Intent (BoardMake.class)        *
     ***********************************************************/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                Intent intent = new Intent(getApplicationContext(), BoardMake.class);
                startActivity(intent);
                //데이터 받기
                String result = data.getStringExtra("result");
                finish();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /***********************************************************
     *           디바이스에서 뒤로가기 버튼 클릭시             *
     ***********************************************************/
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
