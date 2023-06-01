package com.example.IRUAndroidApp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.IRUAndroidApp.adapters.ItemsAdapter;
import com.example.IRUAndroidApp.models.Schedule;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    ImageView menu;
    private RecyclerView rvMenu1,rvCalendar;
    private RecyclerView rvMenu2;
    private RecyclerView rvMenu3;
    private ItemsAdapter itemsAdapter;
    private ScrollView scrollView;

    Toolbar toolbar;
    NavigationView navigationView;
    List<String> dates = new ArrayList<>();
    TextView tvTest;
    List<Schedule> allSchedule = new ArrayList<>();
    List<Schedule> daySchedule = new ArrayList<>();
    List<Schedule> filteredSchedule = new ArrayList<>();
    //TextView tvCancel;
    TextView tvDate,tv1,tv2,tv3,tv4,tv5,tv6,tv7,test;
    TextView courseName;
    String monthName;
    TextView[] calendar = new TextView[7];
    ImageView ivSearch;
    androidx.appcompat.widget.SearchView searchView;
    //private boolean isSearchVisible=false;
    Menu navigationMenu;
    EditText etTest;
    FragmentManager fragmentManager= getSupportFragmentManager();;
    ScheduleFragment scheduleFragment;

    ReportFragment reportFragment;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        //searchControl();
        addFragment(new ScheduleFragment());




    }

    public void addFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container,fragment);
        fragmentTransaction.commit();
    }








    void init(){
        reportFragment = (ReportFragment) fragmentManager.findFragmentById(R.id.con_frag);
        scheduleFragment = (ScheduleFragment) fragmentManager.findFragmentById(R.id.fragment_drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationMenu = navigationView.getMenu();
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        tvDate = findViewById(R.id.tv_date);
        //tvCancel = findViewById(R.id.tv_cancel);
        courseName = findViewById(R.id.course_name);
        //tvTest = findViewById(R.id.tv_test);

        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        rvMenu1 = findViewById(R.id.rv_menu1);
        rvMenu1.setLayoutManager(layoutManager);
        menu = findViewById(R.id.right_icon);


    }








}