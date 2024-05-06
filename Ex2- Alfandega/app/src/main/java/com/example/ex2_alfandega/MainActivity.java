package com.example.ex2_alfandega;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView imagem;
    TextView texto;
    Animation aparece, some;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagem = findViewById(R.id.imageView);
        texto = findViewById(R.id.textView);
        texto.setText("Toque para continuar");

        aparece = new AlphaAnimation(0,1);
        some = new AlphaAnimation(1,0);

        aparece.setDuration(500);
        some.setDuration(500);

        imagem.setVisibility(View.INVISIBLE);

        aparece.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imagem.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imagem.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        some.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imagem.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imagem.setVisibility(View.INVISIBLE);
                texto.setText("Toque para continuar");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void clicouNaTela(View view){

        if(Math.random()<0.5){
            imagem.setScaleX(-1);
            texto.setText("Siga para direita");
        }else{
            imagem.setScaleX(1);
            texto.setText("Siga para esquerda");
        }

        imagem.startAnimation(aparece);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                imagem.startAnimation(some);
            }
        }, 1500);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Meu log", "Start");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Meu log", "Restart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Meu log", "Onstop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Meu log", "Ondestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Meu log", "Onpause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Meu log", "Onresume");
    }
}