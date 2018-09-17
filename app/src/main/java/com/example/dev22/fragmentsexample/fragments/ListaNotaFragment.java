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
import com.example.dev22.fragmentsexample.adapters.NotaAdapter;
import com.example.dev22.fragmentsexample.controllers.NotaController;
import com.example.dev22.fragmentsexample.model.Nota;

import static android.app.Activity.RESULT_OK;

public class ListaNotaFragment extends Fragment {

    private static final Integer MATERIA_CADASTRO_REQUEST_CODE = 123;

    private FloatingActionButton btnAdicionar;
    private NotaAdapter notaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_notas, container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Lista Nota");
        instanceMethods(view);
        implementsMethods();
        initMethods();
    }

    private void instanceMethods(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recycler_nota);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        notaAdapter = new NotaAdapter();
        recyclerView.setAdapter(notaAdapter);
        btnAdicionar = view.findViewById(R.id.btnCadNotas);

    }

    private void implementsMethods() {

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                NotaFragment fragments = new NotaFragment();
                fragments.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragments).commit();
            }
        });

        notaAdapter.setOnItemClickListener(new NotaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(NotaFragment.EXTRA_NOTA, nota);

                NotaFragment fragment = new NotaFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.container, fragment).commit();

            }
        });

    }


    private void initMethods() {
        notaAdapter.updateList(NotaController.getInstance().findAll());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == MATERIA_CADASTRO_REQUEST_CODE && resultCode == RESULT_OK) {
            notaAdapter.updateList(NotaController.getInstance().findAll());
        }
    }
}
