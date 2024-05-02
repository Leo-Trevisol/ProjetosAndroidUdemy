package com.projeto.agendadecontatos;

import android.net.Uri;

/**
 * Created by luizugliano on 28/10/15.
 */
public class Contato {

    private String _nome, _telefone, _endereco, _email;
    private Uri _imageURI;
    private long _id;

    public Contato (String nome, String telefone, String endereco, String email, Uri imageURI) {
        _nome = nome;
        _telefone = telefone;
        _endereco = endereco;
        _email = email;
        _imageURI = imageURI;
    }

    public long getId() { return _id; }

    public String getName() {
        return _nome;
    }

    public String getPhone() {
        return _telefone;
    }

    public String getAddress() {
        return _endereco;
    }

    public String getEmail() {
        return _email;
    }

    public Uri getImageURI() { return _imageURI; }

    public void setId(long id) {
        this._id = id;
    }

    public void setNome(String _nome) {
        this._nome = _nome;
    }

    public void setTelefone(String _telefone) {
        this._telefone = _telefone;
    }

    public void setEndereco(String _endereco) {
        this._endereco = _endereco;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public void setImageURI(Uri _imageURI) {
        this._imageURI = _imageURI;
    }
}
