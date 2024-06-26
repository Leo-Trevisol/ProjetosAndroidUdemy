package com.projeto.novoprojetofirebase.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel {
    String id;
    String nome;
    String sobrenome;
    String email;

    public UserModel() {
    }

    public UserModel(String id, String nome, String sobrenome, String email) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void salvar(){
        //referencía o banco de dados
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("usuarios").child(getId()).setValue(this);
    }
}
