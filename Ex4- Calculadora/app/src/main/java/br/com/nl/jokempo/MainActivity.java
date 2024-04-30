package br.com.nl.jokempo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText textoOperacao;
    private float numeroA;
    private String resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       textoOperacao = findViewById(R.id.texto);

    }

    public void acaobt(View view){
            switch (view.getId()){
                case R.id.btlimpar:
                    textoOperacao.setText("");
                    numeroA = 0;
                    resultado = "";
                    break;
                case R.id.btmais:
                    caculaNumeros("+");
                    break;
                case R.id.btmenos:
                    caculaNumeros("-");
                    break;
                case R.id.btdivide:
                    caculaNumeros("/");
                    break;
                case R.id.btx:
                    caculaNumeros("*");
                    break;
                case R.id.btigual:
                    mostrarResultado();
                    break;
                default:
                    String num;
                    num =((Button) view).getText().toString();
                    getKeyboard(num);
                    break;
            }
    }



    private void caculaNumeros(String tipOperacao) {
        numeroA = Float.parseFloat(textoOperacao.getText().toString());
        resultado = tipOperacao;
        textoOperacao.setText("");
    }

    private void getKeyboard(String vlr) {
        String currentVlr = textoOperacao.getText().toString();
        currentVlr += vlr;
        textoOperacao.setText(currentVlr);
    }

    public void mostrarResultado(){
        float numeroB = Float.parseFloat(textoOperacao.getText().toString());
        float result = 0;

        if(resultado.equals("+")){
            result = numeroB + numeroA;
        }
        if(resultado.equals("-")){
            result = numeroB - numeroA;
        }
        if(resultado.equals("*")){
            result = numeroB * numeroA;
        }
        if(resultado.equals("/")){
            result = numeroB / numeroA;
        }
       textoOperacao.setText(String.valueOf(result));
    }

}