package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mins;
    private EditText sec;
    private TextView timer;
    private Button goButton;
    private CountDownTimer countDownTimer;

    private boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void controlTimer(View view){

        mins = findViewById(R.id.min);
        sec = findViewById(R.id.sec);
        timer = findViewById(R.id.timer);
        goButton = findViewById(R.id.goButton);

        if(isActive){
            goButton.setText("GO!");
            countDownTimer.cancel();
            timer.setText("00:00");
            mins.setText("");
            sec.setText("");
            isActive = false;
        } else {

            String minsInput = mins.getText().toString();
            String secsInput = sec.getText().toString();


            if(minsInput.equals("")){
                minsInput = "0";
            }

            if(secsInput.equals("")){
                secsInput = "0";
            }

            int minutesLeft = Integer.parseInt(minsInput);
            int secondsLeft = Integer.parseInt(secsInput);
            int totalTime = minutesLeft * 60 + secondsLeft;

            if(totalTime != 0)
                goButton.setText("STOP!");

            if (secondsLeft <= 60 && totalTime != 0){

                countDownTimer = new CountDownTimer((long) totalTime * 1000 + 100, 1000) {
                    @Override
                    public void onTick(long l) {
                        updateTimer((int) l / 1000);
                    }

                    @Override
                    public void onFinish() {
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                        mediaPlayer.start();
                    }
                }.start();
                isActive = true;
            } else{
                Toast.makeText(this, "Enter Valid time", Toast.LENGTH_SHORT).show();
                sec.setText("");
            }
        }
    }

    private void updateTimer(int timeLeft){
        int minutesLeft = timeLeft / 60;
        int secondsLeft = timeLeft - (60*minutesLeft);

        String minutesLabel = "";
        String secondsLabel = "";

        if(minutesLeft < 10){
            minutesLabel = "0" + minutesLeft;
        } else {
            minutesLabel = Integer.toString(minutesLeft);
        }

        if(secondsLeft < 10){
            secondsLabel = "0" + secondsLeft;
        } else{
            secondsLabel = Integer.toString(secondsLeft);
        }
        timer.setText(minutesLabel + ":" + secondsLabel);
    }

}