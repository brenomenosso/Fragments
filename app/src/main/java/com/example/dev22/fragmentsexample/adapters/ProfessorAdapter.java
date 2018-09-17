package com.example.dev22.fragmentsexample.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dev22.fragmentsexample.R;
import com.example.dev22.fragmentsexample.model.Professor;

import java.util.ArrayList;
import java.util.List;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ViewHolder> {

    private List<Professor> professorList ;
    private OnItemClickListener onItemClickListener;

    public ProfessorAdapter() {
        this.professorList = new ArrayList<>();
    }

    public void updateList(List<Professor> professorList) {
        this.professorList = professorList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_professor,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(position);
        viewHolder.textCodigo.setText((String.valueOf(professorList.get(position).getCodigo())));
        viewHolder.textNome.setText(professorList.get(position).getNome());
        viewHolder.textCep.setText(professorList.get(position).getCep());
        viewHolder.textEndereco.setText(professorList.get(position).getEndereco());
        viewHolder.textNumero.setText(professorList.get(position).getNumero());
        viewHolder.textCidade.setText(professorList.get(position).getCidade());
        viewHolder.textEstado.setText(professorList.get(position).getEstado());
        viewHolder.textEmail.setText(professorList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return professorList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Professor professor);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView textNome;
        private AppCompatTextView textCodigo;
        private AppCompatTextView textCep;
        private AppCompatTextView textEndereco;
        private AppCompatTextView textNumero;
        private AppCompatTextView textCidade;
        private AppCompatTextView textEstado;
        private AppCompatTextView textEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textNomeProfessor);
            textCodigo = itemView.findViewById(R.id.textCodigoProfessor);
            textCep = itemView.findViewById(R.id.textCepProfessor);
            textEndereco = itemView.findViewById(R.id.textEnderecoProfessor);
            textNumero = itemView.findViewById(R.id.textNumeroProfessor);
            textCidade = itemView.findViewById(R.id.textCidadeProfessor);
            textEstado = itemView.findViewById(R.id.textEstadoProfessor);
            textEmail = itemView.findViewById(R.id.textEmailProfessor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(professorList.get((Integer) v.getTag()));
                    }
                }
            });

        }
    }
}
