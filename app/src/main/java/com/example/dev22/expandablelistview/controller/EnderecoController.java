package com.example.dev22.expandablelistview.controller;

import com.example.dev22.expandablelistview.dao.EnderecoDao;
import com.example.dev22.expandablelistview.model.Endereco;
import com.example.dev22.expandablelistview.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EnderecoController {

    private static EnderecoController instance;

    public static EnderecoController getInstance() {
        if(instance == null) {
            instance = new EnderecoController();
        }
        return instance;
    }

    private EnderecoDao enderecoDao;

    private EnderecoController() {
        enderecoDao = new EnderecoDao();
    }

    public boolean save(Endereco endereco) {
        return enderecoDao.save(endereco);
    }

    public boolean delete(Endereco endereco) {
        return enderecoDao.delete(endereco);
    }

    public List<Endereco> buscarEnderecos(Usuario usuario) {
        return enderecoDao.buscarEnderecos(usuario);
    }

}
