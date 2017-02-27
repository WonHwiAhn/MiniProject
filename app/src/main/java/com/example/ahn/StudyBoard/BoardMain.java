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
import android.widget.Button;
import android.widget.ListView;

import com.example.ahn.studyviewer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.ahn.studyviewer.R.id.fab;

/**
 * Created by Ahn on 2017-02-23.
 */

public class BoardMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    BoardMainAdapter boardMainAdapter;
    ArrayList<BoardMainData> boardMainData = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_main);

        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("study"); //디비 루트 설정 (채팅 방 밑 노드)

        setTitle("스터디 모집 게시판");  //타이틀 바 제목 수정

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);  //board_app_bar_main에 있음(상태바)
        setSupportActionBar(toolbar);

        /*********************************편지모양 튼 부분*****************************/
        Button addStudy = (Button) findViewById(fab); //board_app_bar_main에 있음(편지모양 버튼)

        addStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*팝업으로*/   //////////////////////////////////////////////////////////////////팝업 수정중
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
        ListView boardList = (ListView) findViewById(R.id.boardList);

        /************************************************************************************/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode==1){
                if(resultCode==RESULT_OK){
                    Intent intent = new Intent(getApplicationContext(), BoardMake.class);
                    startActivity(intent);
                    //데이터 받기
                    String result = data.getStringExtra("result");
                    //txtResult.setText(result);
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
