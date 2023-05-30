package com.example.IRUAndroidApp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.IRUAndroidApp.adapters.CalendarAdapter;
import com.example.IRUAndroidApp.adapters.ItemsAdapter;
import com.example.IRUAndroidApp.api.ApiService;
import com.example.IRUAndroidApp.models.CalendarDayModel;
import com.example.IRUAndroidApp.models.Schedule;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    ImageView menu;
    private RecyclerView rvMenu1,rvCalendar;
    private RecyclerView rvMenu2;
    private RecyclerView rvMenu3;
    private ItemsAdapter itemsAdapter;
    private ScrollView scrollView;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    List<String> dates = new ArrayList<>();
    TextView tvTest;
    List<Schedule> allSchedule = new ArrayList<>();
    List<Schedule> daySchedule = new ArrayList<>();
    //TextView tvCancel;
    TextView tvDate,tv1,tv2,tv3,tv4,tv5,tv6,tv7,test;
    TextView courseName;
    String monthName;
    TextView[] calendar = new TextView[7];







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        callApi(calendar[0]);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawrer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        scrollView = findViewById(R.id.scroll_view);

        // Set scroll position to top
        scrollView.post(() -> {
            scrollView.scrollTo(0, 0);
        });



    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Handle settings button click
                return true;
            case R.id.action_help:
                // Handle help button click
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void init(){

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
        drawer = findViewById(R.id.drawer_layout);
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

        calendar[0] = findViewById(R.id.tv1);
        calendar[1] = findViewById(R.id.tv2);
        calendar[2] = findViewById(R.id.tv3);
        calendar[3] = findViewById(R.id.tv4);
        calendar[4] = findViewById(R.id.tv5);
        calendar[5] = findViewById(R.id.tv6);
        calendar[6] = findViewById(R.id.tv7);

        for (int i =0; i<calendar.length;i++){
            final int tvIndex = i;
            calendar[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callApi(calendar[tvIndex]);
                    calendar[tvIndex].setBackgroundColor(getResources().getColor(R.color.transparent));
                    calendar[tvIndex].setTextColor(Color.WHITE);


                    for (int j = 0 ; j<calendar.length;j++){
                        if (j != tvIndex){
                            calendar[j].setBackgroundColor(Color.WHITE);
                            calendar[j].setTextColor(Color.BLACK);
                        }

                    }
                }
            });
        }





    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    void Calendar(){
        ArrayList<CalendarDayModel> data = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int daysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= daysInCurrentMonth; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            boolean isCurrentMonth = (calendar.get(Calendar.MONTH) == currentMonth);

            String dayName = new SimpleDateFormat("EEE", Locale.getDefault()).format(calendar.getTime());

            if (i == currentDay && isCurrentMonth) {
                data.add(new CalendarDayModel(i, true, dayName));
            } else {
                data.add(new CalendarDayModel(i, isCurrentMonth, dayName));
            }
        }
        rvCalendar.setAdapter(new CalendarAdapter(this, data));

        // Add snap helper to enable snapping to each item in the RecyclerView
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvCalendar);


    }

    void callApi(TextView textView){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.43.56/apiproject1/")
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Schedule>> call = apiService.getData();

        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                if (response.isSuccessful()) {
                    allSchedule = response.body();
                    //itemsAdapter = new ItemsAdapter(getApplicationContext(),allSchedule);
                    //rvMenu1.setAdapter(itemsAdapter);

                    String date1 = allSchedule.get(0).getDate();
                    String[] dateParts1 = date1.split("/");
                    int day1 = Integer.parseInt(dateParts1[2]);

                    int count = day1;
                    for (int j = 0; j<calendar.length;j++){
                        calendar[j].setText(Integer.toString(count));
                        count++;

                    }



                    switch (textView.getId()){
                        case R.id.tv1:
                            //Toast.makeText(MainActivity.this, tv1.getText(), Toast.LENGTH_SHORT).show();
                            String d1="",m1 = "";
                            daySchedule.clear();
                            for (Schedule schedule : allSchedule){
                                if (schedule.getDate()!=null){
                                    String[] dp1 = schedule.getDate().split("/");
                                    d1 = dp1[2];
                                    m1 = dp1[1];
                                    if (d1.equals(Integer.toString(day1))){
                                        getMonth(m1);
                                        tvDate.setText(tv1.getText()+" "+monthName);
                                        daySchedule.add(schedule);
                                    }
                                }else
                                    Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(MainActivity.this, d1, Toast.LENGTH_SHORT).show();
                            itemsAdapter = new ItemsAdapter(getApplicationContext(),daySchedule);
                            rvMenu1.setAdapter(itemsAdapter);

                            break;
                        case R.id.tv2:
                            //Toast.makeText(MainActivity.this, "tv2", Toast.LENGTH_SHORT).show();
                            String d2="",m2 = "";
                            daySchedule.clear();
                            for (Schedule schedule : allSchedule){
                                if (schedule.getDate()!=null){
                                    String[] dp2 = schedule.getDate().split("/");
                                    d2 = dp2[2];
                                    m2 = dp2[1];
                                    if (d2.equals(tv2.getText())){
                                        getMonth(m2);
                                        tvDate.setText(tv2.getText()+" "+monthName);
                                        daySchedule.add(schedule);
                                    }
                                }else
                                    Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
                            }

                            itemsAdapter = new ItemsAdapter(getApplicationContext(),daySchedule);
                            rvMenu1.setAdapter(itemsAdapter);

                            break;
                        case R.id.tv3:
                            //Toast.makeText(MainActivity.this, "tv3", Toast.LENGTH_SHORT).show();
                            String d3="",m3 = "";
                            daySchedule.clear();
                            for (Schedule schedule : allSchedule){
                                if (schedule.getDate()!=null){
                                    String[] dp3 = schedule.getDate().split("/");
                                    d3 = dp3[2];
                                    m3 = dp3[1];
                                    if (d3.equals(tv3.getText())){
                                        getMonth(m3);
                                        tvDate.setText(tv3.getText()+" "+monthName);
                                        daySchedule.add(schedule);
                                    }
                                }else
                                    Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
                            }

                            itemsAdapter = new ItemsAdapter(getApplicationContext(),daySchedule);
                            rvMenu1.setAdapter(itemsAdapter);

                            break;
                        case R.id.tv4:
                            //Toast.makeText(MainActivity.this, "tv4", Toast.LENGTH_SHORT).show();

                            String d4="",m4 = "";
                            daySchedule.clear();
                            for (Schedule schedule : allSchedule){
                                if (schedule.getDate()!=null){
                                    String[] dp4 = schedule.getDate().split("/");
                                    d4 = dp4[2];
                                    m4 = dp4[1];
                                    if (d4.equals(tv4.getText())){
                                        getMonth(m4);
                                        tvDate.setText(tv4.getText()+" "+monthName);
                                        daySchedule.add(schedule);
                                    }
                                }else
                                    Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
                            }

                            itemsAdapter = new ItemsAdapter(getApplicationContext(),daySchedule);
                            rvMenu1.setAdapter(itemsAdapter);

                            break;
                        case R.id.tv5:
                            //Toast.makeText(MainActivity.this, "tv5", Toast.LENGTH_SHORT).show();

                            String d5="",m5 = "";
                            daySchedule.clear();
                            for (Schedule schedule : allSchedule){
                                if (schedule.getDate()!=null){
                                    String[] dp5 = schedule.getDate().split("/");
                                    d5 = dp5[2];
                                    m5 = dp5[1];
                                    if (d5.equals(tv5.getText())){
                                        getMonth(m5);
                                        tvDate.setText(tv5.getText()+" "+monthName);
                                        daySchedule.add(schedule);
                                    }
                                }else
                                    Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
                            }

                            itemsAdapter = new ItemsAdapter(getApplicationContext(),daySchedule);
                            rvMenu1.setAdapter(itemsAdapter);

                            break;
                        case R.id.tv6:
                            //Toast.makeText(MainActivity.this, "tv6", Toast.LENGTH_SHORT).show();

                            String d6="",m6 = "";
                            daySchedule.clear();
                            for (Schedule schedule : allSchedule){
                                if (schedule.getDate()!=null){
                                    String[] dp6 = schedule.getDate().split("/");
                                    d6 = dp6[2];
                                    m6 = dp6[1];
                                    if (d6.equals(tv6.getText())){
                                        getMonth(m6);
                                        tvDate.setText(tv6.getText()+" "+monthName);
                                        daySchedule.add(schedule);
                                    }
                                }else
                                    Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
                            }

                            itemsAdapter = new ItemsAdapter(getApplicationContext(),daySchedule);
                            rvMenu1.setAdapter(itemsAdapter);
                            break;
                        case R.id.tv7:
                            //Toast.makeText(MainActivity.this, "tv7", Toast.LENGTH_SHORT).show();

                            String d7="",m7 = "";
                            daySchedule.clear();
                            for (Schedule schedule : allSchedule){
                                if (schedule.getDate()!=null){
                                    String[] dp7 = schedule.getDate().split("/");
                                    d7 = dp7[2];
                                    m7 = dp7[1];

                                    if (d7.equals(tv7.getText())){
                                        getMonth(m7);
                                        tvDate.setText(tv7.getText()+" "+monthName);
                                        daySchedule.add(schedule);
                                    }
                                }else
                                    Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
                            }
                            if (daySchedule.size()==0){
                                Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
                                tvDate.setText(tv7.getText()+" "+monthName);
                            }

                            itemsAdapter = new ItemsAdapter(getApplicationContext(),daySchedule);
                            rvMenu1.setAdapter(itemsAdapter);
                            break;
                    }






                } else {}





            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
                // Handle error
            }
        });

    }

    void getMonth(String m){
        switch (m) {
            case "1":
                monthName = "فروردین";
                break;
            case "2":
                monthName = "اردیبهشت";
                break;
            case "3":
                monthName = "خرداد";
                break;
            case "4":
                monthName = "تیر";
                break;
            case "5":
                monthName = "مرداد";
                break;
            case "6":
                monthName = "شهریود";
                break;
            case "7":
                monthName = "مهر";
                break;
            case "8":
                monthName = "آبان";
                break;
            case "9":
                monthName = "آذر";
                break;
            case "10":
                monthName = "دی";
                break;
            case "11":
                monthName = "بهمن";
                break;
            case "12":
                monthName = "اسفند";
        }
    }


}