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
import com.example.dev22.fragmentsexample.adapters.MateriaAdapter;
import com.example.dev22.fragmentsexample.controllers.MateriaController;
import com.example.dev22.fragmentsexample.model.Materia;


import static android.app.Activity.RESULT_OK;

public class ListaMateriaFragment extends Fragment {

    private static final Integer MATERIA_CADASTRO_REQUEST_CODE = 190;

    private FloatingActionButton btnAdicionar;
    private MateriaAdapter materiaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_materias, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Lista Materia");
        instanceMethods(view);
        implementsMethods();
        initMethods();
    }


    private void instanceMethods(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_materia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        materiaAdapter = new MateriaAdapter();
        recyclerView.setAdapter(materiaAdapter);
        btnAdicionar = view.findViewById(R.id.btnCadMaterias);


    }

    private void implementsMethods() {

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new MateriaFragment()).commit();

            }
        });

        materiaAdapter.setOnItemClickListener(new MateriaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Materia materia) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(MateriaFragment.EXTRA_MATERIA, materia);

                MateriaFragment fragment = new MateriaFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment).commit();
            }
        });
    }

    private void initMethods() {
        materiaAdapter.updateList(MateriaController.getInstance().findAll());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == MATERIA_CADASTRO_REQUEST_CODE && resultCode == RESULT_OK) {
            materiaAdapter.updateList(MateriaController.getInstance().findAll());
        }
    }
}
