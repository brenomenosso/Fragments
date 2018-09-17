package com.example.dev22.fragmentsexample.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dev22.fragmentsexample.R;
import com.example.dev22.fragmentsexample.adapters.AlunoAdapter;
import com.example.dev22.fragmentsexample.controllers.AlunoController;
import com.example.dev22.fragmentsexample.model.Aluno;

import static android.app.Activity.RESULT_OK;

public class ListaAlunoFragment extends Fragment {

    private static final Integer ALUNO_CADASTRO_REQUEST_CODE = 210;

    private AlunoAdapter alunoAdapter;
    private FloatingActionButton btnAdicionar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_alunos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Lista Alunos");
        instanceMethods(view);
        implementsMethods();
        inithMethods();
    }

    private void instanceMethods(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_aluno);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        alunoAdapter = new AlunoAdapter();
        recyclerView.setAdapter(alunoAdapter);
        btnAdicionar = view.findViewById(R.id.btnCadAlunos);

    }

    private void implementsMethods() {

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new AlunoFragment()).commit();

            }
        });

        alunoAdapter.setOnItemClickListener(new AlunoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Aluno aluno) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AlunoFragment.EXTRA_ALUNO, aluno);

                AlunoFragment fragment = new AlunoFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment).commit();

            }
        });

    }

    private void inithMethods() {
        alunoAdapter.updateLista(AlunoController.getInstance().findAll());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ALUNO_CADASTRO_REQUEST_CODE && resultCode == RESULT_OK) {
            alunoAdapter.updateLista(AlunoController.getInstance().findAll());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
