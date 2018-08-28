package com.example.dev22.expandablelistview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.dev22.expandablelistview.R;
import com.example.dev22.expandablelistview.adapter.EnderecoAdapter;
import com.example.dev22.expandablelistview.model.Endereco;
import com.example.dev22.expandablelistview.model.Usuario;


public class EnderecoActivity extends AppCompatActivity {

    public static final String EXTRA_USUARIO = "EXTRA_USUARIO";
    private static final Integer ENDERECO_CADASTRO_REQUEST_CODE = 100;

    private Usuario usuario;

    private FloatingActionButton btnCadEndereco;
    private ListView listEnderecos;
    private EnderecoAdapter enderecoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);
        instanceMethods();
        implementsMethods();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_endereco,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_endereco).getActionView();
        searchView.setQueryHint("Pesquisar...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                enderecoAdapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)) {
                    enderecoAdapter.getFilter().filter(s);
                    return true;
                }
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initMethods();
    }

    private void instanceMethods() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        btnCadEndereco = findViewById(R.id.btnCadEndereco);
        listEnderecos = findViewById(R.id.list_endereco);
        enderecoAdapter = new EnderecoAdapter();
        listEnderecos.setAdapter(enderecoAdapter);

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ENDERECO_CADASTRO_REQUEST_CODE && resultCode == RESULT_OK) {
            enderecoAdapter.updateList(usuario);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void implementsMethods() {
        btnCadEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Endereco endereco = new Endereco();
                endereco.setUserId(usuario.getId());
                Intent intentEndereco = new Intent(EnderecoActivity.this, EnderecoCadActivity.class);
                intentEndereco.putExtra(EnderecoCadActivity.EXTRA_ENDERECO, endereco);
                startActivityForResult(intentEndereco, ENDERECO_CADASTRO_REQUEST_CODE);

            }
        });

        listEnderecos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Endereco endereco = (Endereco) listEnderecos.getItemAtPosition(position);
                Intent intentEndereco = new Intent(EnderecoActivity.this, EnderecoCadActivity.class);
                intentEndereco.putExtra(EnderecoCadActivity.EXTRA_ENDERECO, endereco);
                startActivityForResult(intentEndereco, ENDERECO_CADASTRO_REQUEST_CODE);

            }
        });

    }


    private void initMethods() {
        if (getIntent() != null && getIntent().hasExtra(EXTRA_USUARIO)) {
            usuario = (Usuario) getIntent().getSerializableExtra(EXTRA_USUARIO);
            enderecoAdapter.updateList(usuario);
        } else {
            finish();
        }
    }
}
