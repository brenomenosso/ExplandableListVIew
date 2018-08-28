package com.example.dev22.expandablelistview.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.example.dev22.expandablelistview.R;
import com.example.dev22.expandablelistview.controller.EnderecoController;
import com.example.dev22.expandablelistview.model.Endereco;
import com.example.dev22.expandablelistview.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EnderecoAdapter extends BaseAdapter implements Filterable {


    private List<Endereco> lista;
    private List<Endereco> listaOriginal;


    public EnderecoAdapter() {
        this.lista = new ArrayList<>();
        this.listaOriginal = new ArrayList<>();

    }

    public void updateList(Usuario usuario) {
        this.lista = EnderecoController.getInstance().buscarEnderecos(usuario);
        this.listaOriginal = lista;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Endereco getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).getId();
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        if(contentView == null) {
            contentView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_endereco, viewGroup, false);
            contentView.setTag(new ViewHolder(contentView));
        }
        ViewHolder viewHolder = (ViewHolder) contentView.getTag();
            viewHolder.textEndereco.setText(getItem(position).getEndereco());
            viewHolder.textCodigo.setText(String.valueOf(getItem(position).getId()));
            viewHolder.textNumero.setText(String.valueOf(getItem(position).getNumero()));
            viewHolder.textComplemento.setText(getItem(position).getComplemento());

        return contentView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Endereco> listaFiltro = new ArrayList<>();
                if(TextUtils.isEmpty(constraint)) {
                    listaFiltro = listaOriginal;
                } else {
                    String filtro = constraint.toString().toLowerCase();
                    for (Endereco endereco : listaOriginal) {
                        if(endereco.getEndereco().toLowerCase().contains(filtro)) {
                            listaFiltro.add(endereco);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.count = listaFiltro.size();
                filterResults.values = listaFiltro;
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lista = (List<Endereco>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    private class ViewHolder {

        private TextView textEndereco,textCodigo ,textNumero, textComplemento;

        ViewHolder(View contentView) {
            textEndereco = contentView.findViewById(R.id.textEndereco);
            textCodigo = contentView.findViewById(R.id.textCodigo);
            textNumero = contentView.findViewById(R.id.textNumero);
            textComplemento = contentView.findViewById(R.id.textComplemento);
        }
    }

}
