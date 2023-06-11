package com.example.IRUAndroidApp;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.IRUAndroidApp.adapters.ItemsAdapter;
import com.example.IRUAndroidApp.api.ApiService;
import com.example.IRUAndroidApp.models.Schedule;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScheduleFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private View rootView;
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
    List<Schedule> filteredSchedule = new ArrayList<>();
    //TextView tvCancel;
    TextView tvDate,tv1,tv2,tv3,tv4,tv5,tv6,tv7,test;
    TextView courseName;
    String monthName;
    TextView[] calendar = new TextView[7];
    ImageView ivSearch;
    androidx.appcompat.widget.SearchView searchView;
    private boolean isSearchVisible=false;
    Menu navigationMenu;
    EditText etTest;
    AppCompatActivity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_schedule,null,false);
        rootView.setFocusable(true);
        rootView.setFocusableInTouchMode(true); rootView.setClickable(true);

        MainActivity activity = (MainActivity) getActivity();

        // Call the function from the activity


        init();
        //searchControl();
        callApi(calendar[0]);


        navigationMenu.findItem(R.id.action_settings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //((MainActivity)requireActivity()).replaceFragment(new ScheduleFragment());
                drawer.closeDrawer(GravityCompat.START);
                //TextView tv = new TextView(MainActivity.this);
                //menuItem.setActionView(tv);
                //Toast.makeText(getContext(), "shit", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        navigationMenu.findItem(R.id.action_help).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                ((MainActivity)requireActivity()).replaceFragment(new ReportFragment());
                drawer.closeDrawer(GravityCompat.START);

                // item1.setActionView(null);
                return true;
            }
        });





        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawrer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        scrollView = rootView.findViewById(R.id.scroll_view);

        // Set scroll position to top
        scrollView.post(() -> {
            scrollView.scrollTo(0, 0);
        });




        return rootView;
    }







    void init(){

        navigationView = rootView.findViewById(R.id.nav_view);
        navigationMenu = navigationView.getMenu();
        MenuItem menuItem1 = navigationMenu.findItem(R.id.action_settings);
        MenuItem menuItem2 = navigationMenu.findItem(R.id.action_help);
        menuItem1.setChecked(true);
        tv1 = rootView.findViewById(R.id.tv1);
        tv2 = rootView.findViewById(R.id.tv2);
        tv3 = rootView.findViewById(R.id.tv3);
        tv4 = rootView.findViewById(R.id.tv4);
        tv5 = rootView.findViewById(R.id.tv5);
        tv6 = rootView.findViewById(R.id.tv6);
        tv7 = rootView.findViewById(R.id.tv7);
        tvDate = rootView.findViewById(R.id.tv_date);
        //tvCancel = findViewById(R.id.tv_cancel);
        courseName = rootView.findViewById(R.id.course_name);
        //tvTest = findViewById(R.id.tv_test);
        drawer = rootView.findViewById(R.id.fragment_drawer);
        navigationView = rootView.findViewById(R.id.nav_view);
        toolbar = rootView.findViewById(R.id.abo);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        rvMenu1 = rootView.findViewById(R.id.rv_menu1);
        rvMenu1.setLayoutManager(layoutManager);
        menu = rootView.findViewById(R.id.right_icon);

        calendar[0] = rootView.findViewById(R.id.tv1);
        calendar[1] = rootView.findViewById(R.id.tv2);
        calendar[2] = rootView.findViewById(R.id.tv3);
        calendar[3] = rootView.findViewById(R.id.tv4);
        calendar[4] = rootView.findViewById(R.id.tv5);
        calendar[5] = rootView.findViewById(R.id.tv6);
        calendar[6] = rootView.findViewById(R.id.tv7);

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
        ivSearch = rootView.findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchControl();
            }
        });








    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }


    private void filter(String searchText){
        Log.d("AAA", "filter() method called with search text: " + searchText);
        int i;
        filteredSchedule.clear();
        for (i=0 ; i<daySchedule.size() ; i++){
            if (daySchedule.get(i).getCourseName().contains(searchText)){
                filteredSchedule.add(daySchedule.get(i));
            }
            Log.d("MainActivity", "Filtered schedule size: " + filteredSchedule.size());
        }
        if (itemsAdapter!=null) {
            itemsAdapter.notifyDataSetChanged();
        }
    }

    void callApi(TextView textView){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://apiproject.ir/")
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Schedule>> call = apiService.getData();

        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                if (response.isSuccessful()) {
                    rootView.findViewById(R.id.rl1).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.rl2).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.view1).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.include1).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.pb_loading).setVisibility(View.GONE);

                    allSchedule = response.body();
                    //filteredSchedule.addAll(allSchedule);
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
                            filteredSchedule.clear();
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
                                    Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                            }
                            if (daySchedule.size()==0){
                                Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                                tvDate.setText(tv1.getText()+" "+monthName);
                            }
                            filteredSchedule.addAll(daySchedule);

                            //Toast.makeText(getContext(), d1, Toast.LENGTH_SHORT).show();
                            itemsAdapter = new ItemsAdapter(getContext(),filteredSchedule);
                            rvMenu1.setAdapter(itemsAdapter);

                            break;
                        case R.id.tv2:
                            //Toast.makeText(MainActivity.this, "tv2", Toast.LENGTH_SHORT).show();
                            String d2="",m2 = "";
                            daySchedule.clear();
                            filteredSchedule.clear();
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
                                    Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                            }
                            if (daySchedule.size()==0){
                                Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                                tvDate.setText(tv2.getText()+" "+monthName);
                            }
                            filteredSchedule.addAll(daySchedule);

                            itemsAdapter = new ItemsAdapter(getContext(),filteredSchedule);
                            rvMenu1.setAdapter(itemsAdapter);

                            break;
                        case R.id.tv3:
                            //Toast.makeText(MainActivity.this, "tv3", Toast.LENGTH_SHORT).show();
                            String d3="",m3 = "";
                            daySchedule.clear();
                            filteredSchedule.clear();
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
                                    Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                            }
                            if (daySchedule.size()==0){
                                Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                                tvDate.setText(tv3.getText()+" "+monthName);
                            }
                            filteredSchedule.addAll(daySchedule);

                            itemsAdapter = new ItemsAdapter(getContext(),filteredSchedule);
                            rvMenu1.setAdapter(itemsAdapter);

                            break;
                        case R.id.tv4:
                            //Toast.makeText(MainActivity.this, "tv4", Toast.LENGTH_SHORT).show();

                            String d4="",m4 = "";
                            daySchedule.clear();
                            filteredSchedule.clear();
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
                                    Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                            }
                            if (daySchedule.size()==0){
                                Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                                tvDate.setText(tv4.getText()+" "+monthName);
                            }
                            filteredSchedule.addAll(daySchedule);

                            itemsAdapter = new ItemsAdapter(getContext(),filteredSchedule);
                            rvMenu1.setAdapter(itemsAdapter);

                            break;
                        case R.id.tv5:
                            //Toast.makeText(MainActivity.this, "tv5", Toast.LENGTH_SHORT).show();

                            String d5="",m5 = "";
                            daySchedule.clear();
                            filteredSchedule.clear();
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
                                    Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                            }
                            if (daySchedule.size()==0){
                                Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                                tvDate.setText(tv5.getText()+" "+monthName);
                            }
                            filteredSchedule.addAll(daySchedule);

                            itemsAdapter = new ItemsAdapter(getContext(),filteredSchedule);
                            rvMenu1.setAdapter(itemsAdapter);

                            break;
                        case R.id.tv6:
                            //Toast.makeText(MainActivity.this, "tv6", Toast.LENGTH_SHORT).show();

                            String d6="",m6 = "";
                            daySchedule.clear();
                            filteredSchedule.clear();
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
                                    Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                            }
                            if (daySchedule.size()==0){
                                Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                                tvDate.setText(tv6.getText()+" "+monthName);
                            }
                            filteredSchedule.addAll(daySchedule);

                            itemsAdapter = new ItemsAdapter(getContext(),filteredSchedule);
                            rvMenu1.setAdapter(itemsAdapter);
                            break;
                        case R.id.tv7:
                            //Toast.makeText(MainActivity.this, "tv7", Toast.LENGTH_SHORT).show();

                            String d7="",m7 = "";
                            daySchedule.clear();
                            filteredSchedule.clear();
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
                                    Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                            }
                            if (daySchedule.size()==0){
                                Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                                tvDate.setText(tv7.getText()+" "+monthName);
                            }
                            filteredSchedule.addAll(daySchedule);

                            itemsAdapter = new ItemsAdapter(getContext(),filteredSchedule);
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

    void searchControl(){

        searchView = rootView.findViewById(R.id.search_view);
        searchView.setVisibility(View.VISIBLE);
        isSearchVisible = true;
        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        ColorFilter colorFilter = new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        closeButton.setColorFilter(Color.WHITE);
        textView.setTextColor(Color.WHITE);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("AAA", "onQueryTextChange: ");
                filter(newText);
                return true;
            }
        });


        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);

// Set onClickListener for search icon
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform some action when search icon is clicked
                //Toast.makeText(getContext(), "Search icon clicked", Toast.LENGTH_SHORT).show();
                searchView.setVisibility(View.GONE);
            }
        });




    }




    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (isSearchVisible){
                    searchView.setVisibility(View.GONE);
                    isSearchVisible=false;
                } else {
                    // Call a method to handle the back press event inside the fragment
                    handleBackPress();
                }
            }
        });
    }

    // Method to handle the back press event inside the fragment
    private void handleBackPress() {
        FragmentManager fragmentManager = getParentFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            getActivity().finish();
        }
    }



}





