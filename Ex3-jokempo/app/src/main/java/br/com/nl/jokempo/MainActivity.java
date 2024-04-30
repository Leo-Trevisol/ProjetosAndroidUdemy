package br.com.nl.jokempo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView jogador1, jogador2;
    ImageButton pedra, papel, tesoura;
    Animation some, aparece;
    int jogada1, jogada2;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jogador1 = findViewById(R.id.j1);
        jogador2 = findViewById(R.id.j2);
        pedra = findViewById(R.id.btpedra);
        tesoura = findViewById(R.id.bttesoura);
        papel = findViewById(R.id.btpapel);
        mp = MediaPlayer.create(MainActivity.this, R.raw.alex_play);

        some = new AlphaAnimation(1,0);
        aparece = new AlphaAnimation(0,1);

        some.setDuration(1500);
        aparece.setDuration(250);

        some.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                jogador2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jogador2.setVisibility(View.INVISIBLE);
                jogador2.startAnimation(aparece);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        aparece.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                sorteiaJogadaOponente();
                jogador2.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                verificaJogada();
                jogador2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        pedra.setOnClickListener(v -> {
            tocouBotao(pedra);
        });

        papel.setOnClickListener(v -> {
            tocouBotao(papel);

        });

        tesoura.setOnClickListener(v -> {
            tocouBotao(tesoura);

        });

    }

    public void tocouBotao(View view){
        startSom();
        switch (view.getId()){
            case(R.id.btpedra) :
                jogador1.setImageResource(R.drawable.pedra);
                jogada1 = 1;
                break;
            case(R.id.btpapel) :
                jogador1.setImageResource(R.drawable.papel);
                jogada1 = 2;
                break;
            case(R.id.bttesoura) :
                jogador1.setImageResource(R.drawable.tesoura);
                jogada1 = 3;
                break;
        }
        jogador2.startAnimation(some);
        jogador2.setImageResource(R.drawable.interrogacao);

    }

    public void verificaJogada(){
        if(jogada1 == jogada2){
            Toast.makeText(this, "Empate", Toast.LENGTH_SHORT).show();
        }

        if((jogada1==1&&jogada2==3)||(jogada1==2&&jogada2==1) || (jogada1==3&&jogada2==2)){
            Toast.makeText(this, "Vitoria", Toast.LENGTH_SHORT).show();
        }
        if((jogada2==1&&jogada1==3)||(jogada2==2&&jogada1==1) || (jogada2==3&&jogada1==2)){
            Toast.makeText(this, "Derrota", Toast.LENGTH_SHORT).show();
        }
    }

    public void sorteiaJogadaOponente(){
        Random r = new Random();
        int num = r.nextInt(3);
        switch (num){
            case 0:
                jogador2.setImageResource(R.drawable.pedra);
                jogada2 = 1;
                break;
            case 1:
                jogador2.setImageResource(R.drawable.papel);
                jogada2 = 2;
                break;
            case 2:
                jogador2.setImageResource(R.drawable.tesoura);
                jogada2 = 3;
                break;

        }
    }

    public void startSom(){
        if(mp != null){
            mp.start();
        }
    }
}