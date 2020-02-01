package com.akin.timebender;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static long START_TIME_IN_MILLIS = 0;

    private TextView mTextViewCountDown, currentTime, randomNumber, targetTime;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mTimeLeftInMillisDegisken;
    ImageView timer1, timer2, timer3;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int min = 2000;
        final int max = 7000;
        mTimeLeftInMillis = new Random().nextInt((max - min) + 1) + min;


        final int minD = (int) mTimeLeftInMillis + 1000;
        final int maxD = (int) mTimeLeftInMillis + 5000;
        mTimeLeftInMillisDegisken = new Random().nextInt((maxD - minD) + 1) + minD;


        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        currentTime = findViewById(R.id.text_view_currentTime);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        randomNumber = findViewById(R.id.text_view_randomNumber);
        targetTime = findViewById(R.id.text_view_targetTime);
        randomNumber.setText("Hedef:"+mTimeLeftInMillis);
        timer1 = findViewById(R.id.timer1);
        timer2 = findViewById(R.id.timer2);
        timer3 = findViewById(R.id.timer3);






        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

       /** mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });*/

        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillisDegisken, 1000/100) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillisDegisken = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        //mCountDownTimer.cancel();
       // mTimerRunning = false;
        mButtonStartPause.setText("Pause");
        currentTime.setVisibility(View.VISIBLE);
        targetTime.setVisibility(View.VISIBLE);

        String alinanZaman = (String) mTextViewCountDown.getText();
        currentTime.setText("Your Time:"+alinanZaman);

        targetTime.setText("Target:"+mTimeLeftInMillis);


        mButtonReset.setVisibility(View.VISIBLE);



        final int minD = (int) mTimeLeftInMillis + 1000;
        final int maxD = (int) mTimeLeftInMillis + 5000;
        mTimeLeftInMillisDegisken = new Random().nextInt((maxD - minD) + 1) + minD;
        final int min = 2000;
        final int max = 7000;
        mTimeLeftInMillis = new Random().nextInt((max - min) + 1) + min;
        randomNumber.setText("Hedef:"+mTimeLeftInMillis);



        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);





        int sonuc = (int) Math.abs(mTimeLeftInMillis-mTimeLeftInMillisDegisken);
        if (sonuc < 2000){
            timer1.setImageResource(R.drawable.check);
            Toast.makeText(MainActivity.this, "Basarili!", Toast.LENGTH_SHORT).show();
        }else {
            timer1.setImageResource(R.drawable.error);
            Toast.makeText(MainActivity.this, "Basarisiz!", Toast.LENGTH_SHORT).show();

        }

    }

   /** private void resetTimer() {
        final int minD = (int) mTimeLeftInMillis + 1000;
        final int maxD = (int) mTimeLeftInMillis + 5000;
        mTimeLeftInMillisDegisken = new Random().nextInt((maxD - minD) + 1) + minD;
        final int min = 2000;
        final int max = 7000;
        mTimeLeftInMillis = new Random().nextInt((max - min) + 1) + min;
        randomNumber.setText("Hedef:"+mTimeLeftInMillis);
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
        currentTime.setVisibility(View.INVISIBLE);
    }*/

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillisDegisken / 1000) % 60;
        int seconds = (int) (mTimeLeftInMillisDegisken)%1000;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }



}



