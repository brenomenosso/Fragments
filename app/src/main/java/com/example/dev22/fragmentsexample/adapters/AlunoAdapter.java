package com.example.dev22.fragmentsexample.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dev22.fragmentsexample.R;
import com.example.dev22.fragmentsexample.model.Aluno;


import java.util.ArrayList;
import java.util.List;


public class AlunoAdapter extends RecyclerView.Adapter< AlunoAdapter.ViewHolder> {

    private List<Aluno> alunoList;
    private OnItemClickListener onItemClickListener;

    public AlunoAdapter() {
        this.alunoList = new ArrayList<>();
    }

    public void updateLista(List<Aluno> alunoList) {
        this.alunoList = alunoList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_aluno, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(position);
        viewHolder.textCodigo.setText(String.valueOf(alunoList.get(position).getCodigo()));
        viewHolder.textNome.setText(alunoList.get(position).getNome());
        viewHolder.textEndereco.setText(alunoList.get(position).getEndereco());
        viewHolder.textNumero.setText(alunoList.get(position).getNumero());
        viewHolder.textCep.setText(alunoList.get(position).getCep());
        viewHolder.textCidade.setText(alunoList.get(position).getCidade());
        viewHolder.textEstado.setText(alunoList.get(position).getEstado());
    }

    @Override
    public int getItemCount() {
        return alunoList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Aluno aluno);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView textCodigo;
        private AppCompatTextView textNome;
        private AppCompatTextView textEndereco;
        private AppCompatTextView textNumero;
        private AppCompatTextView textCep;
        private AppCompatTextView textCidade;
        private AppCompatTextView textEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCodigo = itemView.findViewById(R.id.textCodigoAluno);
            textNome = itemView.findViewById(R.id.textNomeAluno);
            textEndereco = itemView.findViewById(R.id.textEnderecoAluno);
            textNumero = itemView.findViewById(R.id.textNumeroAluno);
            textCep = itemView.findViewById(R.id.textCepAluno);
            textCidade = itemView.findViewById(R.id.textCidadeAluno);
            textEstado = itemView.findViewById(R.id.textEstadoAluno);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(alunoList.get((Integer) v.getTag()));
                    }
                }
            });
        }
    }
}
