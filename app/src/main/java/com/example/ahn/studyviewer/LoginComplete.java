package com.example.ahn.studyviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by Ahn on 2017-02-15.
 */

public class LoginComplete extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView txtWelcome;
    private EditText input_new_password;
    private Button btnChangePass, btnLogout;
    private RelativeLayout activity_dashboard;

    private FirebaseAuth auth;

    /**수정한 부분**/
    private Button add_room;
    private EditText room_name;
    private String name, intro;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    //private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("chatroom");
    /*******************************************************/
    private Button menu1, menu2;
    private Animation aniLeft, aniRight, sleep;
    private LinearLayout slidingPanel;
    /*******************************************************/

    /********nav창 변수************/
    private TextView navTView01, navTView02;
    /******************************/

    /*******스터디 모집 게시판 변수*******/
    Button studyRecruitBoardbtn;
    /*************************************/

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_complete);

        getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;

        getWindow().getAttributes().height = WindowManager.LayoutParams.MATCH_PARENT;

        /******************************************************/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /******************************************************/

        txtWelcome = (TextView) findViewById(R.id.dashboard_welcome);
        //input_new_password = (EditText) findViewById(R.id.dashboard_new_password);
        //btnChangePass = (Button) findViewById(R.id.dashboard_btn_change_pass);
        btnLogout = (Button) findViewById(R.id.dashboard_btn_logout);
        //activity_dashboard = (RelativeLayout) findViewById(R.id.activity_login_complete);

        //btnChangePass.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);



        auth = FirebaseAuth.getInstance();

        /***********스터디 모집 게시판 부분************/
        studyRecruitBoardbtn = (Button) findViewById(R.id.btn2);
        studyRecruitBoardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginComplete.this, com.example.ahn.StudyBoard.BoardMain.class));
            }
        });
        /**********************************************/

        /*****************************슬라이딩 패널부분 ****************************/
        aniRight = AnimationUtils.loadAnimation(this, R.anim.translate_right);
        aniLeft = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        sleep = AnimationUtils.loadAnimation(this, R.anim.sleep);

        //slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);

        //menu1 = (Button) findViewById(R.id.menu_btn01);
        //menu2 = (Button) findViewById(R.id.menu_btn02);

        /*menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingPanel.setVisibility(View.VISIBLE);
                slidingPanel.startAnimation(aniRight);
                buttonView(true);
            }
        });*/

        /*menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingPanel.setVisibility(View.GONE);
                slidingPanel.startAnimation(aniLeft);
                buttonView(false);
            }
        });*/
        /***************************************************************************/
        name = auth.getCurrentUser().getEmail();
        /****************************nav창 처리 부분*******************************/
        //navTView01 = ;
        //navTView02 = (TextView) findViewById(R.id.navTView02);

        //navTView01.setText(auth.getCurrentUser().getUid());
        //navTView02.setText(auth.getCurrentUser().getEmail());
        /**************************************************************************/


        /****************************수정한 부분*********************************/


        //request_user_name();



        /*root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Chat_Room.class);
                intent.putExtra("room_name", ((TextView)view).getText().toString());
                intent.putExtra("user_name", name);
                intent.putExtra("intro", name+"님이 입장하셨습니다.");
                startActivity(intent);
            }
        });*/
        /**************************************************************************************/

        if(auth.getCurrentUser() != null)
            txtWelcome.setText("Welcome!!\n" + auth.getCurrentUser().getEmail());
    }


    Button.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //if(view.getId() == R.id.dashboard_btn_change_pass){
            // changePassword(input_new_password.getText().toString());
            // }else if(view.getId() == R.id.dashboard_btn_logout){



            if(view.getId() == R.id.dashboard_btn_logout){
                logoutUser();
            }
        }
    };

    /* 수정시작

    private void request_user_name(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter name: ");

        final EditText input_field = new EditText(this);

        builder.setView(input_field);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name = input_field.getText().toString();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                request_user_name();
            }
        });

        builder.show();
    }
     */
    private void logoutUser(){
        auth.signOut();
        if(auth.getCurrentUser() == null){
            startActivity(new Intent(LoginComplete.this, MainActivity.class));
        }
    }

    private void buttonView(boolean flag){
        if(flag){
            menu1.setVisibility(View.GONE);
        }else{
            //menu1.startAnimation(sleep);
            menu1.setVisibility(View.VISIBLE);
        }
    }
/*
    private void changePassword(String newPassword){
        FirebaseUser user = auth.getCurrentUser();
        user.updatePassword(newPassword).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Snackbar snackbar = Snackbar.make(activity_dashboard, "Password changed", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
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