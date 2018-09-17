package com.example.dev22.fragmentsexample.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dev22.fragmentsexample.model.Professor;

import java.util.ArrayList;
import java.util.List;

public class ProfessorSpinnerAdapter extends BaseAdapter {

    private List<Professor> professorList;

    public ProfessorSpinnerAdapter() {
        this.professorList = new ArrayList<>();
    }

    public void updateList(List<Professor> professorList) {
        this.professorList = professorList;
        notifyDataSetChanged();
    }

    public int getPosition(Professor professor){
        return professorList.indexOf(professor);
    }

    @Override
    public int getCount() {
        return professorList.size();
    }

    @Override
    public Professor getItem(int position) {
        return professorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return professorList.get(position).getCodigo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_spinner_item, parent, false);
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
