package com.example.shopeee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.shopeee.R;
import com.example.shopeee.adapter.SlideAdapter;

public class OnBoardingActivity extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout doslayout;
    SlideAdapter slideAdapter;
    Button btn;
    Animation animation;

    TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_on_boarding);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        getSupportActionBar().hide();

        viewPager = findViewById(R.id.slider);
        doslayout = findViewById(R.id.dots);
        btn = findViewById(R.id.get_started_btn);
        // call adapter
        slideAdapter = new SlideAdapter(this);
        viewPager.setAdapter(slideAdapter);

        ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    btn.setVisibility(View.INVISIBLE);
                }else if(position == 1) {
                    btn.setVisibility(View.INVISIBLE);
                }else {
                    animation = AnimationUtils.loadAnimation(OnBoardingActivity.this, R.anim.slide_animation);
                    btn.setAnimation(animation);
                    btn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        // bắt sự kiện cho viewpager
        viewPager.addOnPageChangeListener(changeListener);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoardingActivity.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });

    }

}