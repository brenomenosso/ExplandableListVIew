package com.example.dev22.expandablelistview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dev22.expandablelistview.R;
import com.example.dev22.expandablelistview.controller.EnderecoController;
import com.example.dev22.expandablelistview.model.Endereco;

public class EnderecoCadActivity extends AppCompatActivity {

    public static final String EXTRA_ENDERECO = "EXTRA_ENDERECO";

    private EditText editEndereco;
    private EditText editNumero;
    private EditText editComplemento;
    private Endereco endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_endereco);
        instanceMethods();
        implementsMethods();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initMethods();
    }

    private void instanceMethods() {

        editEndereco = findViewById(R.id.edtEndereco);
        editNumero = findViewById(R.id.edtNumero);
        editComplemento = findViewById(R.id.edtComplemento);

    }

    private void implementsMethods() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro_endereco, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu != null && endereco != null && endereco.getId() != null) {
            menu.findItem(R.id.item_excluir).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void initMethods() {
        endereco = (Endereco) getIntent().getSerializableExtra(EXTRA_ENDERECO);
        if (endereco != null) {
            editEndereco.setText(endereco.getEndereco());
            if(endereco.getNumero() != null) {
                editNumero.setText(String.valueOf(endereco.getNumero()));
            }
            editComplemento.setText(endereco.getComplemento());
        } else {
           finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.item_adicionar):
                if (validaCampos()) {
                    endereco.setEndereco(editEndereco.getText().toString());
                    endereco.setComplemento(editComplemento.getText().toString());
                    if (!TextUtils.isEmpty(editNumero.getText()) &&
                            TextUtils.isDigitsOnly(editNumero.getText())) {
                        endereco.setNumero(Integer.valueOf(editNumero.getText().toString()));
                    }
                    if (EnderecoController.getInstance().save(endereco)) {
                        Toast.makeText(EnderecoCadActivity.this, "Endereço salvo com sucesso!", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(EnderecoCadActivity.this, "Erro ao cadastrar novo endereço!", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            case (R.id.item_excluir):
                if (EnderecoController.getInstance().delete(endereco)) {
                    Toast.makeText(EnderecoCadActivity.this, "Endereço Excluido com sucesso!", Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
         }
      }


    private boolean validaCampos() {

        editEndereco.setError(null);

        if (TextUtils.isEmpty(editEndereco.getText())) {
            editEndereco.setError(("Informe o campo \"Endereço\""));
            editEndereco.requestFocus();
            return false;
        }
        return true;
    }
}
