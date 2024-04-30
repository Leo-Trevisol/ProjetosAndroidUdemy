package br.com.nl.ex1_contador;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int soma = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Meuapp", "ola amigo");

        Button mais = findViewById(R.id.mais);
        Button menos = findViewById(R.id.menos);
        Button reseta = findViewById(R.id.reseta);
        TextView text = findViewById(R.id.text);

        mais.setBackgroundColor(Color.MAGENTA);

        mais.setOnClickListener(v -> {
            soma++;
            text.setText(soma + "");
        });

        menos.setOnClickListener(v -> {
            if(soma == 0 ){
                Toast.makeText(this, "O valor ja eh zero", Toast.LENGTH_SHORT).show();
            }else {
                soma--;
                text.setText(soma + "");
            }
        });

        reseta.setOnClickListener(v -> {
            soma = 0;
            text.setText(soma + "");
        });

    }
}