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

public class Register extends AppCompatActivity {

    private EditText editEmail,editSenha,editConfirmSenha;
    private CheckBox checkMostrarSenha;
    private Button btLogin,btRegitsrar;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.edit_email_register);
        editSenha = findViewById(R.id.edit_senha_register);
        editConfirmSenha = findViewById(R.id.edit_confirmar_senha_register);
        btLogin = findViewById(R.id.bt_login_register);
        btRegitsrar= findViewById(R.id.bt_registrar_register);
        checkMostrarSenha = findViewById(R.id.check_mostrar_senha_register);
        progressBar = findViewById(R.id.login_progress_bar_register);

        checkMostrarSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editConfirmSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    editSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editConfirmSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btRegitsrar.setOnClickListener(v -> {
            String loginRegister = editEmail.getText().toString();
            String senhaRegister = editSenha.getText().toString();
            String senhaConfirmarRegister = editConfirmSenha.getText().toString();

            if(!TextUtils.isEmpty(loginRegister) || !TextUtils.isEmpty(senhaRegister) || !TextUtils.isEmpty(senhaConfirmarRegister)){
                if(senhaRegister.equals(senhaConfirmarRegister)){

                    progressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.createUserWithEmailAndPassword(loginRegister, senhaRegister).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                abrirTelaPrincipal();
                            }else{
                                String error = task.getException().getMessage();
                                Toast.makeText(Register.this, "Erro: " + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(this, "Senhas devem ser iguais em ambos os campos!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void abrirTelaPrincipal() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}