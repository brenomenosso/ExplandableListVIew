package com.example.dev22.expandablelistview.controller;

import com.example.dev22.expandablelistview.dao.UsuarioDao;
import com.example.dev22.expandablelistview.model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class UsuarioController {

    private static UsuarioController instance;

    public static UsuarioController getInstance() {
        if (instance == null) {
            instance = new UsuarioController();
        }
        return instance;
    }

    private UsuarioDao usuarioDao;

    private UsuarioController() {
        usuarioDao = new UsuarioDao();
    }

    public boolean save(Usuario usuario) {
        return usuarioDao.save(usuario);
    }

    public boolean delete(Usuario usuario) {
        return usuarioDao.delete(usuario);
    }

    public List<Usuario> buscarUsuarios() {
        List<Usuario> usuarios = usuarioDao.buscarUsuarios();
        for (Usuario usuario : usuarios) {
            usuario.setEnderecos(EnderecoController.getInstance().buscarEnderecos(usuario));
        }
        return usuarios;
    }
}
