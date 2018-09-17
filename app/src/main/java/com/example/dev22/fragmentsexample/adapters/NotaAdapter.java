package com.example.dev22.fragmentsexample.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.example.dev22.fragmentsexample.R;
import com.example.dev22.fragmentsexample.model.Nota;

import java.util.ArrayList;
import java.util.List;


public class NotaAdapter extends RecyclerView.Adapter< NotaAdapter.ViewHolder>{

    private List<Nota> notaList;
    private OnItemClickListener onItemClickListener;

    public NotaAdapter() {
        this.notaList = new ArrayList<>();
    }

    public void updateList (List<Nota> notaList) {
        this.notaList = notaList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_notas, viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotaAdapter.ViewHolder viewHolder, int position) {
        viewHolder.itemView.setTag(position);
        viewHolder.textCodigo.setText(String.valueOf(notaList.get(position).getCodigo()));
        viewHolder.textAluno.setText(notaList.get(position).getAluno().getNome());
        viewHolder.textMateria.setText(notaList.get(position).getMateria().getNome());
        viewHolder.textNota1.setText(String.valueOf(notaList.get(position).getNota1()));
        viewHolder.textNota2.setText(String.valueOf(notaList.get(position).getNota2()));
        viewHolder.textNota3.setText(String.valueOf(notaList.get(position).getNota3()));
        viewHolder.textNota4.setText(String.valueOf(notaList.get(position).getNota4()));
        viewHolder.textFaltas.setText(String.valueOf(notaList.get(position).getFaltas()));
    }

    @Override
    public int getItemCount() {
        return notaList.size();
    }


    public interface OnItemClickListener{
        void onItemClick(Nota nota);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView textCodigo;
        private AppCompatTextView textAluno;
        private AppCompatTextView textMateria;
        private AppCompatTextView textNota1;
        private AppCompatTextView textNota2;
        private AppCompatTextView textNota3;
        private AppCompatTextView textNota4;
        private AppCompatTextView textFaltas;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textCodigo = itemView.findViewById(R.id.textCodigoNota);
            textAluno = itemView.findViewById(R.id.textAlunoNota);
            textMateria = itemView.findViewById(R.id.textMateriaNota);
            textNota1 = itemView.findViewById(R.id.textN1Nota);
            textNota2 = itemView.findViewById(R.id.textN2Nota);
            textNota3 = itemView.findViewById(R.id.textN3Nota);
            textNota4 = itemView.findViewById(R.id.textN4Nota);
            textFaltas = itemView.findViewById(R.id.textFaltasNota);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(notaList.get((Integer) v.getTag()));
                    }
                }
            });
        }
    }
}
