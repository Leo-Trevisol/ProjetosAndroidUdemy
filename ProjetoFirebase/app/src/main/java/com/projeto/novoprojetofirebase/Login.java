package com.projeto.novoprojetofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText editEmail,editSenha;
    private CheckBox checkMostrarSenha;
    private Button btLogin,btRegitsrar;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.edit_email);
        editSenha = findViewById(R.id.edit_senha);
        btLogin = findViewById(R.id.bt_login);
        btRegitsrar= findViewById(R.id.bt_registrar);
        checkMostrarSenha = findViewById(R.id.check_mostrar_senha);
        progressBar = findViewById(R.id.login_progress_bar);

        btLogin.setOnClickListener(v -> {

            String loginEmail = editEmail.getText().toString();
            String loginSenha = editSenha.getText().toString();

            if(!TextUtils.isEmpty(loginEmail) || !TextUtils.isEmpty(loginSenha)){
                progressBar.setVisibility(View.VISIBLE);

                //verifica usuario por email e senha, configurados manualmente no firebase -> Authentication -> Usuários
                firebaseAuth.signInWithEmailAndPassword(loginEmail,loginSenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            abrirTelaPrincipal();
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(Login.this, "Erro: " + error, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }else{
                Toast.makeText(this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
            }
        });

        checkMostrarSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    editSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    private void abrirTelaPrincipal() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}