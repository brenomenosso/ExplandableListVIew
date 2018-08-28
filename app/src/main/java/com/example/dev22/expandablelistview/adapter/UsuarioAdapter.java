package com.example.dev22.expandablelistview.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.dev22.expandablelistview.R;
import com.example.dev22.expandablelistview.controller.UsuarioController;
import com.example.dev22.expandablelistview.model.Endereco;
import com.example.dev22.expandablelistview.model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class UsuarioAdapter extends BaseExpandableListAdapter implements Filterable {

    private List<Usuario> listaUsuarios;
    private List<Usuario> listaOriginal;

    public UsuarioAdapter() {
        listaUsuarios = new ArrayList<>();
        listaOriginal = new ArrayList<>();
    }

    public void updateList() {
        listaUsuarios = UsuarioController.getInstance().buscarUsuarios();
        this.listaOriginal = listaUsuarios;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return listaUsuarios.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listaUsuarios.get(groupPosition).getEnderecos().size();
    }

    @Override
    public Usuario getGroup(int groupPosition) {
        return listaUsuarios.get(groupPosition);
    }

    @Override
    public Endereco getChild(int groupPosition, int childPosition) {
        return listaUsuarios.get(groupPosition).getEnderecos().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return ExpandableListView.getPackedPositionForGroup(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return ExpandableListView.getPackedPositionForChild(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grupo_expandable, viewGroup, false);
            convertView.setTag(new viewHolderGroup(convertView));
        }

        viewHolderGroup viewHolder = (viewHolderGroup) convertView.getTag();
        viewHolder.tvNome.setText(listaUsuarios.get(groupPosition).getNome());
        viewHolder.tvCodigo.setText(String.valueOf(listaUsuarios.get(groupPosition).getId()));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_explandable, viewGroup, false);
            convertView.setTag(new ViewHolderItem(convertView));
        }

        ViewHolderItem viewHolder = (ViewHolderItem) convertView.getTag();
        viewHolder.tvEndereco.setText(listaUsuarios.get(groupPosition).getEnderecos().get(childPosition).getEndereco());
        viewHolder.tvNumero.setText(String.valueOf(listaUsuarios.get(groupPosition).getEnderecos().get(childPosition).getNumero()));
        viewHolder.tvComplemento.setText(listaUsuarios.get(groupPosition).getEnderecos().get(childPosition).getComplemento());
        return convertView;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Usuario> listaFiltro = new ArrayList<>();
                if (TextUtils.isEmpty(constraint)) {
                    listaFiltro = listaOriginal;
                } else {
                    String filtro = constraint.toString().toLowerCase();
                    for (Usuario usuario : listaOriginal) {
                        if (usuario.getNome().toLowerCase().contains(filtro)) {
                            listaFiltro.add(usuario);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.count = listaFiltro.size();
                results.values = listaFiltro;
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaUsuarios = (List<Usuario>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private class viewHolderGroup {

        private TextView tvNome, tvCodigo;

        viewHolderGroup(View contentView) {
            tvNome = contentView.findViewById(R.id.tvNome);
            tvCodigo = contentView.findViewById(R.id.tvCodigo);
        }
    }

    private class ViewHolderItem {

        private TextView tvEndereco, tvNumero, tvComplemento;

        ViewHolderItem(View contentView) {
            tvEndereco = contentView.findViewById(R.id.tvEndereco);
            tvNumero = contentView.findViewById(R.id.tvNumero);
            tvComplemento = contentView.findViewById(R.id.tvComplemento);
        }
    }

}

