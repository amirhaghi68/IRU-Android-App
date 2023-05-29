package com.example.IRUAndroidApp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        //Calendar();
        callApi();



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
        //rvCalendar = findViewById(R.id.recyclerView);
       // rvMenu2 = findViewById(R.id.rv_menu2);
       // rvMenu3 = findViewById(R.id.rv_menu3);
       // rvMenu2.setLayoutManager(new LinearLayoutManager(this));
        rvMenu1.setLayoutManager(layoutManager);
        //rvCalendar.setLayoutManager(layoutManager1);
       // rvMenu3.setLayoutManager(new LinearLayoutManager(this));

        menu = findViewById(R.id.right_icon);
        /*menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        /*for (int i =1 ; i<=30 ;i++){
         dates.add(String.valueOf(i));
        }*/



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

    void callApi(){
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
                    itemsAdapter = new ItemsAdapter(getApplicationContext(),allSchedule);
                    rvMenu1.setAdapter(itemsAdapter);

                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
                // Handle error
            }
        });

    }
}