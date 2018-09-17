package com.example.dev22.fragmentsexample.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dev22.fragmentsexample.model.Materia;

import java.util.ArrayList;
import java.util.List;

public class MateriaSpinnerAdapter extends BaseAdapter {

    private List<Materia> materiaList;

    public MateriaSpinnerAdapter() {
        this.materiaList = new ArrayList<>();
    }

    public void updateList(List<Materia> materiaList) {
        this.materiaList = materiaList;
        notifyDataSetChanged();
    }

    public int getPosition(Materia materia){
        return materiaList.indexOf(materia);
    }

    @Override
    public int getCount() {
        return materiaList.size();
    }

    @Override
    public Materia getItem(int position) {
        return materiaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return materiaList.get(position).getCodigo();
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

