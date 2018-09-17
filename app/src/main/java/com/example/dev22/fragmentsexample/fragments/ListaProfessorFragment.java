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
import com.example.dev22.fragmentsexample.adapters.ProfessorAdapter;
import com.example.dev22.fragmentsexample.controllers.ProfessorController;
import com.example.dev22.fragmentsexample.model.Professor;


import static android.app.Activity.RESULT_OK;

public class ListaProfessorFragment extends Fragment {

    private static final Integer PROFESSOR_CADASTRO_REQUEST_CODE = 147;

    private ProfessorAdapter professorAdapter;
    private FloatingActionButton btnAdicionar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_professores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Lista Professor");
        instanceMethods(view);
        implementsMethods();
        initMethods();
    }

    private void instanceMethods(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_professor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        professorAdapter = new ProfessorAdapter();
        recyclerView.setAdapter(professorAdapter);
        btnAdicionar = view.findViewById(R.id.btnCadProfessores);
    }

    private void implementsMethods() {

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ProfessorFragment()).commit();
            }
        });

        professorAdapter.setOnItemClickListener(new ProfessorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Professor professor) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ProfessorFragment.EXTRA_PROFESSOR, professor);

                ProfessorFragment fragment = new ProfessorFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment).commit();

            }
        });

    }

    private void initMethods() {
        professorAdapter.updateList(ProfessorController.getInstance().findAll());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PROFESSOR_CADASTRO_REQUEST_CODE && resultCode == RESULT_OK) {
            professorAdapter.updateList(ProfessorController.getInstance().findAll());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
