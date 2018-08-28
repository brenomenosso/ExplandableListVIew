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
import android.widget.ExpandableListView;

import com.example.dev22.expandablelistview.R;
import com.example.dev22.expandablelistview.adapter.UsuarioAdapter;


public class PrincipalActivity extends AppCompatActivity {

    private static final Integer USUARIO_CADASTRO_REQUEST_CODE = 432;

    private FloatingActionButton btnAdicionar;
    private UsuarioAdapter usuarioAdapter;
    private ExpandableListView listUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        instanceMethods();
        implementsMethods();

    }

    //Filtro Pesquisa
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setQueryHint("Pesquisar...");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                usuarioAdapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                      usuarioAdapter.getFilter().filter(s);
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
        btnAdicionar = findViewById(R.id.btn_adicionar);
        listUsuario = findViewById(R.id.list_usuario);
        usuarioAdapter = new UsuarioAdapter();
        listUsuario.setAdapter(usuarioAdapter);

    }


    private void implementsMethods() {
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, CadastroActivity.class);
                startActivityForResult(intent, USUARIO_CADASTRO_REQUEST_CODE);
                setResult(RESULT_OK);

            }
        });

        listUsuario.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                startActivityForResult(new Intent(PrincipalActivity.this, CadastroActivity.class)
                        .putExtra(CadastroActivity.EXTRA_USUARIO, usuarioAdapter.getGroup(groupPosition)), USUARIO_CADASTRO_REQUEST_CODE);
                return true;
            }
        });

        listUsuario.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                startActivityForResult(new Intent(PrincipalActivity.this, EnderecoCadActivity.class)
                        .putExtra(EnderecoCadActivity.EXTRA_ENDERECO, usuarioAdapter.getChild(groupPosition, childPosition)), USUARIO_CADASTRO_REQUEST_CODE);
                return false;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == USUARIO_CADASTRO_REQUEST_CODE && resultCode == RESULT_OK) {
            initMethods();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initMethods() {
        usuarioAdapter.updateList();
        for (int position = 0; position < usuarioAdapter.getGroupCount(); position++) {
            if (!listUsuario.isGroupExpanded(position)) {
                listUsuario.expandGroup(position);
            }
        }
    }


}





