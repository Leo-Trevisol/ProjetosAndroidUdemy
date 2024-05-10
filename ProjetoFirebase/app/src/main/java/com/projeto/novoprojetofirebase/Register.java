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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.projeto.novoprojetofirebase.model.UserModel;

public class Register extends AppCompatActivity {

    private EditText editEmail,editSenha,editConfirmSenha, editNome, editSobrenome;
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
        editNome = findViewById(R.id.edit_nome_register);
        editSobrenome = findViewById(R.id.edit_sobrenome_register);
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

            UserModel userModel = new UserModel();

            userModel.setEmail(editEmail.getText().toString());
            userModel.setNome(editNome.getText().toString());
            userModel.setSobrenome(editSobrenome.getText().toString());
            String senhaRegister = editSenha.getText().toString();
            String senhaConfirmarRegister = editConfirmSenha.getText().toString();

            if(!TextUtils.isEmpty(userModel.getEmail()) && !TextUtils.isEmpty(senhaRegister) &&
                    !TextUtils.isEmpty(senhaConfirmarRegister) && !TextUtils.isEmpty(userModel.getNome())
                    &&!TextUtils.isEmpty(userModel.getSobrenome())){
                if(senhaRegister.equals(senhaConfirmarRegister)){

                    progressBar.setVisibility(View.VISIBLE);

                    //cria usuário no firebase
                    firebaseAuth.createUserWithEmailAndPassword(userModel.getEmail(), senhaRegister).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //seta id do usuario
                                userModel.setId(firebaseAuth.getUid());
                                userModel.salvar();
                                abrirTelaPrincipal();
                            }else{
                                //tratamento de exceções
                                String error = null;
                                try {

                                    throw task.getException();

                                } catch(FirebaseAuthWeakPasswordException e){
                                    error = "A senha deve conter no mínimo 6 caracteres";
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    error = "E-mail inválido";
                                }catch(FirebaseAuthUserCollisionException e){
                                    error = "E-mail já existe";
                                } catch (Exception e) {
                                   error = "Falha ao cadastrar usuário";
                                   e.printStackTrace();
                                }
                                Toast.makeText(Register.this, error, Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }else{
                    Toast.makeText(this, "Senhas devem ser iguais em ambos os campos!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
            }
        });

        btLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });

    }

    private void abrirTelaPrincipal() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}