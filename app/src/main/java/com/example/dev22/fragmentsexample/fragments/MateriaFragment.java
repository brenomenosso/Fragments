package com.example.dev22.fragmentsexample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dev22.fragmentsexample.R;
import com.example.dev22.fragmentsexample.adapters.ProfessorSpinnerAdapter;
import com.example.dev22.fragmentsexample.controllers.MateriaController;
import com.example.dev22.fragmentsexample.controllers.ProfessorController;
import com.example.dev22.fragmentsexample.model.Materia;


public class MateriaFragment extends Fragment {

    public static final String EXTRA_MATERIA = "EXTRA_MATERIA";

    private AppCompatEditText editNome;
    private AppCompatSpinner spinnerProfessor;
    private AppCompatEditText editDescricao;
    private ProfessorSpinnerAdapter professorSpinnerAdapter;
    private Materia materia;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad_materia, container, false);
        getActivity().setTitle("Cadastro Materia");
        instanceMethods(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMethods();

    }

    private void instanceMethods(View view) {
        professorSpinnerAdapter = new ProfessorSpinnerAdapter();
        editNome = view.findViewById(R.id.editNomeMateria);
        spinnerProfessor = view.findViewById(R.id.spinnerProfessorMateria);
        editDescricao = view.findViewById(R.id.editDescricaoMateria);
        spinnerProfessor.setAdapter(professorSpinnerAdapter);
    }

    private void initMethods() {
        professorSpinnerAdapter.updateList(ProfessorController.getInstance().findAll());
        if (getArguments() != null && getArguments().containsKey(EXTRA_MATERIA)) {
            materia = (Materia) getArguments().getSerializable(EXTRA_MATERIA);
            editNome.setText(materia.getNome());
            spinnerProfessor.setSelection(professorSpinnerAdapter.getPosition(materia.getProfessor()));
            editDescricao.setText(materia.getDescricao());
        } else {
            materia = new Materia();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.item_salvar):
                if (verificaCampos()) {
                    materia.setNome(editNome.getText().toString());
                    materia.setProfessor(professorSpinnerAdapter.getItem(spinnerProfessor.getSelectedItemPosition()));
                    materia.setDescricao(editDescricao.getText().toString());
                    if (MateriaController.getInstance().save(materia)) {
                        Toast.makeText(getActivity(), "Materia salva com sucesso!", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction().
                                replace(R.id.container, new ListaMateriaFragment()).commit();
                    }

                } else {
                    Toast.makeText(getActivity(), "Erro ao salvar materia!", Toast.LENGTH_LONG).show();
                }
                return true;
            case (R.id.item_deletar):
                if (MateriaController.getInstance().delete(materia)) {
                    Toast.makeText(getActivity(), "Materia deletada com sucesso!", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.container, new ListaMateriaFragment()).commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cadastro, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean verificaCampos() {

        editNome.setError(null);

        if (TextUtils.isEmpty(editNome.getText())) {
            editNome.setError(("Informe o campo \"Nome\""));
            editNome.requestFocus();
            return false;
        } else if (spinnerProfessor.getSelectedItem() == null) {
//            spinnerProfessor.se
//            return false;
        }
        return true;
    }

}
