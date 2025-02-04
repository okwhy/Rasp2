package com.example.scheduleapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.scheduleapplication.adapters.DayAdapterFragment;
import com.example.scheduleapplication.adapters.DayListAdapter;
import com.example.scheduleapplication.adapters.ListAdapterTwo;
import com.example.scheduleapplication.entites.Day;
import com.example.scheduleapplication.entites.Lesson;
import com.example.scheduleapplication.service.DayService;
import com.example.scheduleapplication.service.ScheduleService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    static RecyclerView recyclerView;

    ListView listView;

    public static int positionCurrent = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }

        int position = date.getDayOfWeek().getValue()-1;
        positionCurrent = position;


        viewPager2 = findViewById(R.id.viewPager);
        recyclerView = findViewById(R.id.day_list);
        listView = findViewById(R.id.list_list);

        List<Day> days = new ArrayList<>();

        getSupportActionBar().hide();

        DayAdapterFragment dayAdapterFragment = new DayAdapterFragment(this, this);
        DayService dayService = new DayService();
        dayService.setDays(days);
        Log.d("taggg", days.toString());
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.s_select_radius_border);
        Drawable drawablePassive = getResources().getDrawable(R.drawable.select_radius_border);

        DayListAdapter dayListAdapter;
        DayListAdapter.OnStateClickListener onStateClickListener = new DayListAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(int position) {
                Log.d("taggg", String.valueOf(position));
                viewPager2.setCurrentItem(position);
                recyclerView.getAdapter().notifyItemChanged(position, 1);
                recyclerView.getAdapter().notifyItemChanged(positionCurrent, 0);                positionCurrent = position;
                positionCurrent = position;



            }
        };
        dayListAdapter = new DayListAdapter(this, days, dayAdapterFragment, onStateClickListener, drawable, drawablePassive);
        recyclerView.setAdapter(dayListAdapter);
        viewPager2.setAdapter(dayAdapterFragment);
        viewPager2.setCurrentItem(positionCurrent);
        recyclerView.offsetChildrenHorizontal(14);
        listView.setAdapter(new ListAdapterTwo(this,R.layout.day_fragment, days));

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                recyclerView.getAdapter().notifyItemChanged(position, 1);
                recyclerView.getAdapter().notifyItemChanged(positionCurrent, 0);                positionCurrent = position;
                positionCurrent = position;
                super.onPageSelected(position);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }

        int position = date.getDayOfWeek().getValue()-1;
        positionCurrent = position;
        viewPager2.setCurrentItem(position);
        recyclerView.getAdapter().notifyItemChanged(position, 1);


    }
}