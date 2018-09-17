package com.example.dev22.fragmentsexample.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dev22.fragmentsexample.R;
import com.example.dev22.fragmentsexample.model.Materia;

import java.util.ArrayList;
import java.util.List;

public class MateriaAdapter extends RecyclerView.Adapter <MateriaAdapter.ViewHolder> {

    private List<Materia> materiaList;
    private OnItemClickListener onItemClickListener;

    public MateriaAdapter () {
        this.materiaList = new ArrayList<>();
    }

    public void updateList (List<Materia> materiaList) {
        this.materiaList = materiaList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_materias, viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(position);
        viewHolder.textCodigo.setText(String.valueOf(materiaList.get(position).getCodigo()));
        viewHolder.textNome.setText(materiaList.get(position).getNome());
        viewHolder.textProfessor.setText(materiaList.get(position).getProfessor().getNome());
        viewHolder.textDescricao.setText(materiaList.get(position).getDescricao());
    }

    @Override
    public int getItemCount() {
        return materiaList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Materia materia);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView textCodigo;
        private AppCompatTextView textNome;
        private AppCompatTextView textProfessor;
        private AppCompatTextView textDescricao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCodigo = itemView.findViewById(R.id.textCodigoMateria);
            textNome = itemView.findViewById(R.id.textNomeMateria);
            textProfessor = itemView.findViewById(R.id.textProfessorMateria);
            textDescricao = itemView.findViewById(R.id.textDescricaoMateria);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(materiaList.get((Integer) v.getTag()));
                    }
                }
            });
        }
    }
}
