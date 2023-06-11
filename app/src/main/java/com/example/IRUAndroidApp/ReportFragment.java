package com.example.IRUAndroidApp;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.IRUAndroidApp.adapters.ItemsAdapter;
import com.example.IRUAndroidApp.models.Schedule;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReportFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
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
    Menu navigationMenu;
    EditText etTest;
    AppCompatActivity activity;
    ImageView arrow;
    WebView webView;
    CheckBox checkBox;
    RequestQueue queue;
    EditText etMessage,etSubject,etName,etStuNumber;
    Button btSend;
    private boolean t1,t2,t3,t4;
    Spinner spinner;
    ImageView captchaImageView,ivRefresh;
    EditText captchaInputEditText;
    String captchaText;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_report,null,false);
        rootView.setFocusable(true);
        rootView.setFocusableInTouchMode(true); rootView.setClickable(true);
        init();
        imageCaptcha();




        navigationMenu.findItem(R.id.action_settings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                ((MainActivity)requireActivity()).replaceFragment(new ScheduleFragment());
                //TextView tv = new TextView(MainActivity.this);
                //menuItem.setActionView(tv);
                //Toast.makeText(getContext(), "shit", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        navigationMenu.findItem(R.id.action_help).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                drawer.closeDrawer(GravityCompat.START);
                //((MainActivity)requireActivity()).replaceFragment(new ReportFragment());
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




        return rootView;

    }


    private void init(){
        captchaImageView = rootView.findViewById(R.id.captchaImageView);
        captchaInputEditText = rootView.findViewById(R.id.captchaInputEditText);
        navigationView = rootView.findViewById(R.id.nav_view);
        navigationMenu = navigationView.getMenu();
        MenuItem menuItem1 = navigationMenu.findItem(R.id.action_settings);
        MenuItem menuItem2 = navigationMenu.findItem(R.id.action_help);
        menuItem2.setChecked(true);

        toolbar = rootView.findViewById(R.id.alo);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        drawer = rootView.findViewById(R.id.drawer_layout);
        arrow = rootView.findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).replaceFragment(new ScheduleFragment());
            }
        });



        ivRefresh = rootView.findViewById(R.id.iv_refresh);
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCaptcha();
            }
        });

        btSend = rootView.findViewById(R.id.bt_send);
        etMessage = rootView.findViewById(R.id.et_message);
        etName = rootView.findViewById(R.id.et_name);
        etSubject = rootView.findViewById(R.id.et_subject);
        etStuNumber = rootView.findViewById(R.id.et_stu_number);


        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if (etName.getText().toString().trim().length() < 5) {
                        etName.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }
                }

            }
        });

        etStuNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if (etStuNumber.getText().toString().trim().length() != 8) {
                        etStuNumber.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }
                }
            }
        });

        etSubject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if (etSubject.getText().toString().trim().length() < 5) {
                        etSubject.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }
                }
            }
        });

        etMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if (etMessage.getText().toString().trim().length() < 10) {
                        etMessage.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }
                }
            }
        });



        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().trim().length() > 5) {


                    etName.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    t1= true;
                } else {
                    t1=false;
                }

            }
        });

        etStuNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().trim().length() == 8) {

                    etStuNumber.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    t2 = true;

                } else {
                    t2=false;
                }

            }
        });

        etSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().trim().length() > 5) {

                    etSubject.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    t3 = true;

                } else {
                    t3=false;
                }

            }
        });


        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().trim().length() > 10) {

                    etMessage.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    t4=true;

                } else {
                    t4 = false;
                }

            }
        });



        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput = captchaInputEditText.getText().toString();
                /*String unDistortedCaptchaText = "";
                for (int i = 0; i < captchaText.length(); i++) {
                    char c = captchaText.charAt(i);
                    if (Character.isUpperCase(c)) {
                        c = Character.toLowerCase(c);
                    }
                    unDistortedCaptchaText += c;
                }*/
               if (!t1){
                   etName.setFocusable(true);
                   etName.requestFocus();
                   etName.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
               }
               else if (!t2){
                   etStuNumber.setFocusable(true);
                   etStuNumber.requestFocus();
                   etStuNumber.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
               }
               else if (!t3){
                   etSubject.setFocusable(true);
                   etSubject.requestFocus();
                   etSubject.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
               }
               else if (!t4){
                   etMessage.requestFocus();
                   etMessage.setFocusable(true);
                   etMessage.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
               }

               else if (!userInput.equalsIgnoreCase(captchaText)){
                   Toast.makeText(getContext(), captchaText, Toast.LENGTH_SHORT).show();
                   captchaInputEditText.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
               }
               else {Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                   ((MainActivity)requireActivity()).replaceFragment(new ReportFragment());}


            }
        });

        spinner = rootView.findViewById(R.id.department_spinner);




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

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                /*FragmentManager fragmentManager = getParentFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 1) {
                    fragmentManager.popBackStack();
                } else {
                    getActivity().finish();
                }*/
                ((MainActivity)requireActivity()).replaceFragment(new ScheduleFragment());
            }
        });
    }










    public static class RandomStringGenerator {
        private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        public static String generateRandomString(int length) {
            Random random = new Random();
            StringBuilder stringBuilder = new StringBuilder(length);

            for (int i = 0; i < length; i++) {
                stringBuilder.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
            }

            return stringBuilder.toString();
        }
    }

    private void imageCaptcha() {
        captchaText = RandomStringGenerator.generateRandomString(5);
        int width = 500;
        int height = 200;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);

        // Add some random lines to the image
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            canvas.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height), paint);
        }

        // Add some random noise and distortion to the text
        StringBuilder distortedText = new StringBuilder(captchaText);
        for (int i = 0; i < captchaText.length(); i++) {
            if (random.nextBoolean()) {
                // Distort the character
                char c = captchaText.charAt(i);
                int offset = random.nextInt(26);
                if (random.nextBoolean()) {
                    c = Character.toUpperCase(c);
                    c += offset;
                    if (c > 'Z') {
                        c -= 26;
                    }
                } else {
                    c = Character.toLowerCase(c);
                    c += offset;
                    if (c > 'z') {
                        c -= 26;
                    }
                }
                distortedText.setCharAt(i, c);
            }
        }
        captchaText = distortedText.toString().replaceAll("[^A-Za-z0-9]", "a");

        // Draw the distorted text onto the image
        for (int i = 0; i < captchaText.length(); i++) {
            char c = captchaText.charAt(i);
            float x = i * width / captchaText.length() + random.nextInt(20) - 10;
            float y = height / 2 + random.nextInt(20) - 10;
            canvas.drawText(String.valueOf(c), x, y, paint);
        }

        captchaImageView.setImageBitmap(bitmap);
    }





}
