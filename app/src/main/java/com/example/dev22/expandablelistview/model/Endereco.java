package com.example.dev22.expandablelistview.model;

import java.io.Serializable;

public class Endereco implements Serializable{

    private Integer id;
    private Integer userId;
    private String endereco;
    private Integer numero;
    private String complemento;


    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
