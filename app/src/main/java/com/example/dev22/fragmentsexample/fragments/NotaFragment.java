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
import com.example.dev22.fragmentsexample.adapters.AlunoSpinnerAdapter;
import com.example.dev22.fragmentsexample.adapters.MateriaSpinnerAdapter;
import com.example.dev22.fragmentsexample.controllers.AlunoController;
import com.example.dev22.fragmentsexample.controllers.MateriaController;
import com.example.dev22.fragmentsexample.controllers.NotaController;
import com.example.dev22.fragmentsexample.model.Nota;


public class NotaFragment extends Fragment {

    public static final String EXTRA_NOTA = "EXTRA_NOTA";

    private AppCompatSpinner spinnerAluno;
    private AppCompatSpinner spinnerMateria;
    private AppCompatEditText editNota1;
    private AppCompatEditText editNota2;
    private AppCompatEditText editNota3;
    private AppCompatEditText editNota4;
    private AppCompatEditText editFalta;
    private AlunoSpinnerAdapter alunoSpinnerAdapter;
    private MateriaSpinnerAdapter materiaSpinnerAdapter;
    private Nota nota;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad_nota, container, false);
        instanceMethods(view);
        getActivity().setTitle("Cadastro Nota");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMethods();
    }

    private void instanceMethods(View view) {

        alunoSpinnerAdapter = new AlunoSpinnerAdapter();
        materiaSpinnerAdapter = new MateriaSpinnerAdapter();
        spinnerAluno = view.findViewById(R.id.spinnerAlunorMateriaNota);
        spinnerMateria = view.findViewById(R.id.spinnerMateriaNota);
        editNota1 = view.findViewById(R.id.editNota1);
        editNota2 = view.findViewById(R.id.editNota2);
        editNota3 = view.findViewById(R.id.editNota3);
        editNota4 = view.findViewById(R.id.editNota4);
        editFalta = view.findViewById(R.id.editFaltaNota);
        spinnerAluno.setAdapter(alunoSpinnerAdapter);
        spinnerMateria.setAdapter(materiaSpinnerAdapter);
    }

    private void initMethods() {

        alunoSpinnerAdapter.updateList(AlunoController.getInstance().findAll());
        materiaSpinnerAdapter.updateList(MateriaController.getInstance().findAll());
        if (getArguments() != null && getArguments().containsKey(EXTRA_NOTA)) {

            nota = (Nota) getArguments().getSerializable(EXTRA_NOTA);
            spinnerAluno.setSelection(alunoSpinnerAdapter.getPosition(nota.getAluno()));
            spinnerMateria.setSelection(materiaSpinnerAdapter.getPosition(nota.getMateria()));
            if (nota.getNota1() != null) {
                editNota1.setText(String.valueOf(nota.getNota1()));
            }
            if (nota.getNota2() != null) {
                editNota2.setText(String.valueOf(nota.getNota2()));
            }
            if (nota.getNota3() != null) {
                editNota3.setText(String.valueOf(nota.getNota3()));
            }
            if (nota.getNota4() != null) {
                editNota4.setText(String.valueOf(nota.getNota4()));
            }
            if (nota.getFaltas() != null) {
                editFalta.setText(String.valueOf(nota.getFaltas()));
            }
        } else {
            nota = new Nota();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.item_salvar):
                if (verificaCampos()) {
                    nota.setAluno(alunoSpinnerAdapter.getItem(spinnerAluno.getSelectedItemPosition()));
                    nota.setMateria(materiaSpinnerAdapter.getItem(spinnerMateria.getSelectedItemPosition()));
                    if (!TextUtils.isEmpty(editNota1.getText()) &&
                            TextUtils.isDigitsOnly(editNota1.getText())) {
                        nota.setNota1(Double.valueOf(editNota1.getText().toString()));
                    }
                    if (!TextUtils.isEmpty(editNota2.getText()) &&
                            TextUtils.isDigitsOnly(editNota2.getText())) {
                        nota.setNota2(Double.valueOf(editNota2.getText().toString()));
                    }
                    if (!TextUtils.isEmpty(editNota3.getText()) &&
                            TextUtils.isDigitsOnly(editNota3.getText())) {
                        nota.setNota3(Double.valueOf(editNota3.getText().toString()));
                    }
                    if (!TextUtils.isEmpty(editNota4.getText()) &&
                            TextUtils.isDigitsOnly(editNota4.getText())) {
                        nota.setNota4(Double.valueOf(editNota4.getText().toString()));
                    }
                    if (!TextUtils.isEmpty(editFalta.getText()) &&
                            TextUtils.isDigitsOnly(editFalta.getText())) {
                        nota.setFaltas(Integer.valueOf(editFalta.getText().toString()));
                    }
                    if (NotaController.getInstance().save(nota)) {
                        Toast.makeText(getActivity(), "Nota salva com sucesso!", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction().
                                replace(R.id.container, new ListaNotaFragment()).commit();
                    }
                } else {
                    Toast.makeText(getActivity(), "Erro ao salvar nota!", Toast.LENGTH_LONG).show();
                }
                return true;
            case (R.id.item_deletar):
                if (NotaController.getInstance().delete(nota)) {
                    Toast.makeText(getActivity(), "Nota deletada com sucesso!", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.container, new ListaNotaFragment()).commit();
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

        editNota1.setError(null);
        editNota2.setError(null);
        editNota3.setError(null);
        editNota4.setError(null);
        editFalta.setError(null);


        if (TextUtils.isEmpty(editNota1.getText())) {
            editNota1.setError(("Informe o campo \"Nota\""));
            editNota1.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editNota2.getText())) {
            editNota2.setError(("Informe o campo \"Nota\""));
            editNota2.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editNota3.getText())) {
            editNota3.setError(("Informe o campo \"Nota\""));
            editNota3.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editNota4.getText())) {
            editNota4.setError(("Informe o campo \"Nota\""));
            editNota4.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(editFalta.getText())) {
            editFalta.setError(("Informe o campo \"Falta\""));
            editFalta.requestFocus();
            return false;
        }
        return true;
    }
}
