package com.example.dev22.expandablelistview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.dev22.expandablelistview.R;
import com.example.dev22.expandablelistview.controller.UsuarioController;
import com.example.dev22.expandablelistview.model.Usuario;


public class CadastroActivity extends AppCompatActivity {

    public static final String EXTRA_USUARIO = "EXTRA_USUARIO";

    private EditText editNome;
    private EditText editSobrenome;
    private EditText editIdade;
    private Button btnEndereco;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        instanceMethods();
        implementsMethods();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initMethods();
    }

    private void instanceMethods() {

        editNome = findViewById(R.id.edtNome);
        editSobrenome = findViewById(R.id.edtSobrenome);
        editIdade = findViewById(R.id.edtIdade);
        btnEndereco = findViewById(R.id.btnEndereco);
    }

    private void implementsMethods() {

        btnEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(CadastroActivity.this, EnderecoActivity.class);
                    intent.putExtra(EnderecoActivity.EXTRA_USUARIO, usuario);
                    startActivity(intent);
                    setResult(RESULT_OK);
                    finish();
                }

        });
    }

    private void initMethods() {

        usuario = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
        if (usuario != null) {
            editNome.setText(usuario.getNome());
            editSobrenome.setText(usuario.getSobrenome());
            editIdade.setText(String.valueOf(usuario.getIdade()));
            btnEndereco.setVisibility(View.VISIBLE);
        } else {
            usuario = new Usuario();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null && usuario != null && usuario.getId() != null) {
            menu.findItem(R.id.item_deletar).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_salvar:
                if (validaCampos()) {
                    usuario.setNome(editNome.getText().toString());
                    usuario.setSobrenome(editSobrenome.getText().toString());
                    if (usuario.getIdade() != null) {
                        usuario.setIdade(Integer.valueOf(editIdade.getText().toString()));
                    }

                    if (UsuarioController.getInstance().save(usuario)) {
                        Toast.makeText(CadastroActivity.this, "Aluno salvo!", Toast.LENGTH_SHORT).show();
                        btnEndereco.setVisibility(View.VISIBLE);
                        setResult(RESULT_OK);
                    } else {
                        Toast.makeText(CadastroActivity.this, "Erro ao salvar Aluno!", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            case (R.id.item_deletar):
                if (UsuarioController.getInstance().delete(usuario)) {
                    Toast.makeText(CadastroActivity.this, " Usuário deletado ", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean validaCampos() {

        editNome.setError(null);
        editSobrenome.setError(null);
        editIdade.setError(null);

        if (TextUtils.isEmpty(editNome.getText())) {
            editNome.setError(getString(R.string.erro_campo_obrigatorio, getString(R.string.nome)));
            editNome.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editSobrenome.getText())) {
            editSobrenome.setError(getString(R.string.erro_campo_obrigatorio, getString(R.string.sobrenome)));
            editSobrenome.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editIdade.getText())) {
            editIdade.setError(getString(R.string.erro_campo_obrigatorio, getString(R.string.idade)));
            editIdade.requestFocus();
            return false;
        }
        return true;
    }

}
