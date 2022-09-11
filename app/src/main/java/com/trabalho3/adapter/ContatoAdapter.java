package com.trabalho3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trabalho3.R;
import com.trabalho3.data.Contato;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContatoAdapter extends BaseAdapter {
    private Context context;
    private List<Contato> contatos = new ArrayList<>();

    public ContatoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Contato getItem(int i) {
        return contatos.get(i) ;
    }

    @Override
    public long getItemId(int i) {
        return contatos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_listcontatos, viewGroup, false);

        TextView txtNome = v.findViewById(R.id.txt_nome);
        TextView txtTelefone = v.findViewById(R.id.txt_telefone);

        Contato contato = contatos.get(i);
        txtNome.setText(contato.getNome());
        txtTelefone.setText(contato.getTelefone());

        return v;
    }

    public void setItems(List<Contato> contatos) {
        this.contatos = contatos;
        notifyDataSetChanged();
    }
}
