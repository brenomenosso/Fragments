package com.example.dev22.fragmentsexample.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dev22.fragmentsexample.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoSpinnerAdapter extends BaseAdapter {

    private List<Aluno> alunoList;


    public AlunoSpinnerAdapter() {
        this.alunoList = new ArrayList<>();
    }

    public void updateList(List<Aluno> alunoList) {
        this.alunoList = alunoList;
        notifyDataSetChanged();
    }

    public int getPosition(Aluno aluno){
        return alunoList.indexOf(aluno);
    }

    @Override
    public int getCount() {
        return alunoList.size();
    }

    @Override
    public Aluno getItem(int position) {
        return alunoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunoList.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_spinner_item, parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textNome.setText(getItem(position).getNome());
        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.textNome.setText(getItem(position).getNome());
        return convertView;
    }

    public class ViewHolder {

        private TextView textNome;

        public ViewHolder(View convertView) {
            textNome = convertView.findViewById(android.R.id.text1);
        }
    }
}
