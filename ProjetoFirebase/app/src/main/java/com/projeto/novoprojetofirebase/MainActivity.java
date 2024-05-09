package com.projeto.novoprojetofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btLogout;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLogout = findViewById(R.id.bt_logout);
        firebaseAuth = FirebaseAuth.getInstance();


        btLogout.setOnClickListener(v -> {

            //faz o logout do usuário do firebase
            firebaseAuth.signOut();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();

        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //verifica se tem usuario logado
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

    }
}